import java.io.File;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


public class ArrayEMap extends AbstractMap {

	ArrayList<MEntry> v;
	
	public ArrayEMap () {
		v = new ArrayList<MEntry>();
	}

	public Set entrySet() {
		HashSet<MEntry> set = new HashSet<MEntry>();
		set.addAll(v);
		return set;
	}
	
	public Object put (Object key, Object value) {
		MEntry newEntry = new MEntry(key);
		Object old = newEntry.setValue(value);
		v.add(newEntry);
		return old;
	}
	
	public int size() {
		return v.size();
	}
	
	public static void main(String[] args) {
		
		ArrayEMap map = new ArrayEMap();
		
		String sDir = ".";
		File dir = new File(sDir);
		File[] list = new File[dir.list().length];
		list = dir.listFiles();
		
		for (int i=0; i<list.length; i++) {
			map.put(list[i].getName(), list[i].length());
		}
		
		Object[] o = map.entrySet().toArray();
		
		Arrays.sort(o, new Comparator(){
			public int compare(Object o1, Object o2) {
				Map.Entry<String, Long> v1 = (Map.Entry<String, Long>)o1;
				Map.Entry<String, Long> v2 = (Map.Entry<String, Long>)o2;
				Long l1 = (Long) v1.getValue();
				Long l2 = (Long) v2.getValue();
				if (l1 > l2)
					return 1;
				else
					return -1;
			}
		});
		
		for (int i=0; i<o.length; i++)
			System.out.println(o[i]);

	}

}
