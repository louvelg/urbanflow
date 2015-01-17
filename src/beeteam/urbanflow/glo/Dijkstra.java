package beeteam.urbanflow.glo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Dijkstra {

	public int[][]	distances;
	List<Arret> graph;
	
	public List initGraph() {
		List<Arret> l = new ArrayList<>();

		Arret a = new Arret("L1","a","","12:00:00","antares");
		Arret b = new Arret("L1","b","","12:00:00","antares");
		Arret c = new Arret("L1","c","","12:00:00","antares");
		Arret d = new Arret("L1","d","","12:00:00","antares");
		Arret e = new Arret("L1","e","","12:00:00","antares");
		Arret f = new Arret("L1","f","","12:00:00","antares");
		Arret g = new Arret("L1","g","","12:00:00","antares");
		Arret h = new Arret("L1","h","","12:00:00","antares");
		Arret i = new Arret("L1","i","","12:00:00","antares");
		Arret j = new Arret("L1","j","","12:00:00","antares");

		a.addSuivant(b);
		a.addSuivant(c);
		a.addSuivant(e);
		b.addSuivant(f);
		c.addSuivant(g);
		c.addSuivant(h);
		e.addSuivant(j);
		f.addSuivant(i);
		h.addSuivant(j);
		i.addSuivant(j);
		
		l.add(a);
		l.add(b);
		l.add(c);
		l.add(d);
		l.add(e);
		l.add(f);
		l.add(g);
		l.add(h);
		l.add(i);
		l.add(j);
		
		return l;
	}

	public List<String> listeDesArrets() {
		return Arrays.asList(new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j" });
	}
	
	public int[][] calcule1niveau(int[][] dist) {
		List<Arret> l = this.graph;
		for (Arret a : l) {
			
		}
		
		return dist;
	}

	public void algo(String start,String end) {

		this.graph = initGraph();
		List<String> arrets = listeDesArrets();

		this.distances = new int[arrets.size()][arrets.size()];
		for (int i = 0; i < arrets.size(); i++) {
			for (int j = 0; j < arrets.size(); j++) {
				distances[i][j] = -1;
			}
		}
		Utils.afficheDistances(distances);
	}

	public static void main(String[] args) {
		Dijkstra d = new Dijkstra();
		d.algo("a","j");
	}
}
