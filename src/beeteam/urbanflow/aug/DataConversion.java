package beeteam.urbanflow.aug;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import beeteam.urbanflow.aug.jsonparser.JsonParser;
import beeteam.urbanflow.aug.readtextfile.FileReadString;


public class DataConversion {
	
	
	public static final String EXT = "tab";

	
	private File inputDir;
	private File outputDir;
	
	private FileReadString frs;
	private JsonParser jp;
	private Properties prop;
	
	
	
	public DataConversion(File rootDir) throws Exception
	{
		inputDir = new File(rootDir,"input");
		outputDir = new File(rootDir,"output");
		outputDir.mkdirs();
		
		frs = new FileReadString();
		jp = new JsonParser();
		prop = new Properties();
	}
	
	
	
	
	public void convertAll() throws Exception
	{
		File[] ff = inputDir.listFiles();
		for(File f:ff) convertFile(f);
		
		File propFile = new File(outputDir,"id_name.properties");
		FileOutputStream fos = new FileOutputStream(propFile);
		prop.store(fos,"");
		fos.close();
	}
	
	
	
	
	private void convertFile(File f) throws Exception
	{
		System.out.println("Converting file: "+f);
		
		String s = (String) frs.transform(f);
		Map root = (Map) jp.transform(s);
		
		String ligne = (String) root.get("track_number");
		Map schedule = (Map) root.get("schedule");
		List stops = (List) root.get("stops");
		
		analyzeStops(stops);
		analyzeSchedule(schedule,ligne);
	}
	
	
	
	
	private void analyzeStops(List stops) throws Exception
	{
		Iterator it = stops.iterator();
		while(it.hasNext())
		{
			Map m = (Map) it.next();
			String id = (String) m.get("id");
			String name = (String) m.get("name");
			String position = (String) m.get("position");
			
			if(prop.containsKey(id) && !prop.get(id).equals(name))
				throw new Exception("stop id: "+id+" has 2 names: "+name+" & "+prop.get(id));
			
			prop.put(id,name);
		}
	}
	
	
	
	
	private void analyzeSchedule(Map schedule, String ligne) throws Exception
	{
		Iterator it = schedule.keySet().iterator();
		while(it.hasNext())
		{
			String arret = (String) it.next();
			
			Map jours = (Map) schedule.get(arret);
			Iterator it1 = jours.keySet().iterator();
			while(it1.hasNext())
			{
				String jour = (String) it1.next();
				List horaires = (List) jours.get(jour);
				
				File fileArret = fileArret(jour,arret);
				File fileLigne = fileLigne(jour,ligne);
				
				appendToFileArret(fileArret,horaires,ligne);
				appendToFileLigne(fileLigne,horaires,arret);
			}
		}
	}
	
	
	
	
	
	
	private void appendToFileArret(File f, List horaires, String ligne) throws FileNotFoundException
	{
		FileOutputStream fos = new FileOutputStream(f,true);
		PrintStream p = new PrintStream(fos);
		
		for(Object horaire:horaires)
		p.println(horaire+"\t"+ligne);
		p.close();
	}
	
	private void appendToFileLigne(File f, List horaires, String arret) throws FileNotFoundException
	{
		FileOutputStream fos = new FileOutputStream(f,true);
		PrintStream p = new PrintStream(fos);
		
		for(Object horaire:horaires)
		p.println(horaire+"\t"+arret);
		p.close();
	}
	

	
	
	private File fileArret(String jour, String arret)
	{
		File dirJ = new File(new File(outputDir,"arrets"),jour);
		dirJ.mkdirs();
		return new File(dirJ,arret+"."+EXT);
	}
	
	private File fileLigne(String jour, String ligne)
	{
		File dirJ = new File(new File(outputDir,"lignes"),jour);
		dirJ.mkdirs();
		return new File(dirJ,ligne+"."+EXT);
	}	
}