package beeteam.urbanflow.aug.jsonparser;


public class JsonParser {


    public String getName()				{return "gus.file.convert.json.parser";}
    public String getCreationDate()		{return "2013.03.14";}

    
    public static final boolean DEBUG = false;
    
    

    private Parser2 parser;
    

    public JsonParser() throws Exception
    {
    	parser = new Parser2(DEBUG);
    }



    public Object transform(Object obj) throws Exception
    {
    	String in = (String) obj;
    	return parser.parse(clean(in));
    }
    
    
    
    
    
    private String clean(String in)
    {
    	return in.trim().replace("\n","").replace("\t","").replace("\r","").replace("\b","").replace("\f","");
    }
}

