import java.util.Vector;


public class SetAsVectorV {
	Vector v;
	
	public SetAsVectorV () {
		v = new Vector();
	}
	
	public boolean add (Object o) {
		if (!v.contains(o))
			return v.add(o);
		else
			return false;
	}
	
	public boolean remove (Object o) {
		return v.remove(o);
	}
	
	public String toString () {
		return v.toString();
	}
	
	public static void main (String[] args) {
		SetAsVectorV set = new SetAsVectorV();
		set.add(12.1);
		set.add(14.1);
		set.add(12.1);
		set.add(13.7);
		set.remove(14.1);
		System.out.println(set);
	}
}
