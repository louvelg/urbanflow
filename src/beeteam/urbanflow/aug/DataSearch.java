package beeteam.urbanflow.aug;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import beeteam.urbanflow.aug.readtextfile.FileReadString;
import beeteam.urbanflow.aug.weekday.CalendarTool;

public class DataSearch {

	
	public static final SimpleDateFormat HHmmss = new SimpleDateFormat("HH:mm:ss");
	
	public static final String[] DAYS = new String[]{"lu","ma","me","je","ve","sa","di"};
	public static final String EXT = "tab";

	
	private File outputDir;
	
	private FileReadString frs;
	
	
	public DataSearch(File rootDir) throws Exception
	{
		outputDir = new File(rootDir,"output");
		outputDir.mkdirs();
		
		frs = new FileReadString();
	}
	
	
	
	
	
	public Set findConnections(Date date, String arret, String ligne) throws Exception
	{return findConnections(jour(date),horaire(date),arret,ligne);}
	
	
	public String[] findNext(Date date, String arret, String ligne) throws Exception
	{return findNext(jour(date),horaire(date),arret,ligne);}
	
	
	
	
	
	
	
	public Set findConnections(String jour, String horaire, String arret, String ligne) throws Exception
	{
		Set set = new HashSet();
		Set set_ligne = new HashSet();
		
		System.out.println("find connections");
		System.out.println("jour:"+jour+" horaire:"+horaire+" arret:"+arret+" ligne:"+ligne);
		
		File f = fileArret(jour,arret);
		if(!f.exists()) throw new Exception("File not found: "+f);
		List lines = fileToList(f);
		
		String startLine = horaire+"\t"+ligne;
		boolean started = false;
		
		for(int i=0;i<lines.size();i++)
		{
			String line = ((String) lines.get(i)).trim();
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
	
	
	
	

	
	
	public String[] findNext(String jour, String horaire, String arret, String ligne) throws Exception
	{
		System.out.println("find next stop");
		
		File f = fileLigne(jour,ligne);
		if(!f.exists()) throw new Exception("File not found: "+f);
		List lines = fileToList(f);
		
		String startLine = horaire+"\t"+arret;
		boolean isNext = false;
		
		for(int i=0;i<lines.size();i++)
		{
			String line = ((String) lines.get(i)).trim();
			if(!isNext) isNext = line.equals(startLine);
			else
			{
				String[] t = line.split("\t");
				
				String horaire0 = t[0];
				String arret0 = t[1];
				
				return new String[]{jour,horaire0,arret0,ligne};
			}
		}
		
		return null;
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
	
	
	private String jour(Date date)
	{return DAYS[CalendarTool.dayOfWeek_index(date)-1];}
	
	
	private String horaire(Date date) throws Exception
	{return HHmmss.format(date);}
	

	
	
	
	private List fileToList(File f) throws Exception
	{
		String s = (String) frs.transform(f);
		String[] lines = s.trim().split("\n");
		List list = Arrays.asList(lines);
		Collections.sort(list);
		return list;
	}
}
