package beeteam.urbanflow.aug.jsonparser;

public abstract class Parser1 extends Parser0 {

	
	
	public Parser1(boolean debug)
	{super(debug);}
	
	
	
	
	
	
	
	
	
	/*
	 * chars = char*
	 */
	protected Object parse_chars()
	{
		int n0 = index();
		debug("parse_chars() at "+n0);
		
		StringBuffer b = new StringBuffer();
		
		String char_ = (String) parse_char();
		while(char_!=null)
		{
			b.append(char_);
			char_ = (String) parse_char();
		}
		return b.toString();
	}
	
	
	
	
	
	protected Object parse_blank()
	{
		int n0 = index();
		debug("parse_blank() at "+n0);
		
		StringBuffer b = new StringBuffer();
		while(goToNext() && get()==' ') b.append(' ');
		
		goToPrevious();
		return b.toString();
	}
	
	
	
	
	
	
	/*
	 * char = 
	 */
	private Object parse_char()
	{
		int n0 = index();
		debug("parse_char() at "+n0);
		
		if(!goToNext()) return reset(n0,"");
		if(get()=='"') return reset(n0,"");
		
		if(get()=='\\')
		{
			if(!goToNext()) return reset(n0,"");
			if(get()=='"') return "\"";
			if(get()=='\\') return "\\";
			if(get()=='/') return "/";
			if(get()=='b') return "\b";
			if(get()=='f') return "\f";
			if(get()=='n') return "\n";
			if(get()=='r') return "\r";
			if(get()=='t') return "\t";
			if(get()=='u')
			{
				if(!goToNext()) return reset(n0,"");
				char u1 = get();
				if(!goToNext()) return reset(n0,"");
				char u2 = get();
				if(!goToNext()) return reset(n0,"");
				char u3 = get();
				if(!goToNext()) return reset(n0,"");
				char u4 = get();
				
				String hexValue = new String(new char[]{u1,u2,u3,u4});
				try
				{
					int codePoint = Integer.parseInt(hexValue,16);
					return new String(Character.toChars(codePoint));
				}
				catch(NumberFormatException nfe)
				{return reset(n0,"");}
			}
		}
		
		return ""+get();
	}
	
	
	
	
	
	/*
	 * number = 
	 * entier
	 * entier frac
	 * entier exp
	 * entier frac exp 
	 */
	protected Object parse_number()
	{
		debug("parse_number()");
		int n0 = index();

		String integer = (String) parse_integer();
		if(integer!=null)
		{
			String frac = (String) parse_frac();
			if(frac!=null)
			{
				String exp = (String) parse_exp();
				if(exp!=null) return done(integer+frac+exp);
				return done(integer+frac);
			}
			String exp = (String) parse_exp();
			if(exp!=null) return done(integer+exp);
			return done(integer);
		}
		
		return reset(n0,"");
	}
	
	
	
	
	
	
	private Object parse_integer()
	{
		debug("parse_integer()");
		int n0 = index();
		
		StringBuffer b = new StringBuffer();
		
		if(!goToNext()) return reset(n0,"");
		if(get()=='-')
		{
			b.append('-');
			if(!goToNext()) return reset(n0,"");
		}
		
		if(!Character.isDigit(get()))  return reset(n0,"");
		b.append(get());
		
		while(goToNext() && Character.isDigit(get()))
			b.append(get());
		goToPrevious();
		
		return b.toString();
	}
	
	
	
	
	private Object parse_frac()
	{
		int n0 = index();
		
		StringBuffer b = new StringBuffer();
		
		if(!goToNext()) return reset(n0,"");
		if(get()!='.') return reset(n0,"");
		b.append('.');
		
		if(!goToNext()) return reset(n0,"");
		if(!Character.isDigit(get()))  return reset(n0,"");
		b.append(get());
		
		while(goToNext() && Character.isDigit(get()))
			b.append(get());
		goToPrevious();
		
		return b.toString();
	}
	
	
	
	
	
	private Object parse_exp()
	{
		int n0 = index();
		
		StringBuffer b = new StringBuffer();
		
		if(!goToNext()) return reset(n0,"");
		if(get()!='e' && get()!='E') return reset(n0,"");
		b.append(get());
		
		if(!goToNext()) return reset(n0,"");
		if(get()=='-')
		{
			b.append('-');
			if(!goToNext()) return reset(n0,"");
		}
		else if(get()=='+')
		{
			b.append('+');
			if(!goToNext()) return reset(n0,"");
		}
		
		if(!Character.isDigit(get()))  return reset(n0,"");
		b.append(get());
		
		while(goToNext() && Character.isDigit(get()))
			b.append(get());
		
		return b.toString();
	}
	
	
	
	
	
	
	
	
	protected Object parse_true()
	{
		int n0 = index();
		
		if(!goToNext()) return reset(n0,"");
		if(get()!='t') return reset(n0,"");
		
		if(!goToNext()) return reset(n0,"");
		if(get()!='r') return reset(n0,"");
		
		if(!goToNext()) return reset(n0,"");
		if(get()!='u') return reset(n0,"");
		
		if(!goToNext()) return reset(n0,"");
		if(get()!='e') return reset(n0,"");
		
		return Boolean.TRUE;
	}
	
	
	
	
	protected Object parse_false()
	{
		int n0 = index();
		
		if(!goToNext()) return reset(n0,"");
		if(get()!='f') return reset(n0,"");
		
		if(!goToNext()) return reset(n0,"");
		if(get()!='a') return reset(n0,"");
		
		if(!goToNext()) return reset(n0,"");
		if(get()!='l') return reset(n0,"");
		
		if(!goToNext()) return reset(n0,"");
		if(get()!='s') return reset(n0,"");
		
		if(!goToNext()) return reset(n0,"");
		if(get()!='e') return reset(n0,"");
		
		return Boolean.FALSE;
	}
	
	
	
	
	protected Object parse_null()
	{
		int n0 = index();
		
		if(!goToNext()) return reset(n0,"");
		if(get()!='n') return reset(n0,"");
		
		if(!goToNext()) return reset(n0,"");
		if(get()!='u') return reset(n0,"");
		
		if(!goToNext()) return reset(n0,"");
		if(get()!='l') return reset(n0,"");
		
		if(!goToNext()) return reset(n0,"");
		if(get()!='l') return reset(n0,"");
		
		return "NULL";
	}
	
}
