import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.SortedSet;


public class SArraySet extends ArrayList implements SortedSet{

	Comparator c;
	
	public SArraySet(Comparator c) {
		this.c = c;
	}
	
	public SArraySet() {
		this(null);
	}
	
	public Comparator comparator() {
		return c;
	}
	
	public boolean add (Object o) {
		if (this.contains(o))
			return false;
		else {
			super.add(o);
			Collections.sort(this, c);
			return true;
		}
	}

	public Object first() {
		return this.get(0);
	}

	public SortedSet headSet(Object arg0) {
		SArraySet set = new SArraySet(c);
		set.addAll(super.subList(0, super.indexOf(arg0)));
		return set;
	}

	public Object last() {
		return this.get(this.size());
	}

	public SortedSet subSet(Object arg0, Object arg1) {
		SArraySet set = new SArraySet(c);
		set.addAll(super.subList(super.indexOf(arg0), super.indexOf(arg1)));
		return set;
	}

	public SortedSet tailSet(Object arg0) {
		SArraySet set = new SArraySet(c);
		set.addAll(super.subList(super.indexOf(arg0), this.size()));
		return set;
	}
	
	public static void main(String[] args) {
		SArraySet ss = new SArraySet(new Comparator(){
			public int compare (Object o1, Object o2) {
				int nr1 = (int) o1;
				int nr2 = (int) o2;
				if (nr1 > nr2)
					return -1;
				else
					return 1;
			}
		});
		ss.add(12);
		ss.add(32);
		ss.add(12);
		ss.add(10);
		ss.add(15);
		ss.add(11);
		ss.add(1);
		ss.add(7);
		
		SortedSet subset = new SArraySet();
		subset = ss.headSet(10);
		
		System.out.println(ss);
		System.out.println(subset);
	}
	
}
