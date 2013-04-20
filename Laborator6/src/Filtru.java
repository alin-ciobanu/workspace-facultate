import java.io.*;

public class Filtru implements FilenameFilter {

	String ext;
	
	public Filtru (String extensie) {
		ext = extensie;
	}
	
	public boolean accept(File dir, String name) {
		return name.endsWith("." + ext);
	}
	
	public static void main (String[] args) {
		
		File dir = new File("C:\\Users\\yozmo\\testare_java\\");
		String[] list;
		
		String[] extensii = {"txt", "class", "jar"};
		
		list = dir.list(new Filtru(extensii[0]));
		
		for (int i=0; i<list.length; i++)
			System.out.println(list[i]);
		
	}
	
}
