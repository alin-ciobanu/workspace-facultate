import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.Vector;


public class ArrayMap {

	Vector<Object> keys;
	Vector<Object> values;
	
	public ArrayMap () {
		keys = new Vector<Object>();
		values = new Vector<Object>();
	}
	
	public Object put (Object key, Object value) {
		Object oldValue = null;
		if (keys.contains(key)) {
			oldValue = values.get(keys.indexOf(key));
			values.set(keys.indexOf(key), value);
		}
		else {
			keys.add(key);
			values.add(value);
		}
		return oldValue;
	}
	
	public Object get (Object key) {
		return values.get(keys.indexOf(key));
	}
	
	public Set<Map.Entry<Object, Object>> entrySet () {
		HashSet set = new HashSet();
		for (int i=0; i<keys.size(); i++) {
			MEntry newEntry = new MEntry(keys.get(i));
			newEntry.setValue(values.get(i));
			set.add(newEntry);
		}
		return set;
	}
	
	public Collection values () {
		return values;
	}
	
	public String toString () {
		String s = "";
		s = s + "[";
		for (int i=0; i<keys.size(); i++) {
			s = s + keys.get(i) + "=";
			s = s + values.get(i);
			if (i+1 < keys.size())
				s = s + ", ";
		}
		s = s + "]";
		return s;
	}
	
	public static void main(String[] args) {

		ArrayMap map = new ArrayMap();
		map.put("Cuvant", 5);
		map.put("Litera", 4);
		map.put("Alfabet", 10);
		map.put("Cuvant", 11);
		
		Collection values = new LinkedList();
		values = map.values();

		Set entrySet = new HashSet();
		entrySet = map.entrySet();
		
		System.out.println(map);
		System.out.println(values);
		System.out.println(entrySet);
		System.out.println(map.get("Litera"));
		
	}

}
