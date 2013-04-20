
import java.util.*;

public class CitireTastaturaScanner_p7 {

	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);
		String s;
		
		while (in.hasNextLine()) {
			s = in.nextLine();
			if (s.equals("exit"))
				break;
			System.out.println(s);
		}
		
		in.close();
		
	}

}
