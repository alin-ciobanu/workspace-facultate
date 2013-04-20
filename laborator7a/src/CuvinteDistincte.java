
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;

public class CuvinteDistincte {

	
	public static void main (String[] args) throws IOException {
		
		RandomAccessFile in = new RandomAccessFile("text.txt", "r");	
		String s;
		ArraySet aset = new ArraySet();
		
		TreeSet<String> set = new TreeSet<String>(new Comparator() {
			public int compare (Object o1, Object o2) {
				String s1 = (String) o1;
				String s2 = (String) o2;
				return s2.compareTo(s1);
			}
		});
		
		while ((s = in.readLine()) != null) {
			StringTokenizer tok = new StringTokenizer(s, " ");
			while (tok.hasMoreElements()) {
				aset.add(tok.nextToken());
			}
		}
		
		System.out.println(aset);
		
	}
}
