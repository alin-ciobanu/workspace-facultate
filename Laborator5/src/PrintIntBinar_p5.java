
import java.io.*;

public class PrintIntBinar_p5 {

	public static void main (String[] args) throws IOException {
		
		DataOutputStream out = new DataOutputStream(new FileOutputStream("p5.txt"));
		
		for (int i=1; i<34; i = i + 2) {
				out.writeInt(i);
		}
			
		out.close();
		
		DataInputStream in = new DataInputStream(new FileInputStream("p5.txt"));
		
		try {
			while (true)
				System.out.print(in.readInt() + " ");
		} catch (IOException e) {
		}
		
		in.close();
		
	}
	
}
