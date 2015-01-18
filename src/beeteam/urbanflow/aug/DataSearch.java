package beeteam.urbanflow.aug;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import beeteam.urbanflow.aug.weekday.CalendarTool;


public class DataSearch extends Data {

	
	public static final SimpleDateFormat HHmmss = new SimpleDateFormat("HH:mm:ss");
	public static final SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
	public static final SimpleDateFormat yyyyMMdd_HHmmss = new SimpleDateFormat("yyyyMMdd_HH:mm:ss");
	
	public static final String[] DAYS = new String[]{"lu","ma","me","je","ve","sa","di"};
	
	
	public static String jour(Date date)
	{
		int day = CalendarTool.dayOfWeek_(date);
		switch(day) {
			case Calendar.MONDAY: return "lu";
			case Calendar.TUESDAY: return "ma";
			case Calendar.WEDNESDAY: return "me";
			case Calendar.THURSDAY: return "je";
			case Calendar.FRIDAY: return "ve";
			case Calendar.SATURDAY: return "sa";
			case Calendar.SUNDAY: return "di";
		}
		return null;
	}
	
	
	public static String horaire(Date date) throws Exception
	{return HHmmss.format(date);}
	
	public static String display(Date date) throws Exception
	{return jour(date)+" "+horaire(date);}
	
	public static String yyyyMMdd(Date date) throws Exception
	{return yyyyMMdd.format(date);}
	
	public static Date rebuildDate(String yyyyMMdd, String horaire) throws Exception
	{return yyyyMMdd_HHmmss.parse(yyyyMMdd+"_"+horaire);}

	
	public static Long duration_min(String horaire1, String horaire0) throws Exception
	{
		Date d1 = rebuildDate("20150101",horaire1);
		Date d0 = rebuildDate("20150101",horaire0);
		return duration_min(d1,d0);
	}
	
	public static Long duration_min(Date date1, Date date0)
	{
		long t = date1.getTime()-date0.getTime();
		return t/60000;
	}
	
	
	
	
	
	
	
	public DataSearch(File rootDir) throws Exception
	{
		super(rootDir);
		
		id_name = load(new File(outputDir,"id_name.properties"));
	}
	
	
	
	
	public String findStopName(String id) throws Exception
	{
		if(!id_name.containsKey(id))
			throw new Exception("Stop id not found: "+id);
		return id_name.getProperty(id);
	}
	
	public String findStopDisplay(String id) throws Exception
	{return findStopName(id)+" ["+id+"]";}



	
	public String[] findNext(Date date, String arret, String ligne) throws Exception
	{return findNext(jour(date),horaire(date),arret,ligne);}
	
	public Set findConnections2(Date date, String arret, String ligne) throws Exception
	{return findConnections2(jour(date),horaire(date),arret,ligne);}
	
	public Set findConnections1(Date date, String arret, String ligne) throws Exception
	{return findConnections1(jour(date),horaire(date),arret,ligne);}


	

	public String[] findNext(String[] infos) throws Exception
	{return findNext(infos[0],infos[1],infos[2],infos[3]);}
	
	public Set findConnections2(String[] infos) throws Exception
	{return findConnections2(infos[0],infos[1],infos[2],infos[3]);}
	
	public Set findConnections1(String[] infos) throws Exception
	{return findConnections1(infos[0],infos[1],infos[2],infos[3]);}
	
	
	
	
	
	
	
	
	
	
	public Set findConnections2(String jour, String horaire, String arret, String ligne) throws Exception
	{
		Set connections = findConnections1(jour,horaire,arret,ligne);
		Set set = new HashSet();
		Iterator it = connections.iterator();
		while(it.hasNext())
		{
			String[] v = (String[]) it.next();
			String[] vv = (String[]) findNext(v);
			if(vv!=null) set.add(vv);
		}
		return set;
	}

	
	
	public Set findConnections1(String jour, String horaire, String arret, String ligne) throws Exception
	{
		Set set = new HashSet();
		Set set_ligne = new HashSet();
		
		//System.out.println("find connections");
		//System.out.println("jour:"+jour+" horaire:"+horaire+" arret:"+arret+" ligne:"+ligne);
		
		File f = fileArret(jour,arret);
		if(!f.exists()) throw new Exception("File not found: "+f);
		
		List lines = fileToList(f);
		if(ligne==null)
		{
			ligne = "@";
			lines.add(horaire+"\t"+ligne);
		}
		Collections.sort(lines);
		
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
		String nextArret = getNextStop(ligne,arret);
		
		File f = fileLigne(jour,ligne);
		if(!f.exists()) throw new Exception("File not found: "+f);
		
		List lines = fileToList(f);
		
		String startLine = horaire+"\t"+nextArret;
		lines.add(startLine);
		Collections.sort(lines);
		
		boolean isAfter = false;
		
		for(int i=0;i<lines.size();i++)
		{
			String line = ((String) lines.get(i)).trim();
			if(!isAfter) isAfter = line.equals(startLine);
			else
			{
				String[] t = line.split("\t");
				
				String horaire0 = t[0];
				String arret0 = t[1];
				
				if(arret0.equals(nextArret))
				return new String[]{jour,horaire0,arret0,ligne};
			}
		}
		return null;
	}
	
	
	

	
	
	
	private List fileToList(File f) throws Exception
	{
		String s = read(f);
		String[] lines = s.trim().split("\n");
		return new ArrayList(Arrays.asList(lines));
	}
	
	
	
	
	
	
	public String getNextStop(String ligne, String arret) throws Exception
	{
		File f = fileStops(ligne);
		Properties p = load(f);
		
		if(p.containsKey(arret))
			return p.getProperty(arret);
		return null;
	}
}
