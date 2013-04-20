package element;

import java.util.StringTokenizer;

public class Paragraph extends Body<Sentence> {

	int size; // numarul de propozitii
	
	public Paragraph () {
		size = 0;
	}
	
	public boolean add (Sentence s) {
		super.add(s);
		size++;
		return true;
	}
	
	public void addSentence (String s) {
		Sentence sentence = new Sentence(s);
		super.add(sentence);
		size++;
	}
	
	public String getStringSentence (int index) {
		return super.get(index).getSentence();
	}
	
	public Sentence getSentence (int index) {
		return super.get(index);
	}

	public Sentence removeSentence (int index) {
		size--;
		return super.remove(index);
	}
	
	public String toString () {
		String s = "";
		for (int i=0; i<super.size(); i++) {
			s = s + super.get(i);
		}
		return s;
	}
	
	public int size () {
		return size;
	}
	
	public int getTotalWordsCount () {
		int n = 0;
		for (int i = 0; i < size; i++) {
			Sentence s = super.get(i);
			String str = s.getSentence();
			StringTokenizer tok = new StringTokenizer(str, " ");
			while (tok.hasMoreTokens()) {
				tok.nextToken();
				n++;
			}
		}
		return n;
	}
	
}

