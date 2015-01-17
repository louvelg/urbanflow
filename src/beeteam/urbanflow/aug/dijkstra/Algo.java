package beeteam.urbanflow.aug.dijkstra;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import beeteam.urbanflow.aug.DataSearch;
import beeteam.urbanflow.aug.sysprint.SysPrint;

public class Algo {
	
	
	private DataSearch ds;
	
	
	public Algo(File dir) throws Exception
	{ds = new DataSearch(dir);}
	
	
	
	
	public void compute(Date date, String arret1, String arret2) throws Exception
	{
		System.out.println("RECHERCHE ENTRE:");
		System.out.println("- horaire: "+DataSearch.display(date));
		System.out.println("- début: "+ds.findStopDisplay(arret1));
		System.out.println("- fin: "+ds.findStopDisplay(arret2));
		System.out.println("_________________");
		
		Map map1 = new HashMap();
		
		Set set = (Set) ds.findConnections2(date,arret1,null);
		
		System.out.println("first stops found: "+set.size());
		System.out.println("_________________");
		
		Iterator it = set.iterator();
		while(it.hasNext())
		{
			String[] p = (String[]) it.next();
			SysPrint.printInfos(ds,p);
			System.out.println("_________________");
			Long duration = duration_min(p,date);
			add(map1,duration,p);
		}
		
		
	}
	
	
	
	private void add(Map map, Object key, Object value)
	{
		if(!map.containsKey(key))
			map.put(key,new HashSet());
		Set set = (Set) map.get(key);
		set.add(value);
	}
	
	
	
	
	private Long duration_min(String[] p, Date date0) throws Exception
	{
		String jour1 = p[0];
		String horaire1 = p[1];
		
		if(!jour1.equals(DataSearch.jour(date0)))
			throw new Exception("day difference have been detected");
		
		String yyyyMMdd0 = DataSearch.yyyyMMdd(date0);
		Date date1 = DataSearch.rebuildDate(yyyyMMdd0,horaire1);
		
		return DataSearch.duration_min(date1,date0);
	}
	
}
