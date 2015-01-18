package beeteam.urbanflow.aug.dijkstra;

import beeteam.urbanflow.aug.DataSearch;

public class Arret {
	
	
	public String ligne;
	public String station;
	public String jour;
	public String horaire;
	
	public boolean justChanged = false;

	
	
	public Arret(String station)
	{
		this.station = station;
	}
	

	public Arret(String jour, String horaire, String station, String ligne)
	{
		this.jour = jour;
		this.horaire = horaire;
		this.station = station;
		this.ligne = ligne;
	}
	
	

	public long getTemps(Arret suivant) throws Exception {
		String debut = this.horaire;
		String fin = suivant.horaire;

		return DataSearch.duration_min(fin, debut);
	}


	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((horaire == null) ? 0 : horaire.hashCode());
		result = prime * result + ((jour == null) ? 0 : jour.hashCode());
		result = prime * result + ((ligne == null) ? 0 : ligne.hashCode());
		result = prime * result + ((station == null) ? 0 : station.hashCode());
		return result;
	}

	public boolean equals(Object obj)
	{
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Arret other = (Arret) obj;
		
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
		
		return true;
	}
	
	

	public String toString()
	{
		return station;
	}

}
