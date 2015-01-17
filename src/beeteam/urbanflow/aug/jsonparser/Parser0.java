package beeteam.urbanflow.aug.jsonparser;

public abstract class Parser0 {
	
	
	private boolean debug;
	
	private String data;
	private int index;
	
	
	
	
	
	public Parser0(boolean debug)
	{this.debug = debug;}
	
	
	protected abstract Object parse_object();
	
	
	
	
	
	public Object parse(String input) throws Exception
	{
		init(input);
		return parse_object();
	}
	
	
	
	private void init(String data)
	{
		debug("init():\n"+data+"\n\n");
		
		this.data = data;
		index = 0;
	}
	
	
	protected char get()
	{return data.charAt(index-1);}

	
	protected int index()
	{return index;}
	
	
	
	protected boolean goToNext()
	{
		if(index==data.length()) return false;
		index++;
		return true;
	}
	
	
	protected boolean goToPrevious()
	{
		if(index==0) return false;
		index--;
		return true;
	}
	
	
	
	protected Object reset(int index, String cause)
	{
		debug("reset at "+index+" ("+cause+")");
		this.index = index;
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	protected Object done(Object s)
	{
		if(debug) System.out.println("PARSED_ELEMENT=["+s+"]");
		return s;
	}
	
	
	
	
	protected void debug(String info)
	{if(debug) System.out.println(info);}
}
