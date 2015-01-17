package beeteam.urbanflow.aug;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import beeteam.urbanflow.aug.jsonparser.JsonParser;
import beeteam.urbanflow.aug.readtextfile.FileReadString;


public class DataConversion {
	
	
	public static final String EXT = "tab";

	
	private File inputDir;
	private File outputDir;
	
	private FileReadString frs;
	private JsonParser jp;
	
	
	
	public DataConversion(File rootDir) throws Exception
	{
		inputDir = new File(rootDir,"input");
		outputDir = new File(rootDir,"output");
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
		System.out.println("Converting file: "+f);
		
		String s = (String) frs.transform(f);
		Map root = (Map) jp.transform(s);
		
		String ligne = (String) root.get("track_number");
		Map schedule = (Map) root.get("schedule");
		
		
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