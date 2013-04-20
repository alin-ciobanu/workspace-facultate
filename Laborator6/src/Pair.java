import java.util.Comparator;


public class Pair {
	Object a;
	Object b;
	
	public Pair (Object a, Object b) {
		this.a = a;
		this.b = b;
	}
	
	public boolean equals(Object o) {
		Pair p = (Pair) o;
		return a.equals(p.a) && b.equals(p.b);
	}
	
	public String toString () {
		return a + " " + b;
	}
	
	public static void main (String[] args) {
		SortedVector sv = new SortedVector (new Comparator() {
			public int compare (Object a, Object b) {
				if (!(a instanceof Pair) || !(b instanceof Pair)) {
					System.err.println ("Eroare.");
					return -1;
				}
				Pair p1 = (Pair) a;
				Pair p2 = (Pair) b;
				String st1 = (String)(p1.a);
				String st2 = (String)(p2.a);
				return st1.compareTo(st2);
			}
		});
		
		sv.add(new Pair("Costel", 1));
		sv.add(new Pair("Ana", 16));
		sv.add(new Pair ("Titi", 5));
		
		System.out.println(sv);
		
	}
	
}
