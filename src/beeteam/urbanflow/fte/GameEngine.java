package beeteam.urbanflow.fte;

import java.util.Map;

import beeteam.urbanflow.BeeTeamData;
import beeteam.urbanflow.aug.jsonparser.JsonParser;

public class GameEngine {

    public enum GameMode {
	TRAINING, ARENA
    }

    public static void main(String[] args) {
	gameLoop(GameMode.TRAINING, BeeTeamData.BOT_TOKEN, BeeTeamData.BOT_SECRET);
    }

    @SuppressWarnings("rawtypes")
    public static void gameLoop(GameMode mode, String botToken, String botSecret) {
	try {
	    Map reponse = demandeDeJeu(mode, botToken, botSecret);
	    boolean success = (boolean) reponse.get("success");
	    if (success) {
		String status = (String) reponse.get("status");
		System.out.println("Status: " + status);
		String checkUrl = "http://24hc15.haum.org" + (String) reponse.get("url");

		reponse = verifJeu(checkUrl, botSecret);
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

}
