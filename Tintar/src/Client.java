import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class Client {

	public static void main (String[] args) throws UnknownHostException, IOException {
	
		Socket cl = new Socket("127.0.0.1", 5413);
		
		PrintWriter out = new PrintWriter(cl.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(cl.getInputStream()));
		Scanner input = new Scanner(System.in);
		
		while (true) {
			String line = input.nextLine();
			out.println(line);
			if (line == "END") {
				break;
			}
		}
		
		cl.close();
		
	}
	
}
