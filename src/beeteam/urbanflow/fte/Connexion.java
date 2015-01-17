package beeteam.urbanflow.fte;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import beeteam.urbanflow.aug.jsonparser.JsonParser;

public class Connexion {

    private static final String USER_AGENT = "Mozilla/5.0";

    private static final String TEAM_NAME = "BEE TEAM";
    private static final String TEAM_TOKEN = "8615bf914d76c122190abc664f7e667956939f76e9a50ad6896a8e3bb3fbdb69";

    private static final String BOT_NAME = "BEEBOT";
    private static final String BOT_TOKEN = "27d87885d1d9de2b58de2b859abbb86e";
    private static final String BOT_SECRET = "be9827851067443c63b1ede0c285c8abd7f373c565c3c698bee2467e60ebcf53";

    private static final String MODE_TRAINING = "training";
    private static final String MODE_ARENA = "arena";

    public static void main(String[] args) {
	// TESTS
	try {
	    Map reponse = demandeDeJeu(MODE_TRAINING);
	} catch (Exception e) {
	    System.err.println("Error: " + e.getMessage());
	}
    }

    public static void run() {
	try {
	    Map reponse = demandeDeJeu(MODE_ARENA);
	} catch (Exception e) {
	    System.err.println("Error: " + e.getMessage());
	}
    }

    private static Map demandeDeJeu(String mode) throws Exception {
	String reponse = post("http://24hc15.haum.org/api/connect/" + BOT_TOKEN, "{\"secret\":\"" + BOT_SECRET + "\",\"mode\":\"" + mode + "\"}");
	return (Map) new JsonParser().transform(reponse);
    }

    public static String post(String url, String urlParameters) throws Exception {
	URL obj = new URL(url);
	HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	con.setRequestMethod("POST");
	con.setRequestProperty("User-Agent", USER_AGENT);
	// con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
	// con.setRequestProperty("Content-Type", "multipart/form-data");
	con.setRequestProperty("Content-Type", "application/json;charset=utf-8");

	con.setDoOutput(true);
	DataOutputStream wr = new DataOutputStream(con.getOutputStream());
	wr.writeBytes(urlParameters);
	wr.flush();
	wr.close();

	int responseCode = con.getResponseCode();
	System.out.println("\nSending 'POST' request to URL : " + url);
	System.out.println("Post parameters : " + urlParameters);
	System.out.println("Response Code : " + responseCode);

	BufferedReader in;
	if (responseCode == 200) {
	    in = new BufferedReader(new InputStreamReader(con.getInputStream()));
	} else {
	    in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
	}
	String inputLine;
	StringBuffer response = new StringBuffer();

	while ((inputLine = in.readLine()) != null) {
	    response.append(inputLine);
	}
	in.close();

	System.out.println(response.toString());
	return response.toString();
    }

}
