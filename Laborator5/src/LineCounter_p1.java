import java.io.*;

public class LineCounter_p1 {

	public static void main (String[] args) throws IOException {
		
		FileReader fis = new FileReader(args[0]);
		LineNumberReader in = new LineNumberReader(fis);
		String s;
		
		while ((s = in.readLine()) != null) {
			System.out.println(in.getLineNumber() + " " + s);
		}
		
		in.close();
	}
	
}
