import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {

	public static void main (String[] args) throws IOException {
		
		ServerSocket serv = new ServerSocket(5413);
		Socket cl = serv.accept();
		
		PrintWriter out = new PrintWriter(cl.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(cl.getInputStream()));

		while (true) {
			String line = in.readLine();
			System.out.println(line);
			if (line == "END") {
				break;
			}
		}
		
		serv.close();
		cl.close();
		
	}
	
}
