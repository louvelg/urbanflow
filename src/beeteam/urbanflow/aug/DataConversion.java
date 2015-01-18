package beeteam.urbanflow.aug;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;


public class DataConversion extends Data {
	
	
	
	
	
	public DataConversion(File rootDir) throws Exception
	{
		super(rootDir);
	}
	
	
	
	
	
	
	public void convertAll() throws Exception
	{
		File[] ff = inputDir.listFiles();
		for(File f:ff) convertFile(f);
		
		store(new File(outputDir,"id_name.properties"),id_name);
	}
	
	
	
	
	private void convertFile(File f) throws Exception
	{
		System.out.println("Converting file: "+f);
		Map root = parseJson(f);
		
		String ligne = (String) root.get("track_number");
		Map schedule = (Map) root.get("schedule");
		List stops = (List) root.get("stops");
		
		analyzeStops(stops,ligne);
		analyzeSchedule(schedule,ligne);
	}
	
	
	
	
	private void analyzeStops(List stops, String ligne) throws Exception
	{
		Properties p = new Properties();
		
		String previousId = null;
		
		for(int i=0;i<stops.size();i++)
		{
			Map m = (Map) stops.get(i);
			String id = (String) m.get("id");
			String name = (String) m.get("name");
			
			if(previousId!=null)
				p.put(previousId,id);
			previousId = id;
			
			if(id_name.containsKey(id) && !id_name.get(id).equals(name))
				throw new Exception("stop id: "+id+" has 2 names: "+name+" & "+id_name.get(id));
			id_name.put(id,name);
		}
		
		store(fileStops(ligne),p);
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
	

	
	
	
}