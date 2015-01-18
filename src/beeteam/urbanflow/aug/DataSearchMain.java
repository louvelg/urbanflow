package beeteam.urbanflow.aug;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import beeteam.urbanflow.aug.dijkstra.Algo;
import beeteam.urbanflow.aug.dijkstra.Arret;
import beeteam.urbanflow.aug.sysprint.SysPrint;

public class DataSearchMain {

	public static final SimpleDateFormat yyyyMMdd_HHmmss = new SimpleDateFormat("yyyyMMdd_HHmmss");
	
	

	public static void main(String[] args) throws Exception
	{
		File root = new File("C:\\Users\\Augustin\\Desktop\\24H");

		compute1(root);
		//test(root);
	}
	
	
	
	private static void compute1(File root) throws Exception
	{
		//Sat Nov 07 19:55:00 CET 2015
		//1341
		//1302
		Date date = yyyyMMdd_HHmmss.parse("20151107_195500");
		
		Algo algo = new Algo(root);
		algo.compute(date,"1341","1302",null,null);
	}
	
	
	
	
	private static void test(File root) throws Exception
	{
		// 12 janvier 2015 : lundi
		Date date = yyyyMMdd_HHmmss.parse("20150112_080600");
		String arret = "1048";
		String ligne = "5_A";
		
		DataSearch ds = new DataSearch(root);
		
		Set set = ds.findConnections1(date,arret,ligne);
		SysPrint.printObj(set);
		
		System.out.println("___________________");
		
		Arret next = ds.findNext(date,arret,ligne);
		SysPrint.printObj(next);
	}
}
