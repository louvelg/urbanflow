package beeteam.urbanflow.glo;

import java.util.List;

public class Arret {
	String ligne;
	String station;
	String jour;
	String horaire;
	String destination;
	

	List<Arret> suivants;


	public Arret(String ligne, String station, String jour, String horaire, String destination) {
		super();
		this.ligne = ligne;
		this.station = station;
		this.jour = jour;
		this.horaire = horaire;
		this.destination = destination;
	}

	public int getTemps(Arret suivant) {
		//TODO modifier la fonction de calcul de temps entre deux arrets
		return 4;
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
	
	
}
