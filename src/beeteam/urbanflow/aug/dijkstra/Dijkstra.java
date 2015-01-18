package beeteam.urbanflow.aug.dijkstra;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import beeteam.urbanflow.aug.DataSearch;
import beeteam.urbanflow.fte.Moveset;

public class Dijkstra {

	private Map<Long, Set<Arret>> parcours;
	private Map back;
	private Set<String> done;
	
	private DataSearch ds;
	
	
	
	public void algo(DataSearch ds, String start, String end, Date initDate, List<Moveset> moves) throws Exception {
		
		this.ds = ds;

		
		String horaire = DataSearch.horaire(initDate);
		String jour = DataSearch.jour(initDate);
		
		Arret s = new Arret(ds,jour,horaire,start,null,false);
		Arret e = new Arret(ds,end);

		parcours = new HashMap<Long, Set<Arret>>();
		back = new HashMap<>();
		done = new HashSet<>();
		
		addToParcours(0L, s);
		done.add(s.toString());
		
		while (step(e)) {}

		
		
		Arret c = e;
		List<Arret> arrets = new ArrayList<>();
		arrets.add(c);
		
		while(back.containsKey(c.station))
		{
			c = (Arret) back.get(c.station);
			arrets.add(c);
		}
		
		Long finalTime = parcours.keySet().iterator().next();
		System.out.println("over "+ finalTime + " min");
		System.out.println("Liste des arrets ("+arrets.size()+") : ");
		System.out.println();
		
		for(int i=0;i<arrets.size();i++)
			System.out.println(i+"- "+arrets.get(arrets.size()-1-i).display());

		System.out.println();
		
		
		int size = arrets.size();
		if(size==0) return;
		
		
		Arret a_ = null;
		Date d_ = null;
		
		for(int i=0;i<size;i++)
		{
			Arret a = arrets.get(size-i-1);
			if(d_==null) d_ = a.buildDateDepart(initDate);
			
			if(a_!=null && a_.ligne!=null && !a_.ligne.equals(a.ligne))
			{
				System.out.println("station id: "+ds.findStopDisplay(a_.station)+" track id: "+a_.ligne);
				Moveset ms = new Moveset(a_.ligne,d_,Long.parseLong(a_.station));
				moves.add(ms);
				
				d_ = a.buildDateDepart(initDate);
			}
			a_ = a;
		}
		
		Moveset ms = new Moveset(a_.ligne,d_,Long.parseLong(a_.station));
		moves.add(ms);
	}

	
	
	
	
	
	
	public boolean step(Arret end) throws Exception {
		//Utils.afficheParcours(parcours, null);
		
		if(parcours.isEmpty())
			throw new Exception("Target not found");

		Long min = getDistanceMin();
		//System.out.println("min="+min);
		
		Set<Arret> s = new HashSet(parcours.get(min));
		for (Arret arretPlusProche : s)
		{
			if(arretPlusProche.station.equals(end.station))
			{
				end.horaire = arretPlusProche.horaire;
				end.jour = arretPlusProche.jour;
				end.ligne = arretPlusProche.ligne;
				end.fullTime = arretPlusProche.fullTime;
				return false;
			}

			Set<Arret> suivants = getSuivants(arretPlusProche);
			for (Arret suivant : suivants)
			{
				if(suivant == null) throw new Exception("Suivant is null !!!");
				if(suivant == arretPlusProche) throw new Exception("Suivant is arretPlusProche !!!");

				String suivantStr = suivant.toString();
				if(!done.contains(suivantStr))
				{
					long time = arretPlusProche.getTime(suivant);
					//System.out.println("suivant="+suivant+" temp="+temp);
					//System.out.println(arretPlusProche.getTemps(suivant) + min);
					
					long fullTime = time + min;
					suivant.fullTime = fullTime;
					addToParcours(fullTime, suivant);
					
					back.put(suivant.station,arretPlusProche);
					done.add(suivantStr);
				}
			}
		}
		parcours.remove(min);
		
		//System.out.println("parcours size: "+parcours.size());
		//Utils.afficheParcours(parcours);
		//Utils.afficheBack2(back2);

		return true;
	}

	public void addToParcours(Long l, Arret a)
	{
		if (!parcours.containsKey(l)) 
			parcours.put(l,new HashSet<Arret>());
		
		Set<Arret> hs = parcours.get(l);
		hs.add(a);
	}

	

	public Long getDistanceMin() {
		Long result = Long.MAX_VALUE;
		for (Long key : parcours.keySet()) {
			if(key < result) result = key;
		}
		return result;
	}
	
	
	
	private Set<Arret> getSuivants(Arret arret) throws Exception
	{
		Set<Arret> set = new HashSet<>();
		
		if(arret.ligne!=null)
		{
			Arret r = ds.findNext(arret);
			if(r!=null) set.add(r);
		}
		
		
		Set<Arret> s = ds.findConnections2(arret);
		set.addAll(s);
		
		return set;
	}
}
