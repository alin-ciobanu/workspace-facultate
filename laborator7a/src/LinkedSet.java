
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;

public class LinkedSet extends LinkedList implements Set{

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

	public Iterator iterator() {
		return this.listIterator();
	}
	
	public static void main (String[] args) throws IOException {
		
		LinkedSet lSet = new LinkedSet();
		for (int i = 0; i<777; i=i+29)
			lSet.add(i);	
		
		System.out.println(lSet);
		for (Iterator it = lSet.iterator(); it.hasNext();)
			System.out.println(it.next());
		
	}
}
