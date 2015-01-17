package beeteam.urbanflow.aug;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import beeteam.urbanflow.aug.dijkstra.Algo;
import beeteam.urbanflow.aug.sysprint.SysPrint;

public class DataConversionMain {

	public static final SimpleDateFormat yyyyMMdd_HHmmss = new SimpleDateFormat("yyyyMMdd_HHmmss");
	
	

	public static void main(String[] args) throws Exception
	{
		File root = new File("C:\\Users\\Augustin\\Desktop\\24H");

		compute1(root);
		//test(root);
		//convert(root);
		
	}
	
	
	
	private static void compute1(File root) throws Exception
	{
		Algo algo = new Algo(root);
		//algo.compute(arret1, arret2, date);
	}
	
	
	
	private static void convert(File root) throws Exception
	{
		DataConversion dc = new DataConversion(root);
		dc.convertAll();
	}
	
	
	private static void test(File root) throws Exception
	{
		// 12 janvier 2015 : lundi
		Date date = yyyyMMdd_HHmmss.parse("20150112_080600");
		String arret = "1048";
		String ligne = "5_A";
		
		DataSearch ds = new DataSearch(root);
		
		Set set = ds.findConnections(date,arret,ligne);
		SysPrint.printObj(set);
		
		System.out.println("___________________");
		
		String[] next = ds.findNext(date,arret,ligne);
		SysPrint.printObj(next);
	}
}
