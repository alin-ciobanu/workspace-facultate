import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.*;


// problema 2 - lab 7b

public class Cuvinte {

	public static void main(String[] args) throws FileNotFoundException {

		Scanner in = new Scanner(new File("./text.txt"));
		TreeMap<String, LinkedList> tm = new TreeMap<String, LinkedList>();
		String s = "";
		int i = 0;
		
		while (in.hasNextLine()) {
			i++;
			s = in.nextLine();
			StringTokenizer st = new StringTokenizer(s, " ");
			while (st.hasMoreTokens()) {
				LinkedList<Integer> lst = new LinkedList<Integer>();
				s = st.nextToken();
				if (tm.keySet().contains(s)) {
					((LinkedList)tm.get(s)).add(i);
				}
				else {
					lst.add(i);
					tm.put(s, lst);
				}
			}
		}
	
		System.out.println(tm);
		
	}

}
