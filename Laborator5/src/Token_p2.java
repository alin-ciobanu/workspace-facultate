import java.util.*;
import java.io.*;

public class Token_p2 {

	public static void main (String[] args) {
	
		LineNumberReader in = null;
		try {
			in = new LineNumberReader(new FileReader("C:\\Users\\yozmo\\workspace\\" +
					"Laborator5\\bin\\p1.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
		}
		String s;
		int nr_linii = 0, nr_cuvinte = 0;
		
		try {
			while ((s = in.readLine()) != null) {
				nr_linii++;
				StringTokenizer st = new StringTokenizer(s, " ");
				nr_cuvinte += st.countTokens();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Nr linii: " + nr_linii +"\n" + "Nr cuvinte: " + nr_cuvinte);
		
	}
}
