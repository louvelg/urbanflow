package beeteam.urbanflow.aug.dijkstra;

import java.io.File;
import java.util.Date;
import beeteam.urbanflow.aug.DataSearch;

public class Algo {
	
	
	private DataSearch ds;
	
	
	public Algo(File dir) throws Exception
	{ds = new DataSearch(dir);}
	
	
	
	
	public void compute(Date date, String arret1, String arret2) throws Exception
	{
		arret1 = "1226";
		arret2 = "1373";
		date = ds.rebuildDate("20150105","15:00:00");
		
		System.out.println("RECHERCHE ENTRE:");
		System.out.println("- horaire: "+DataSearch.display(date));
		System.out.println("- début: "+ds.findStopDisplay(arret1));
		System.out.println("- fin: "+ds.findStopDisplay(arret2));
		System.out.println("_________________");
		
		Dijkstra dj = new Dijkstra();
		dj.algo(ds,arret1,arret2, DataSearch.horaire(date), DataSearch.jour(date));
	}
}
