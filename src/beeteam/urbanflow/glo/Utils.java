package beeteam.urbanflow.glo;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Utils {

	public static void afficheDistances(List<Arret> graph, int[][] distances) {
		for (int i = 0; i < distances.length; i++) {
			for (int j = 0; j < distances.length; j++) {
				System.out.println(graph.get(i).getStation() + " -> " + graph.get(j).getStation() + " : " + distances[i][j]);;
			}
		}
	}
	
	public static void afficheParcours(Map<Long, Set<Arret>> parcours) {
		for (Long l : parcours.keySet()) {
			System.out.println(l + " " + parcours.get(l));
		}
	}
	
	public static Arret searchArret(List<Arret> graph, String station) {
		for (Arret arret : graph) {
			if (arret.getStation().equals(station)) {
				return arret;
			}
		}
		
		return null;
	}
}
