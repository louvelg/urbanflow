package beeteam.urbanflow.aug.dijkstra;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
	
	
	
	public void algo(DataSearch ds, String start, String end, Date date, List<Moveset> moves) throws Exception {
		
		this.ds = ds;
		
		String horaire = DataSearch.horaire(date);
		String jour = DataSearch.jour(date);
		
		Arret s = new Arret(jour,horaire,start,null);
		Arret e = new Arret(end);

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
		
		System.out.println(parcours.keySet().iterator().next() + "min. Liste des arrets ("+arrets.size()+") : " + arrets);
		
		
		
		Arret a_ = null;
		
		int size = arrets.size();
		if(size==0) return;
		
		for(int i=0;i<size;i++)
		{
			Arret a = arrets.get(size-i-1);
			if(a_!=null && a_.ligne!=null && !a_.ligne.equals(a.ligne))
			{
				Moveset ms = new Moveset(a_.ligne,date,Long.parseLong(a_.station));
				moves.add(ms);
			}
			a_ = a;
		}
		
		Moveset ms = new Moveset(a_.ligne,date,Long.parseLong(a_.station));
		moves.add(ms);
	}

	
	
	
	public boolean step(Arret end) throws Exception {
		//Utils.afficheParcours(parcours, null);

		Long min = getDistanceMin();
		
		Set<Arret> s = new HashSet(parcours.get(min));
		for (Arret arretPlusProche : s)
		{
			if(arretPlusProche.station.equals(end.station)) return false;

			Set<Arret> suivants = getSuivants(arretPlusProche);
			for (Arret suivant : suivants)
			{
				if(suivant == null) throw new Exception("Suivant is null !!!");
				if(suivant == arretPlusProche) throw new Exception("Suivant is arretPlusProche !!!");
				
				String suivantStr = suivant.toString();
				if(!done.contains(suivantStr))
				{
					long temp = arretPlusProche.getTemps(suivant);
					System.out.println("suivant="+suivant+" temp="+temp);
					//System.out.println(arretPlusProche.getTemps(suivant) + min);
					
					addToParcours(temp + min, suivant);
					back.put(suivant.station,arretPlusProche);
					done.add(suivantStr);
				}
			}
		}
		parcours.remove(min);
		
		System.out.println("parcours size: "+parcours.size());
		Utils.afficheParcours(parcours);
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
			String[] r = ds.findNext(arretToArray(arret));
			if(r!=null) set.add(arrayToArret(r));
		}
		
		
		Set s = ds.findConnections2(arretToArray(arret));
		Iterator it = s.iterator();
		while(it.hasNext())
		{
			String[] r = (String[]) it.next();
			set.add(arrayToArret(r));
		}
		return set;
	}
	
	
	
	
	
	private Arret arrayToArret(String[] r)
	{
		if(r==null) return null;
		return new Arret(r[0],r[1],r[2],r[3]);
	}
	
	
	private String[] arretToArray(Arret arret)
	{
		if(arret==null) return null;
		return new String[]{arret.jour,arret.horaire, arret.station,arret.ligne};
	}
}
