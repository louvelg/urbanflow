package beeteam.urbanflow.fte;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import beeteam.urbanflow.BeeTeamData;
import beeteam.urbanflow.aug.jsonparser.JsonParser;

public class GameEngine {

	public enum GameMode {
		TRAINING, ARENA
	}

	public static void main(String[] args) {
		new GameEngine().gameLoop(GameMode.TRAINING, BeeTeamData.BOT_TOKEN, BeeTeamData.BOT_SECRET);
	}

	@SuppressWarnings("rawtypes")
	public void gameLoop(GameMode mode, String botToken, String botSecret) {
		System.out.println("[ Starting game run... ]");
		try {
			//**** DEMANDE D'UN NOUVEAU JEU ****//
			Map reponse = demandeDeJeu(mode, botToken, botSecret);
			boolean success = (boolean) reponse.get("success");
			if (success) {
				String status = (String) reponse.get("status");
				System.out.println("Status: " + status);
				String checkUrl = "http://24hc15.haum.org" + (String) reponse.get("url");

				//**** RECUPERATION DES INCIDENTS ****//
				Matcher matcher = Pattern.compile("http://24hc15.haum.org/api/play/([^/]*)/.*/verif").matcher(checkUrl);
				matcher.matches();
				String gameToken = matcher.group(1);
				System.out.println("\n[ Retrieving game incidents... ]");
				Map incidents = getIncidents(gameToken);

				//**** ATTENTE ENTREE EN JEU ****//
				System.out.println("\n[ Entering game... ]");
				boolean startPending;
				do {
					reponse = verifJeu(checkUrl, botSecret);
					success = (boolean) reponse.get("success");
					startPending = "game_pending".equals(reponse.get("status"));
					if (startPending) {
						Object time = reponse.get("time");
						System.out.println("Game start pending... " + time.toString());
						Thread.sleep(10);
					}
				} while (success && startPending);
				System.out.println((String) reponse.get("message"));

				if (success) {
					//**** COLLECTE DONNEES INITIALES ****//
					Map firstStop = (Map) reponse.get("first_stop");
					long firstStopId = Long.valueOf((String) firstStop.get("id"));
					String firstStopName = (String) firstStop.get("name");

					Map targetStop = (Map) reponse.get("target");
					long targetStopId = Long.valueOf((String) targetStop.get("id"));
					String targetStopName = (String) targetStop.get("name");

					String dtstart = (String) reponse.get("dtstart");
					Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss+00:00").parse(dtstart);

					// long time = Long.valueOf((String) reponse.get("time"));
					String moveUrl = "http://24hc15.haum.org" + (String) reponse.get("url");

					//**** PREPARATION DE LA NAVIGATION ****//
					System.out.println(String.format("%s(%d) --> %s(%d) -- %s", firstStopName, firstStopId, targetStopName, targetStopId, date));
					System.out.println("\n[ Preparing navigation... ]");
					List<Moveset> moves = prepareNavigation(firstStopId, targetStopId, date, incidents);

					//**** EXECUTION DES MOUVEMENTS ****//
					for (Moveset move : moves) {
						reponse = mouvement(moveUrl, botSecret, move.trackNumber, move.connection, move.toStopId);
						success = (boolean) reponse.get("success");
						if (!success) {
							status = (String) reponse.get("status");
							String message = (String) reponse.get("message");
							System.out.println(String.format("%1$s: %2$s", status, message));

							if ("moved".equals(status)) {
								//**** MOUVEMENT OK ****//
								System.out.println("RAS");
							} else if ("rerouted".equals(status)) {
								//**** CHANGEMENT CIBLE ****//
								Map newStop = (Map) reponse.get("stop");
								long newStopId = Long.valueOf((String) newStop.get("id"));
								String newStopName = (String) newStop.get("name");
								System.out.println(String.format("%s(%d) --> %s(%d) -- %s", targetStopName, targetStopId, newStopName, newStopId, date));
								System.out.println("\n[ Updating navigation... ]");
								prepareNavigation(targetStopId, newStopId, date, incidents);
								targetStopId = newStopId;
								targetStopName = newStopName;
							} else if ("arrived".equals(status)) {
								//**** JEU TERMINE ****//
								System.out.println("SUCCESS!!");
								break;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
		System.out.println("\n[ Game run done. ]");
	}

	@SuppressWarnings("rawtypes")
	private static Map demandeDeJeu(GameMode mode, String botToken, String botSecret) throws Exception {
		String url = "http://24hc15.haum.org/api/connect/" + botToken;
		String data = String.format("{\"secret\":\"%1$s\",\"mode\":\"%2$s\"}", botSecret, mode.toString().toLowerCase());
		String reponse = Connection.postJson(url, data);
		return (Map) new JsonParser().transform(reponse);
	}

	@SuppressWarnings("rawtypes")
	private static Map getIncidents(String gameToken) throws Exception {
		String url = "http://24hc15.haum.org/api/incidents/" + gameToken;
		String reponse = Connection.getJson(url);
		return (Map) new JsonParser().transform(reponse);
	}

	@SuppressWarnings("rawtypes")
	private static Map verifJeu(String url, String botSecret) throws Exception {
		String data = String.format("{\"secret_token\":\"%1$s\"}", botSecret);
		String reponse = Connection.postJson(url, data);
		return (Map) new JsonParser().transform(reponse);
	}

	@SuppressWarnings("rawtypes")
	private static Map mouvement(String url, String botSecret, String trackNumber, Date connection, long toStopId) throws Exception {
		String connectionStr = new SimpleDateFormat("d MMM HH:mm:ss yyyy").format(connection);
		String data = String.format("{\"secret_token\":\"%1$s\",\"track\":\"%2$s\",\"connection\":\"%3$s\",\"to_stop\":\"%4$s\",\"type\":\"move\"}", botSecret, trackNumber, connectionStr, toStopId);
		String reponse = Connection.postJson(url, data);
		return (Map) new JsonParser().transform(reponse);
	}

	protected class Moveset {
		String trackNumber;
		Date connection;
		long toStopId;

		Moveset(String trackNumber, Date connection, long toStopId) {
			this.trackNumber = trackNumber;
			this.connection = connection;
			this.toStopId = toStopId;
		}
	}

	protected List<Moveset> prepareNavigation(long firstStopId, long targetStopId, Date date, @SuppressWarnings("rawtypes") Map incidents) {
		List<Moveset> moves = new ArrayList<Moveset>();
		// <TEST-SET>
		Calendar calendar = Calendar.getInstance();
		calendar.set(2015, 4, 12, 13, 20, 0);
		moves.add(new Moveset("3", calendar.getTime(), 1217));
		// </TEST-SET>
		return moves;
	}

}
