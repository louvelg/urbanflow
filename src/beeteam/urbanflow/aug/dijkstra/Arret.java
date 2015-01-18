package beeteam.urbanflow.aug.dijkstra;

import java.util.Date;

import beeteam.urbanflow.aug.DataSearch;

public class Arret {
	
	public static final long CHANGED_PENALTY = 7;
	
	
	private DataSearch ds;
	
	public String ligne;
	public String station;
	public String jour;
	public String horaire;
	public String horaire_depart;
	
	public boolean changed;
	
	public long fullTime;

	
	
	public Arret(DataSearch ds, String station)
	{
		this.ds = ds;
		this.station = station;
	}
	

	public Arret(DataSearch ds, String jour, String horaire, String station, String ligne, boolean changed)
	{
		this.ds = ds;
		this.jour = jour;
		this.horaire = horaire;
		this.station = station;
		this.ligne = ligne;
		this.changed = changed;
	}
	
	
	public String display() throws Exception
	{
		return ds.findStopDisplay(station)+"\tligne="+ligne+"\thoraire="+horaire+"\td="+fullTime+" min";
	}
	
	public Date buildDateDepart(Date initDate) throws Exception
	{
		if(horaire_depart==null) return null;
		String yyyyMMdd = ds.yyyyMMdd(initDate);
		return ds.rebuildDate(yyyyMMdd,horaire_depart);
	}
	

	public long getTime(Arret suivant) throws Exception {
		String debut = this.horaire;
		String fin = suivant.horaire;

		long d =  DataSearch.duration_min(fin, debut);
		if(changed) d += CHANGED_PENALTY;
		return d;
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
