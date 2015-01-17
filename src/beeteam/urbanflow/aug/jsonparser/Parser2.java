package beeteam.urbanflow.aug.jsonparser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser2 extends Parser1 {

	
	public Parser2(boolean debug)
	{super(debug);}
	
	
	
	/*
	 * object = { members } 
	 */
	protected Object parse_object()
	{
		int n0 = index();
		debug("parse_object() at "+n0);
		
		if(!goToNext()) return reset(n0,"!goToNext()");
		if(get()!='{') return reset(n0,"get()!='{'");
		
		Object r = parse_members();
		if(r==null) return reset(n0,"r==null");
		
		if(!goToNext()) return reset(n0,"!goToNext()");
		if(get()!='}') return reset(n0,"get()!='}'");
		
		return r;
	}
	
	
	
	/*
	 * members = member*
	 */
	private Object parse_members()
	{
		int n0 = index();
		debug("parse_members() at "+n0);
		
		Map map = new HashMap();
		
		Object[] member = (Object[]) parse_member();
		if(member!=null)
		{
			map.put(member[0],member[1]);

			member = (Object[]) parse_nextMember();
			while(member!=null)
			{
				map.put(member[0],member[1]);
				member = (Object[]) parse_nextMember();
			}
		}
		return map;
	}
	
	
	
	
	
	
	private Object parse_nextMember()
	{
		int n0 = index();
		debug("parse_nextMember() at "+n0);
		
		if(!goToNext()) return reset(n0,"");
		if(get()!=',') return reset(n0,"");
		
		Object[] member = (Object[]) parse_member();
		if(member==null) return reset(n0,"");
		return member;
	}
	
	
	
	
	/*
	 * member = string:value
	 */
	private Object parse_member()
	{
		int n0 = index();
		debug("parse_member() at "+n0);
		
		parse_blank();
		
		Object key = parse_string();
		if(key==null) return reset(n0,"");
		
		parse_blank();
		
		if(!goToNext()) return reset(n0,"");
		if(get()!=':') return reset(n0,"");
		
		parse_blank();
		
		Object value = parse_value();
		if(value==null) return reset(n0,"");
		
		parse_blank();
		
		return new Object[]{key,value};
	}
	
	
	
	
	/*
	 * value = string | number | object | array | true | false | null
	 */
	private Object parse_value()
	{
		int n0 = index();
		debug("parse_value() at "+n0);
		
		Object string = parse_string();
		if(string!=null) return string;
		reset(n0,"string==null");
		
		Object number = parse_number();
		if(number!=null) return number;
		reset(n0,"number==null");
		
		Object object = parse_object();
		if(object!=null) return object;
		reset(n0,"object==null");
		
		Object array = parse_array();
		if(array!=null) return array;
		reset(n0,"array==null");
		
		Object true_ = parse_true();
		if(true_!=null) return true_;
		reset(n0,"true_==null");
		
		Object false_ = parse_false();
		if(false_!=null) return false_;
		reset(n0,"false_==null");
		
		Object null_ = parse_null();
		if(null_!=null) return null_;
		return reset(n0,"null_==null");
	}
	
	
	
	
	/*
	 * string = " chars "
	 */
	private Object parse_string()
	{
		int n0 = index();
		debug("parse_string() at "+n0);
		
		if(!goToNext()) return reset(n0,"!goToNext()");
		if(get()!='"') return reset(n0,"get()!='\"'");
		
		Object r = parse_chars();
		if(r==null) return reset(n0,"r==null");
		
		if(!goToNext()) return reset(n0,"!goToNext()");
		if(get()!='"') return reset(n0,"get()!='\"'");
		
		return done(r);
	}
	
	
	
	
	
	private Object parse_array()
	{
		debug("parse_array()");
		int n0 = index();
		
		if(!goToNext()) return reset(n0,"!goToNext()");
		if(get()!='[') return reset(n0,"get()!='['");
		
		Object r = parse_elements();
		if(r==null) return reset(n0,"r==null");
		
		if(!goToNext()) return reset(n0,"!goToNext()");
		if(get()!=']') return reset(n0,"get()!=']'");
		
		return r;
	}
	
	
	
	
	/*
	 * members = member*
	 */
	private Object parse_elements()
	{
		debug("parse_elements()");
		int n0 = index();
		
		
		List list = new ArrayList();
		
		parse_blank();
		Object value = parse_value();
		parse_blank();
		
		if(value==null) return reset(n0,"value==null");
		list.add(value);
		
		value = parse_nextElement();
		while(value!=null)
		{
			list.add(value);
			value = parse_nextElement();
		}
		return list;
	}
	
	
	
	private Object parse_nextElement()
	{
		debug("parse_nextElement()");
		int n0 = index();
		
		if(!goToNext()) return reset(n0,"!goToNext()");
		if(get()!=',') return reset(n0,"get()!=','");

		parse_blank();
		Object value = parse_value();
		parse_blank();
		
		if(value==null) return reset(n0,"value==null");
		return value;
	}
}
