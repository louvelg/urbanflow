package beeteam.urbanflow;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import beeteam.urbanflow.fte.GameEngine;

public class UrbanFlow extends GameEngine {

    @Override
    protected List<Moveset> prepareNavigation(long firstStopId, long targetStopId, Date date) {
	List<Moveset> moves = new ArrayList<Moveset>();
	// TODO
	return moves;
    }

}
