package beeteam.urbanflow.aug;

import java.io.File;
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
	
	
	public static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	
	public static final String[] DAYS = new String[]{"lu","ma","me","je","ve","sa","di"};
	public static final String EXT = "tab";

	
	private File rootDir;
	private File inputDir;
	private File outputDir;
	
	private PrintStream all;
	
	
	private FileReadString frs;
	private JsonParser jp;
	
	
	public DataConversion(File rootDir) throws Exception
	{
		this.rootDir = rootDir;
		
		inputDir = new File(rootDir,"input");
		outputDir = new File(rootDir,"output");
		
		outputDir.mkdirs();
		
		all = new PrintStream(new File(outputDir,"all.txt"));
		
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
			String id_arret = (String) it.next();
			
			Map jours = (Map) schedule.get(id_arret);
			Iterator it1 = jours.keySet().iterator();
			while(it1.hasNext())
			{
				String jour = (String) it1.next();
				File file = file(jour,id_arret);
				
				FileOutputStream fos = new FileOutputStream(file,true);
				PrintStream p = new PrintStream(fos);
				
				List horaires = (List) jours.get(jour);
				for(Object horaire:horaires)
				{
					p.println(horaire+"\t"+ligne);
					all.println(jour+"\t"+horaire+"\t"+id_arret+"\t"+ligne);
				}
				p.close();
			}
		}
	}
	
	
	
	
	
	
	
	public Set findConnections(Date date, String id_arret, String ligne) throws Exception
	{
		Set set = new HashSet();
		Set set_ligne = new HashSet();
		
		String jour = DAYS[CalendarTool.dayOfWeek_index(date)];
		String horaire = sdf.format(date);
		
		
		File file = file(jour,id_arret);
		if(!file.exists()) throw new Exception("File not found: "+file);
		
		String s = (String) frs.transform(file);
		String[] lines = s.trim().split("\n");
		List lines_ = Arrays.asList(lines);
		Collections.sort(lines_);
		
		String startLine = horaire+"\t"+ligne;
		boolean started = false;
		
		for(int i=0;i<lines_.size();i++)
		{
			String line = (String) lines_.get(i);
			if(!started) started = line.equals(startLine);
			else
			{
				String[] t = line.split("\t");
				
				String horaire0 = t[0];
				String ligne0 = t[1];
				
				if(!set_ligne.contains(ligne0))
				{
					set_ligne.add(ligne0);
					set.add(new String[]{jour,horaire0,id_arret,ligne0});
				}
			}
		}
        
		return set;
	}
	
	
	
	
	
	
	private File file(String jour, String arret)
	{
		File dirJ = new File(outputDir,jour);
		dirJ.mkdirs();
		return new File(dirJ,arret+"."+EXT);
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