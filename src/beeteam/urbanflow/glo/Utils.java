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

	public static void afficheParcours(Map<Long, Set<Arret>> parcours, String s) {
		if (s != null) {
			System.out.println("**** " + s);
		}
		for (Long l : parcours.keySet()) {
			System.out.println(l + " " + parcours.get(l));
		}
		if (s != null) {
			System.out.println("******************");
		}
	}
	
	public static void afficheBack2(Map<Arret, List<Arret>> back) {
		for (Arret l : back.keySet()) {
			System.out.println(back.get(l) + " est parent de " + l);
		}
	}

	public static Arret searchArret(List<Arret> graph, String station) {
		for (Arret arret : graph) {
			if (arret.getStation().equals(station)) { return arret; }
		}

		return null;
	}
}
