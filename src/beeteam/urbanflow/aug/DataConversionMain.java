package beeteam.urbanflow.aug;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import beeteam.urbanflow.aug.sysprint.SysPrint;

public class DataConversionMain {

	public static final SimpleDateFormat yyyyMMdd_HHmmss = new SimpleDateFormat("yyyyMMdd_HHmmss");
	
	

	public static void main(String[] args) throws Exception
	{
		File root = new File("C:\\Users\\Augustin\\Desktop\\24H");
		
		DataConversion dc = new DataConversion(root);
		//dc.convertAll();
		
		DataSearch ds = new DataSearch(root);
		
		
		// 12 janvier 2015 : lundi
		Date date = yyyyMMdd_HHmmss.parse("20150112_080600");
		String arret = "1048";
		String ligne = "5_A";
		
		
		Set set = ds.findConnections(date,arret,ligne);
		SysPrint.printObj(set);
	}
}
