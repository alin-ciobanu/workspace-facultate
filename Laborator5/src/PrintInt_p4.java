
import java.io.*;

public class PrintInt_p4 {

	public static void main (String[] args) throws IOException {
		
		PrintWriter out = new PrintWriter("intregi.txt");
		
		for (int i = 0; i<10; i++) {
			out.print(i);
		}
		
		
		
		
		out.close();
		
	}
	
}
