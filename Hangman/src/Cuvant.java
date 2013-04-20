import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;


public class Cuvant {

	private String word;
	private ArrayList<Character> tried;
	private ArrayList<Character> guessed;
	private int faults;
	private ArrayList<Character> mustBeGuessed;
	
	public Cuvant (String word) {
		this.word = word;
		tried = new ArrayList<Character>();
		guessed = new ArrayList<Character>();
		faults = 0;
		mustBeGuessed = new ArrayList<Character>();
		for (int i=1; i<word.length(); i++)
			if (! mustBeGuessed.contains(word.charAt(i)))
				mustBeGuessed.add(word.charAt(i));
		Collections.sort(mustBeGuessed);
	}
	
	public Cuvant () throws NoWordToGuessException{
		throw new NoWordToGuessException("Ai uitat sa pui cuvantul.");
	}
	
	public void tryCharacter(char ch) {
		if (tried.contains(ch))
			return;
		if (mustBeGuessed.contains(ch)) {
			tried.add(ch);
			guessed.add(ch);
		}
		else {
			tried.add(ch);
			faults = tried.size() - guessed.size();
		}
		Collections.sort(guessed);
	}
	
	public String getFullWord () {
		return word;
	}
	
	public int getFaultsNo () {
		return faults;
	}
	
	public boolean gameOver (float time) {
		return faults == 7 || time > 60000;
	}
	
	public boolean win () {
		if (guessed.equals(mustBeGuessed))
			return true;
		return false;
	}
	
	// de aici se sterge
	public List getGuessed () {
		return guessed;
	}
	
	public List getTried () {
		return tried;
	}
	
	public List getMustBeGuessed () {
		return mustBeGuessed;
	}
	// pana aici se sterge
	
	public String toString () {
		String s = "";
		s = s + word.charAt(0);
		for (int k=1; k<word.length(); k++)
			if (guessed.contains(word.charAt(k)))
				s = s + word.charAt(k);
			else
				s = s + "-";
		return s;
	}
	
	public static void main(String[] args) throws NoWordToGuessException {
		// TODO code here
	}

}
