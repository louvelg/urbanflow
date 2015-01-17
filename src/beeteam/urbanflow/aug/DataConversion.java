package beeteam.urbanflow.aug;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import beeteam.urbanflow.aug.jsonparser.JsonParser;
import beeteam.urbanflow.aug.readtextfile.FileReadString;
import beeteam.urbanflow.aug.weekday.CalendarTool;


public class DataConversion {
	
	
	public static final SimpleDateFormat HHmmss = new SimpleDateFormat("HH:mm:ss");
	
	public static final String[] DAYS = new String[]{"lu","ma","me","je","ve","sa","di"};
	public static final String EXT = "tab";

	
	private File rootDir;
	private File inputDir;
	private File outputDir;
	
	private FileReadString frs;
	private JsonParser jp;
	
	
	public DataConversion(File rootDir) throws Exception
	{
		this.rootDir = rootDir;
		
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	public Set findConnections(Date date, String arret, String ligne) throws Exception
	{
		Set set = new HashSet();
		Set set_ligne = new HashSet();
		
		String jour = DAYS[CalendarTool.dayOfWeek_index(date)-1];
		String horaire = HHmmss.format(date);
		
		System.out.println("jour:"+jour+" horaire:"+horaire+" arret:"+arret+" ligne:"+ligne);
		
		
		File fileArret = fileArret(jour,arret);
		File fileLigne = fileLigne(jour,ligne);
		
		if(!fileArret.exists()) throw new Exception("File not found: "+fileArret);
		if(!fileLigne.exists()) throw new Exception("File not found: "+fileLigne);
		
		
		
		String s = (String) frs.transform(fileArret);
		String[] lines = s.trim().split("\n");
		List lines_ = Arrays.asList(lines);
		Collections.sort(lines_);
		
		String startLine = horaire+"\t"+ligne;
		//System.out.println("startLine: "+startLine);
		boolean started = false;
		
		for(int i=0;i<lines_.size();i++)
		{
			String line = ((String) lines_.get(i)).trim();
			//System.out.println("line: "+line);
			if(!started) started = line.equals(startLine);
			else
			{
				String[] t = line.split("\t");
				
				String horaire0 = t[0];
				String ligne0 = t[1];
				
				if(!set_ligne.contains(ligne0))
				{
					set_ligne.add(ligne0);
					set.add(new String[]{jour,horaire0,arret,ligne0});
				}
			}
		}
        
		return set;
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