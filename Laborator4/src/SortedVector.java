
import java.util.*;

public class SortedVector extends Vector {
	
	public void addElement (Object o) {
		super.addElement(o);
		Collections.sort(this);
	}
	
	public void insertElementAt (Object o, int index) {
		super.insertElementAt(o, index);
		Collections.sort(this);
	}
	
	public String toString () {
		return super.toString();
	}
	
	public static void main (String[] args) {
		SortedVector vect = new SortedVector();
		vect.addElement(13.55);
		vect.addElement(12.11);
		vect.insertElementAt(4.5, 2);
		vect.addElement(54.21);
		System.out.println(vect);
	}
}
