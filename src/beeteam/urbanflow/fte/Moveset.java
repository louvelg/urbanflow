package beeteam.urbanflow.fte;

import java.util.Date;

public class Moveset {
	
	String trackNumber;
	Date connection;
	long toStopId;

	public Moveset(String trackNumber, Date connection, long toStopId) {
		
		this.trackNumber = trackNumber;
		this.connection = connection;
		this.toStopId = toStopId;
	}
}