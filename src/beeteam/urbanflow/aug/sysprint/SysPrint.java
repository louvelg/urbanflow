package beeteam.urbanflow.aug.sysprint;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SysPrint {

	
	
	public static void printMap(Map m)
	{
		System.out.println("Map size: "+m.size());
		Iterator it = m.keySet().iterator();
		while(it.hasNext())
		{
			Object key = it.next();
			Object value = m.get(key);
			System.out.println(key+"="+value);
		}
	}
	
	
	
	public static void printSet(Set s)
	{
		System.out.println("Set size: "+s.size());
		Iterator it = s.iterator();
		while(it.hasNext())
		{
			System.out.println(it.next());
		}
	}
	
	
	
	public static void printList(List l)
	{
		System.out.println("List size: "+l.size());
		for(int i=0;i<l.size();i++)
		{
			System.out.println(l.get(i));
		}
	}
}
