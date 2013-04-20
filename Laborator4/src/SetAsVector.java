import java.util.*;

public class SetAsVector extends Vector {
	
	public boolean add (Object o) {
		if (this.contains(o))
			return false;
		else
			return super.add(o);
	}
	
	public boolean remove (Object o) {
		return super.remove(o);
	}
	
	public boolean contains (Object o) {
		return super.contains(o);
	}
	
	public String toString (Object o) {
		return super.toString();
	}
	
	public static void main (String[] args) {
		SetAsVector st = new SetAsVector();
		st.add(10);
		st.add(12);
		st.add(10);
		st.add(11);
		st.remove(new Integer(10));
		System.out.println(st);
	}
	
}

