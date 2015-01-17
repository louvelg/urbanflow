package beeteam.urbanflow.aug;

import java.io.File;
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
		System.out.println("ligne: "+ligne);
		
		Iterator it = m.keySet().iterator();
		while(it.hasNext())
		{
			String id = (String) it.next();
			System.out.println("id: "+id);
			//List l1 = (List) m.get(id);
			System.out.println(": "+m.get(id).getClass());
		}
		
		Object schedule = m.get("schedule");
		System.out.println("schedule type: "+schedule.getClass());
		
		System.out.println("File: "+f);
		printMap(m);
		
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