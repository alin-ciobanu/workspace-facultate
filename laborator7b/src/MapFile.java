
import java.io.File;
import java.util.*;


// problema 1 - lab 7b

public class MapFile {
	
	public static void main(String[] args) {

		HashMap<String, Long> hm = new HashMap<String, Long>();
		TreeMap<String, Long> tm = new TreeMap<String, Long>();
		
		String sDir = ".";
		File dir = new File(sDir);
		File[] list = new File[dir.list().length];
		list = dir.listFiles();
		
		for (int i=0; i<list.length; i++) {
			hm.put(list[i].getName(), list[i].length());
			tm.put(list[i].getName(), list[i].length());
		}
		
		Object[] o = hm.entrySet().toArray();
		
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
