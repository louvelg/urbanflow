package beeteam.urbanflow.aug.main;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import beeteam.urbanflow.aug.dijkstra.Algo;
import beeteam.urbanflow.fte.GameEngine;

public class UrbanFlow extends GameEngine {

	protected List<Moveset> prepareNavigation(long firstStopId, long targetStopId, Date date, @SuppressWarnings("rawtypes") Map incidents)
    {
		List<Moveset> moves = new ArrayList<Moveset>();
    	
		try
		{
			Algo algo = new Algo(new File("C:\\Users\\Augustin\\Desktop\\24H"));
			algo.compute(date, ""+firstStopId, ""+targetStopId);
		}
		catch(Exception e) {e.printStackTrace();}
		return moves;
    }

}
