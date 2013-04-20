
import java.util.*;
import java.io.*;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class WordDatabase {

	private String fisierDB;
	private File fileDB;
	private ArrayList<String> words; // lista de cuvinte -- vectorul contine cuvintele necriptate
	private int n; // numarul de cuvinte din baza de date
	
	public WordDatabase () {
		
		fisierDB = "words.db";
		fileDB = new File(fisierDB);
		words = new ArrayList<String>();
		n = 0;
		this.readWords();
		Collections.shuffle(words);
	}
	
	private void readWords () {
			
		InputStream is;
		is = getClass().getResourceAsStream("/" + fisierDB);
		BufferedReader in;
		
//		RandomAccessFile in;
		try {
			in = new BufferedReader(new InputStreamReader(is));
	
//			in = new RandomAccessFile(fisierDB, "r");
			String justRead = "";
			while ((justRead = in.readLine()) != null) {
				Criptare cr = new Criptare();
				words.add(cr.decrypt(justRead));
				n++;
			}
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "cause", JOptionPane.OK_OPTION);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error while reading.");
			e.printStackTrace();
		}

	}
	
	public boolean writeEntry (String word) {
		word = word.toUpperCase();
		try {
			RandomAccessFile out = new RandomAccessFile(fisierDB, "rw");
			if ((! words.contains(word)) && word.length() > 2 && word.length() < 16) {
				out.seek(out.length());
				Criptare cr = new Criptare();
				out.writeBytes(cr.crypt(word) + '\n');
				words.add(word);
				n++;
				out.close();
			}
			else {
				out.close();
				return false;
			}
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	public void writeFromFile (String fileName) {
		try {
			RandomAccessFile in = new RandomAccessFile(fileName, "r");
			String line = "";
			while ((line = in.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line, " ,:?!.\"\t;\'+=-\r\n|");
				while (st.hasMoreTokens()) {
					String str = st.nextToken();
					this.writeEntry(str);
				}
			}
			in.close();
		} catch (IOException e) {
			System.out.println("Fisierul nu a fost gasit. Baza de date nu s-a modificat.");
			return;
		}
	}
	
	
	public RandomizedWord getRandomWord (int index) { // index = -1 cand incercam sa luam un cuvant aleator
					// if index > -1 se alege cuvantul de pe pozitia indicata de index
		long t = System.currentTimeMillis();
		int r = (int)(t % words.size());
		if (index < 0)
			return new RandomizedWord(words.get(r), r);
		else
			return new RandomizedWord(words.get(index), index);
	}
	
	public List<String> getWords () {
		return words;
	}
	
	public int size () {
		// returneaza numarul de cuvinte din baza de date
		return n;
	}
	
	public void deleteDatabase() {
		fileDB.delete();
	}
	
	public static void main(String[] args) {
		
		WordDatabase db = new WordDatabase();

//		db.writeFromFile("./cuvinte_cami.txt");

		List<String> words = new ArrayList<String>();
		words = db.getWords();
		System.out.println(words);
		System.out.println(words.size());
		System.out.println(db.size());

	}

}
