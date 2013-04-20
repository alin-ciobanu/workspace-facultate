import java.util.*;

public class SortedVector extends Vector{

	Comparator c;
	
	public SortedVector(Comparator comparator) {
		this.c = comparator; 
	}
	
	public boolean add (Object o) {
		super.add(o);
		Collections.sort(this, c);
		return true;
	}
	
}
