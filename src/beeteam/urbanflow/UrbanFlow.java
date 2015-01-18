package beeteam.urbanflow;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import beeteam.urbanflow.fte.GameEngine;
import beeteam.urbanflow.fte.Moveset;

public class UrbanFlow extends GameEngine {

	@Override
	protected List<Moveset> prepareNavigation(long firstStopId, long targetStopId, Date date, @SuppressWarnings("rawtypes") Map incidents) {
		List<Moveset> moves = new ArrayList<Moveset>();
		// TODO
		return moves;
	}

}
