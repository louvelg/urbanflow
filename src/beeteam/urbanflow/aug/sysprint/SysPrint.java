package beeteam.urbanflow.aug.sysprint;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SysPrint {

	
	
	
	public static void printObj(Object o)
	{
		if(o instanceof String) System.out.println(o);
		else if(o instanceof List) printList((List) o);
		else if(o instanceof Set) printSet((Set) o);
		else if(o instanceof Map) printMap((Map) o);
		else if(o instanceof Object[]) printArray((Object[]) o);
		
	}
	
	
	public static void printMap(Map m)
	{
		System.out.println("Map size: "+m.size());
		Iterator it = m.keySet().iterator();
		while(it.hasNext())
		{
			Object key = it.next();
			Object value = m.get(key);
			System.out.println(key+"=");
			printObj(value);
		}
	}
	
	public static void printSet(Set s)
	{
		System.out.println("Set size: "+s.size());
		Iterator it = s.iterator();
		while(it.hasNext()) printObj(it.next());
	}
	
	public static void printList(List l)
	{
		System.out.println("List size: "+l.size());
		for(int i=0;i<l.size();i++) printObj(l.get(i));
	}
	
	public static void printArray(Object[] a)
	{
		System.out.println("Array size: "+a.length);
		for(int i=0;i<a.length;i++)	printObj(a[i]);
	}
}
