package beeteam.urbanflow.aug.dijkstra;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Utils {

	public static void afficheDistances(List<Arret> graph, int[][] distances) {
		for (int i = 0; i < distances.length; i++) {
			for (int j = 0; j < distances.length; j++) {
				System.out.println(graph.get(i).station + " -> " + graph.get(j).station + " : " + distances[i][j]);;
			}
		}
	}

	public static void afficheParcours(Map<Long, Set<Arret>> parcours) {
		
		for (Long l : parcours.keySet())
			System.out.println(l + "->" + parcours.get(l));
	}
	
	public static void afficheBack2(Map<Arret,Arret> back) {
		for (Arret l : back.keySet()) {
			System.out.println(back.get(l) + " est parent de " + l);
		}
	}

	public static Arret searchArret(List<Arret> graph, String station) {
		for (Arret arret : graph) {
			if (arret.station.equals(station)) { return arret; }
		}

		return null;
	}
}
