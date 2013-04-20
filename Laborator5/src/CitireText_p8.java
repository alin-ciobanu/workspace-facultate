
import java.io.*;

public class CitireText_p8 {

	public static void main(String[] args) throws IOException {

		RandomAccessFile f = new RandomAccessFile("textp9.txt", "r");
		byte[] b = new byte[(int)f.length()];
		f.readFully(b);
		String s = new String(b);
		
		System.out.println(s);
		
		f.close();
	}

}
