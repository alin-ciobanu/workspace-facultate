
import java.util.*;

public class IntSet {
	
	BitSet v;
	
	public IntSet () {
		this(16); // dimensiunea initiala = 16
	}
	
	public IntSet (int n) {
		v = new BitSet(n);
	}
	
	public void add (int x) { // adauga x in Set
		v.set(x);
	}
	
	public void remove (int x) { // scoate x din Set
		v.clear(x);
	}
	
	public boolean contains (int x) { // verifica daca x e in Set
		return v.get(x);
	}
	
	public String toString() {
		return v.toString();
	}
	
	public static void main (String[] args) {
		IntSet set = new IntSet();
		Random r = new Random ();
		for (int i=0; i<14; i++) {
			set.add(i*2);
		}
		set.remove(10);
		System.out.println(set);
		
	}
	
}
