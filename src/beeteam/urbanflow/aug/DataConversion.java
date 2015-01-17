package beeteam.urbanflow.aug;

import java.io.File;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import beeteam.urbanflow.aug.jsonparser.JsonParser;
import beeteam.urbanflow.aug.readtextfile.FileReadString;

public class DataConversion {

	
	
	private File inputDir;
	private File outputDir;
	
	private FileReadString frs;
	private JsonParser jp;
	
	
	public DataConversion(File inputDir, File outputDir) throws Exception
	{
		this.inputDir = inputDir;
		this.outputDir = outputDir;
		
		outputDir.mkdirs();
		
		frs = new FileReadString();
		jp = new JsonParser();
	}
	
	
	
	
	public void convertAll() throws Exception
	{
		File[] ff = inputDir.listFiles();
		for(File f:ff) convertFile(f);
	}
	
	
	
	
	private void convertFile(File f) throws Exception
	{
		String s = (String) frs.transform(f);
		Map m = (Map) jp.transform(s);
		
		String ligne = (String) m.get("track_number");
		
		Map schedule = (Map) m.get("schedule");
		Iterator it = schedule.keySet().iterator();
		
		while(it.hasNext())
		{
			String id = (String) it.next();
			Map jours = (Map) schedule.get(id);
			Iterator it1 = jours.keySet().iterator();
			while(it1.hasNext())
			{
				String jour = (String) it1.next();
				
				File file = new File(outputDir,jour+"_"+id+".txt");
				PrintStream p = new PrintStream(file);
				p.println("Horaire\tLigne");
				
				List horaires = (List) jours.get(jour);
				for(Object horaire:horaires)
				{
					p.println(horaire+"\t"+ligne);
				}
				p.close();
			}
		}
	}
	
	
	
	
	private void printMap(Map m)
	{
		Iterator it = m.keySet().iterator();
		while(it.hasNext())
		{
			String key = (String) it.next();
			System.out.println(key);
		}
	}
	
}