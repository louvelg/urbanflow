package beeteam.urbanflow.glo;

import java.util.ArrayList;
import java.util.List;
import beeteam.urbanflow.aug.DataSearch;

public class Arret {
	String		ligne;
	String		station;
	String		jour;
	String		horaire;
	String		destination;

	List<Arret>	suivants;

	public Arret(String ligne, String station, String jour, String horaire, String destination) {
		super();
		this.ligne = ligne;
		this.station = station;
		this.jour = jour;
		this.horaire = horaire;
		this.destination = destination;
		suivants = new ArrayList<>();
	}

	public long getTemps(Arret suivant) {
		String debut = this.horaire;
		String fin = suivant.getHoraire();

		Long l = null;
		try {
			l = DataSearch.duration_min(fin, debut);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Temps " + station + " -> " + suivant.station + " " + l);

		return l;
	}

	public void addSuivant(Arret suivant) {
		this.suivants.add(suivant);
	}

	public String getLigne() {
		return ligne;
	}

	public void setLigne(String ligne) {
		this.ligne = ligne;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getJour() {
		return jour;
	}

	public void setJour(String jour) {
		this.jour = jour;
	}

	public String getHoraire() {
		return horaire;
	}

	public void setHoraire(String horaire) {
		this.horaire = horaire;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public List<Arret> getSuivants() {
		return suivants;
	}

	public void setSuivants(List<Arret> suivants) {
		this.suivants = suivants;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((destination == null) ? 0 : destination.hashCode());
		result = prime * result + ((horaire == null) ? 0 : horaire.hashCode());
		result = prime * result + ((jour == null) ? 0 : jour.hashCode());
		result = prime * result + ((ligne == null) ? 0 : ligne.hashCode());
		result = prime * result + ((station == null) ? 0 : station.hashCode());
		result = prime * result + ((suivants == null) ? 0 : suivants.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Arret other = (Arret) obj;
		if (destination == null) {
			if (other.destination != null) return false;
		} else if (!destination.equals(other.destination)) return false;
		if (horaire == null) {
			if (other.horaire != null) return false;
		} else if (!horaire.equals(other.horaire)) return false;
		if (jour == null) {
			if (other.jour != null) return false;
		} else if (!jour.equals(other.jour)) return false;
		if (ligne == null) {
			if (other.ligne != null) return false;
		} else if (!ligne.equals(other.ligne)) return false;
		if (station == null) {
			if (other.station != null) return false;
		} else if (!station.equals(other.station)) return false;
		if (suivants == null) {
			if (other.suivants != null) return false;
		} else if (!suivants.equals(other.suivants)) return false;
		return true;
	}

	@Override
	public String toString() {
		return station;
		// return "Arret [ligne=" + ligne + ", station=" + station + ", jour=" + jour + ", horaire=" + horaire + ", destination=" + destination + ", suivants=" + suivants + "]";
	}

}
