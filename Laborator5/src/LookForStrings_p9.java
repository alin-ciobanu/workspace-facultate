
import java.io.*;
import java.util.*;

public class LookForStrings_p9 {

	public static void main(String[] args) throws IOException {
		
		int i = 0;
		
		File dir = new File(".");
		String[] fisiere = new String[dir.list().length];
		fisiere = dir.list();
		Vector<InputStream> v = new Vector<InputStream>();
		while (i < fisiere.length) {
			v.add(new FileInputStream(fisiere[i]));
			i++;
		}
		
		Enumeration<InputStream> enu = v.elements();
		SequenceInputStream in = new SequenceInputStream(enu);
		
		
		int newByte;
		while ((newByte = in.read()) != -1)
			System.out.print(newByte);
		
		in.close();

	}

}
