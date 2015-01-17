package beeteam.urbanflow.glo;

public class Utils {

	public static void afficheDistances(int[][] distances) {
		for (int i = 0; i < distances.length; i++) {
			for (int j = 0; j < distances.length; j++) {
				System.out.println("i : " + i + " j : " + j + " distance : " + distances[i][j]);;
			}
		}
	}
}
