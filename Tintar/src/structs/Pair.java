package structs;

public class Pair<T, U> implements Comparable {

	private T first;
	private U second;

	public Pair(T first, U second) {
		this.first = first;
		this.second = second;
	}

	public Pair () {
		// Nothing here. We just need a constructor with no params.
	}

	@Override
	public boolean equals (Object o) {

		if (! (o instanceof Pair<?, ?>)) {
			return false;
		}

		Pair<?, ?> anotherPair = (Pair<?, ?>) o;
		if (this.first.equals(anotherPair.first) && this.second.equals(anotherPair.second)) {
			return true;
		}

		return false;

	}

	@Override
	public int compareTo (Object o) {

		if (! (o instanceof Pair<?, ?>)) {
			return -1;
		}

		Pair<?, ?> anotherPair = (Pair<?, ?>) o;

		if (this.getFirst().equals(anotherPair.getFirst())) {
			return ((Comparable) this.getSecond()).compareTo(anotherPair.getSecond());
		}

		return ((Comparable) this.getFirst()).compareTo(anotherPair.getFirst());

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

	@Override
	public String toString () {
		return "[" + first + ", " + second + "]";
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += first.hashCode() * 11;
		hash -= second.hashCode() * 7;
		return hash;
	}

}

