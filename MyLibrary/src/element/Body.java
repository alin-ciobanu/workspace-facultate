
package element;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;


public class Body<T> implements Collection<T> {

	private ArrayList<T> c;
	
	public Body () {
		c = new ArrayList<T>();
	}
	
	@Override
	public boolean add(T e) {
		return c.add(e);
	}
	
	public void add(int index, T e) {
		c.add(index, e);
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		return this.c.addAll(c);
	}

	@Override
	public void clear() {
		c.clear();
	}

	@Override
	public boolean contains(Object o) {
		return c.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return c.containsAll(c);
	}

	public T get (int index) {
		return c.get(index);
	}

	@Override
	public boolean isEmpty() {
		return c.isEmpty();
	}

	@Override
	public Iterator<T> iterator() {
		return c.iterator();
	}

	@Override
	public boolean remove(Object o) {
		return c.remove(o);
	}
	
	public T remove (int index) {
		return c.remove(index);
	}
	
	@Override
	public boolean removeAll(Collection<?> c) {
		return this.c.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return this.c.retainAll(c);
	}

	@Override
	public int size() {
		return c.size();
	}

	@Override
	public Object[] toArray() {
		return c.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return c.toArray(a);
	}


}
