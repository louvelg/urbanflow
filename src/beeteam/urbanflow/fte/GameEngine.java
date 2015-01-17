package beeteam.urbanflow.fte;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

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
	try {
	    Map reponse = demandeDeJeu(mode, botToken, botSecret);
	    boolean success = (boolean) reponse.get("success");
	    if (success) {
		String status = (String) reponse.get("status");
		System.out.println("Status: " + status);
		String checkUrl = "http://24hc15.haum.org" + (String) reponse.get("url");

		boolean startPending;
		do {
		    reponse = verifJeu(checkUrl, botSecret);
		    success = (boolean) reponse.get("success");
		    startPending = "game_pending".equals(reponse.get("status"));
		    if (startPending) {
			System.out.println("Game start still pending...");
			Thread.sleep(10);
		    }
		} while (success && startPending);
		System.out.println((String) reponse.get("message"));

		if (success) {
		    Map firstStop = (Map) reponse.get("first_stop");
		    long firstStopId = Long.valueOf((String) firstStop.get("id"));
		    String firstStopName = (String) firstStop.get("name");

		    Map targetStop = (Map) reponse.get("target");
		    long targetStopId = Long.valueOf((String) targetStop.get("id"));
		    String targetStopName = (String) targetStop.get("name");

		    String dtstart = (String) reponse.get("dtstart");
		    Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss+00:00").parse(dtstart);

		    long time = Long.valueOf((String) reponse.get("time"));
		    String moveUrl = "http://24hc15.haum.org" + (String) reponse.get("url");

		    System.out.println(String.format("%s(%d) --> %s(%d) -- %s", firstStopName, firstStopId, targetStopName, targetStopId, date));
		    System.out.println("Preparing...");
		    prepareNavigation(firstStopId, targetStopId, date);

//test:		    mouvement(moveUrl, botSecret, 56, "13 Sep 13:20:00 2015", 1220);
		}
	    }
	} catch (Exception e) {
	    System.err.println("Error: " + e.getMessage());
	}
    }

    @SuppressWarnings("rawtypes")
    private static Map demandeDeJeu(GameMode mode, String botToken, String botSecret) throws Exception {
	String url = "http://24hc15.haum.org/api/connect/" + botToken;
	String data = String.format("{\"secret\":\"%1$s\",\"mode\":\"%2$s\"}", botSecret, mode.toString().toLowerCase());
	String reponse = Connection.postJson(url, data);
	return (Map) new JsonParser().transform(reponse);
    }

    @SuppressWarnings("rawtypes")
    private static Map verifJeu(String url, String botSecret) throws Exception {
	String data = String.format("{\"secret_token\":\"%1$s\"}", botSecret);
	String reponse = Connection.postJson(url, data);
	return (Map) new JsonParser().transform(reponse);
    }

    @SuppressWarnings("rawtypes")
    private static Map mouvement(String url, String botSecret, long trackNumber, String connection, long toStopId) throws Exception {
	String data = String.format("{\"secret_token\":\"%1$s\",\"track\":%2$d,\"connection\":\"%3$s\",\"to_stop\":%4$s,\"type\":\"move\"}", botSecret, trackNumber, connection, toStopId);
	String reponse = Connection.postJson(url, data);
	return (Map) new JsonParser().transform(reponse);
    }

    protected void prepareNavigation(long firstStopId, long targetStopId, Date date) {
    }

    protected void nextMove() {
    }

}
