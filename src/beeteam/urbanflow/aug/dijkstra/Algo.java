package beeteam.urbanflow.aug.dijkstra;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import beeteam.urbanflow.aug.DataSearch;
import beeteam.urbanflow.fte.Moveset;

public class Algo {
	
	
	public static final boolean MOCK = false;
	
	private DataSearch ds;
	
	
	public Algo(File dir) throws Exception
	{ds = new DataSearch(dir);}
	
	
	
	
	public void compute(Date date, String arret1, String arret2, Map incident, List<Moveset> moves) throws Exception
	{
		if(MOCK)
		{
			arret1 = "1226";
			arret2 = "1373";
			date = ds.rebuildDate("20150106","15:00:00");
			
			System.out.println("MOCK DATA USED !!!!!!!!");
		}
		
		System.out.println("RECHERCHE ENTRE:");
		System.out.println("- horaire: "+DataSearch.display(date));
		System.out.println("- début: "+ds.findStopDisplay(arret1));
		System.out.println("- fin: "+ds.findStopDisplay(arret2));
		System.out.println("_________________");
		
		Dijkstra dj = new Dijkstra();
		dj.algo(ds,arret1,arret2,date,moves);
	}
	
	
	
	private void bouchon()
	{
		
	}
}
