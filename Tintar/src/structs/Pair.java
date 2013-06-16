package structs;

public class Pair<T, U> {

	private T first;
	private U second;

	public Pair(T first, U second) {
		this.first = first;
		this.second = second;
	}

	public Pair () {
		// Nothing here. We just need a constructor with no params.
	}

	public boolean equals (Pair<T, U> anotherPair) {
		
		if (this.first.equals(anotherPair.first) && this.second.equals(anotherPair.second)) {
			return true;
		}
		
		return false;
		
	}
	
	public T getFirst () {
		return first;
	}
	
	public U getSecond () {
		return second;
	}

	public void setFirst (T first) {
		this.first = first;
	}

	public void setSecond (U second) {
		this.second = second;
	}

	public void setBoth (T first, U second) {
		this.first = first;
		this.second = second;
	}
	
	public String toString () {
		return "[" + first + ", " + second + "]";
	}

	
}

