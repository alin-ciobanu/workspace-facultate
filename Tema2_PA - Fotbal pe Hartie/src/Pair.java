
public class Pair<T, U> {

	T first;
	U second;
	
	public Pair(T first, U second) {
		this.first = first;
		this.second = second;
	}
	
	T getFirst () {
		return first;
	}
	
	U getSecond () {
		return second;
	}
	
	void setFirst (T first) {
		this.first = first;
	}
	
	void setSecond (U second) {
		this.second = second;
	}
	
	void setBoth (T first, U second) {
		this.first = first;
		this.second = second;
	}

	public String toString () {
		return "[" + first + ", " + second + "]";
	}
	
}
