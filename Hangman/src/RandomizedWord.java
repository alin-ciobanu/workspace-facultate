
public class RandomizedWord {

	// este, de fapt, o pereche (String, int) care retine un cuvant si indexul de la care a fost scos
	// este in stransa relatie cu WordDatabase.getRandomWord(int)

	private String word;
	private int index;

	public RandomizedWord () {
		this("", -1);
	}
	
	public RandomizedWord (String word, int index) {
		this.word = word;
		this.index = index;
	}
	
	public String getWord () {
		return word;
	}
	
	public int getIndex () {
		return index;
	}
	
	public void setWord (String word) {
		this.word = word;
	}
	
	public void setIndex (int index) {
		this.index = index;
	}

}
