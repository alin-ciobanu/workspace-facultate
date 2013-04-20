package element;

import java.util.ArrayList;

public class Sentence extends ArrayList<String> {

	// clasa absolut inutila care m-a incurcat
	// in practica, as fi ales sa nu am clasa Sentence si sa retin fiecare propozitie ca String

	private boolean isSet;
	
	public Sentence () {
		isSet = false;
	}
	
	public Sentence (String s) {
		isSet = false;
		this.setSentence(s);
	}
	
	public String getSentence () {
		if (! isSet)
			return null;
		else
			return super.get(0);
	}
	
	public void setSentence (String sentence) {
		if (!isSet) {
			super.add(sentence);
			isSet = true;
		}
		else
			return;
	}
	
	public boolean resetSentence () {
		// returneaza true daca propozitia era setata, false altfel
		if (isSet) {
			super.remove(0);
			return true;
		}
		else
			return false;
	}
	
	public String toString () {
		return this.get(0);
	}
	
}
