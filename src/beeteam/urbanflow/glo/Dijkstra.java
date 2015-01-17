package beeteam.urbanflow.glo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Dijkstra {

	public int[][]			distances;
	List<Arret>				graph;
	Map<Long, Set<Arret>>	parcours;
	Map<Arret, List<Arret>>	back2;
	Map<Long, Set<Arret>>	back;

	public List<Arret> initGraph() {
		List<Arret> l = new ArrayList<>();

		Arret a = new Arret("L1", "a", "", "12:00:00", "antares");
		Arret b = new Arret("L1", "b", "", "12:04:00", "antares");
		Arret c = new Arret("L1", "c", "", "12:10:00", "antares");
		Arret d = new Arret("L1", "d", "", "12:15:00", "antares");
		Arret e = new Arret("L1", "e", "", "12:25:00", "antares");
		Arret f = new Arret("L1", "f", "", "12:38:00", "antares");
		Arret g = new Arret("L1", "g", "", "12:47:00", "antares");
		Arret h = new Arret("L1", "h", "", "12:56:00", "antares");
		Arret i = new Arret("L1", "i", "", "13:06:00", "antares");
		Arret j = new Arret("L1", "j", "", "13:27:00", "antares");
		Arret k = new Arret("L1", "k", "", "12:30:00", "antares");

		a.addSuivant(b);
		a.addSuivant(c);
		
		a.addSuivant(e);
		a.addSuivant(k);
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

	public void algo(String start, String end) {
		Arret s = Utils.searchArret(graph, start);
		Arret e = Utils.searchArret(graph, end);

		parcours = new HashMap<Long, Set<Arret>>();
		back = new HashMap<Long, Set<Arret>>();
		back2 = new HashMap<>();
		addToParcours(0L, s);
		addToBack(0L, s, s);
		addToBack2(s, s);
		while (step(e)) {}

		List<Arret> arrets = new ArrayList<>();
		arrets.add(e);
		Arret prev = back2.get(arrets.get(0)).get(0);
		while (!prev.equals(s)) {
			arrets.add(prev);
			prev = back2.get(prev).get(0);
		}
		arrets.add(s);
		System.out.println(parcours.keySet().iterator().next() + " liste des arrets : " + arrets);
		Utils.afficheParcours(parcours, null);
		// Utils.afficheDistances(this.graph, distances);
	}

	public boolean step(Arret end) {
		Utils.afficheParcours(parcours, null);

		Long min = getDistanceMin();
		Set<Arret> s = parcours.get(min);
		for (Arret arretPlusProche : s) {
			if (arretPlusProche.equals(end)) { return false; }
			for (Arret suivant : arretPlusProche.getSuivants()) {
				System.out.println(arretPlusProche.getTemps(suivant) + min);
				addToParcours(arretPlusProche.getTemps(suivant) + min, suivant);
				addToBack(arretPlusProche.getTemps(suivant) + min, suivant, arretPlusProche);
				addToBack2(suivant, arretPlusProche);
			}
		}
		parcours.remove(min);
		Utils.afficheParcours(parcours, null);
		Utils.afficheBack2(back2);

		return true;
	}

	public void addToParcours(Long l, Arret a) {
		if (parcours.get(l) == null) {
			HashSet<Arret> hs = new HashSet<>();
			hs.add(a);
			parcours.put(l, hs);
		} else {
			Set<Arret> hs = parcours.get(l);
			hs.add(a);
			parcours.put(l, hs);
		}
	}

	public void addToBack(Long l, Arret a, Arret b) {
		if (back.get(l) == null) {
			HashSet<Arret> hs = new HashSet<>();
			hs.add(a);
			// hs.add(b);
			back.put(l, hs);
		} else {
			Set<Arret> hs = back.get(l);
			hs.add(a);
			// hs.add(b);
			back.put(l, hs);
		}
	}

	public void addToBack2(Arret a, Arret b) {
		if (back2.get(a) == null) {
			ArrayList<Arret> l = new ArrayList<>();
			l.add(b);
			back2.put(a, l);
		} else {
			List<Arret> l = back2.get(a);
			l.add(b);
			back2.put(a, l);
		}
	}

	public Long getDistanceMin() {
		Long result = 0L;
		for (Long key : parcours.keySet()) {
			if (result == 0) {
				result = key;
			}
			if (key < result) {
				result = key;
			}
		}
		return result;
	}

	public static void main(String[] args) {
		Dijkstra d = new Dijkstra();
		d.graph = d.initGraph();
		d.algo("a", "j");
	}

	// public Arret getPlusCourtATraiter(Arret a) {
	// List<Arret> suivants = a.getSuivants();
	// if (suivants == null) { return null; }
	// Arret result = null;
	// result = suivants.get(0);
	// for (Arret arret : suivants) {
	// if (a.getTemps(arret) < a.getTemps(result)) {
	// result = arret;
	// }
	// }
	// return result;
	// }
	// public Arret getSuivantATraiter(Arret arret) {
	// List<Arret> suivants = arret.getSuivants();
	// for (Arret arret2 : suivants) {
	// if (!this.marque.contains(arret2)) { return arret2; }
	// }
	// return null;
	// }

	// this.distances = calcule1niveau(distances, s);
	// marque.add(s);
	// this.atraiter.remove(s);
	//
	// Arret suivantatraiter = null;
	// Arret encours = s;
	// while (this.atraiter.size() > 1) {
	// System.out.println("Traitement de : " + encours + ", " + this.atraiter.size() + " arret(s) a traiter");
	// if (encours.getSuivants() != null && encours.getSuivants().size() > 0) {
	// for (Arret arret : encours.getSuivants()) {
	// if (this.marque.contains(arret)) {
	// continue;
	// }
	// // suivantatraiter = getSuivantATraiter(suivantatraiter);
	// System.out.println(arret);
	// // if (suivantatraiter != null) {
	// this.distances = calcule1niveau(distances, arret);
	// marque.add(suivantatraiter);
	// this.atraiter.remove(suivantatraiter);
	// // } else {
	// // suivantatraiter = this.atraiter.get(0);
	// // }
	// }
	// } else { // L'arret en cours n'a pas de suivant
	// suivantatraiter = this.atraiter.get(0);
	// }
	// }

	// public int[][] calcule1niveau(int[][] dist, Arret start) {
	// dist[this.graph.indexOf(start)][this.graph.indexOf(start)] = 0;
	// for (Arret a : this.graph) {
	// for (Arret suivant : a.getSuivants()) {
	// dist[this.graph.indexOf(a)][this.graph.indexOf(suivant)] = a.getTemps(suivant);
	// }
	// }
	//
	// return dist;
	// }
}
