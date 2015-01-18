package beeteam.urbanflow.aug;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.Properties;

import beeteam.urbanflow.aug.jsonparser.JsonParser;
import beeteam.urbanflow.aug.readtextfile.FileReadString;

public class Data {

	
	public static final String EXT = "tab";

	
	protected File inputDir;
	protected File outputDir;
	
	protected FileReadString frs;
	protected JsonParser jp;
	protected Properties id_name;
	
	
	
	public Data(File rootDir) throws Exception
	{
		inputDir = new File(rootDir,"input");
		outputDir = new File(rootDir,"output");
		outputDir.mkdirs();
		
		id_name = new Properties();
		frs = new FileReadString();
		jp = new JsonParser();
	}
	
	
	
	
	protected Properties load(File f) throws Exception
	{
		Properties p = new Properties();
		FileInputStream fis = new FileInputStream(f);
		p.load(fis);
		fis.close();
		return p;
	}
	
	
	
	protected void store(File f, Properties p) throws Exception
	{
		f.getParentFile().mkdirs();
		FileOutputStream fos = new FileOutputStream(f);
		p.store(fos,"");
		fos.close();
	}
	
	
	
	protected String read(File f) throws Exception
	{return (String) frs.transform(f);}
	
	
	protected Map parseJson(File f) throws Exception
	{return (Map) jp.transform(read(f));}
	
	
	
	
	protected File fileArret(String jour, String arret)
	{
		File dir = new File(new File(outputDir,"arrets"),jour);
		dir.mkdirs();
		return new File(dir,arret+"."+EXT);
	}
	
	protected File fileLigne(String jour, String ligne)
	{
		File dir = new File(new File(outputDir,"lignes"),jour);
		dir.mkdirs();
		return new File(dir,ligne+"."+EXT);
	}
	
	protected File fileStops(String ligne)
	{
		File dir = new File(outputDir,"stops");
		dir.mkdirs();
		return new File(dir,ligne+".properties");
	}
}
