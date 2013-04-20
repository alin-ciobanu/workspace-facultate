
import java.io.*;

public class CopiereLitereMari {

	public static void main (String[] args) {
		
		FileReader in = null;
		FileWriter out = null;
		int ch;
		
		try {
			in = new FileReader("test.txt");
			out = new FileWriter("rez_p3.txt");
		} catch (FileNotFoundException e) {
			System.out.println("Eroare la deschidere.");
		} catch (IOException e) {
			System.out.println("Nu se poate deschide fisierul in care se scrie.");
		}
		
		try {
			while ((ch = in.read()) != -1) {
				out.write(Character.toUpperCase(ch));
			}
		} catch (IOException e) {
			System.out.println("Eroare la scriere/citire.");
		}
		
		try {
			in.close();
			out.close();
		} catch (IOException e) {
			System.out.println("Eroare la inchidere fisier.");
		}
	}
	
}
