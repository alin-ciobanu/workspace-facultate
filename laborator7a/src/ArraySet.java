import java.util.*;


public class ArraySet extends ArrayList implements Set {

	public boolean add(Object e) {
		if (this.contains(e))
			return false;
		else
			return super.add(e);
	}

	public void add (int i, Object e) {
		if (!this.contains(e))
			super.add(i, e);
	}
	
}
