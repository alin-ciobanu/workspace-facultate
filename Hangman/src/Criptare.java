import java.util.ArrayList;


public class Criptare {

	private ArrayList<String> tabelaCriptare;
	
	public Criptare () {
		tabelaCriptare = new ArrayList<String>();
		initTabelaCriptare();
	}
	
	private void initTabelaCriptare () {
		tabelaCriptare.add("Q-f");
		tabelaCriptare.add("W-v");
		tabelaCriptare.add("E-6");
		tabelaCriptare.add("R-h");
		tabelaCriptare.add("T-b");
		tabelaCriptare.add("Y-7");
		tabelaCriptare.add("U-j");
		tabelaCriptare.add("I-m");
		tabelaCriptare.add("O-0");
		tabelaCriptare.add("P-.");
		tabelaCriptare.add("A-#");
		tabelaCriptare.add("S-e");
		tabelaCriptare.add("D-r");
		tabelaCriptare.add("F-t");
		tabelaCriptare.add("G-@");
		tabelaCriptare.add("H-u");
		tabelaCriptare.add("J-i");
		tabelaCriptare.add("K-o");
		tabelaCriptare.add("L-p");
		tabelaCriptare.add("Z-;");
		tabelaCriptare.add("C-a");
		tabelaCriptare.add("V-z");
		tabelaCriptare.add("B-2");
		tabelaCriptare.add("N-s");
		tabelaCriptare.add("M-x");
		tabelaCriptare.add("X-4");
	}
	
	public String crypt (String word) {
		String cryptedWord = "";
		for (int i=0; i<word.length(); i++)
			for (int j=0; j<tabelaCriptare.size(); j++)
				if (tabelaCriptare.get(j).startsWith(word.charAt(i) + "-"))
					cryptedWord += tabelaCriptare.get(j).charAt(2);
		return cryptedWord;
	}
	
	public String decrypt (String word) {
		String decryptedWord = "";
		for (int i=0; i<word.length(); i++)
			for (int j=0; j<tabelaCriptare.size(); j++)
				if (tabelaCriptare.get(j).endsWith("-" + word.charAt(i)))
					decryptedWord += tabelaCriptare.get(j).charAt(0);
		return decryptedWord;
	}
	
	public static void main(String[] args) {
		
	}

}
