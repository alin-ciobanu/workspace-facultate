
import java.util.*;

public class LinkedSetPrim extends AbstractSet implements Iterator{

	Node head;
	Node p;
	
	public class Node {
		Object val;
		Node next;
		
		public Node (Object o) {
			this.val = o;
			this.next = null;
		}
		
		public String toString () {
			String s = "";
			s = s + this.val + " ";
			return s;
		}
	}
	
	public LinkedSetPrim () {
		head = null;
		p = new Node(0);
	}
	
	public boolean add (Object e) {
		Node n = new Node(e);
		n.next = head;
		head = n;
		p.next = head;
		return true;
	}
	
	public Iterator iterator() {
		return this;
	}

	public int size() {
		int nr = 0;
		Node p = head;
		while (p != null) {
			p = p.next;
			nr++;
		}
		return nr;
	}
	
	public String toString () {
		String s = "";
		Node p = head;
		while (p != null) {
			s = s + p.val + "\n";
			p = p.next;
		}
		return s;
	}
	
	public boolean hasNext() {
		return p.next != null;
	}

	public Object next() {
		p = p.next;
		return p;
	}

	public void remove() {
		// TODO Auto-generated method stub
		
	}
	
	public static void main (String[] args) {
		LinkedSetPrim list = new LinkedSetPrim();
		
		list.add("cuvant");
		list.add("cuvant_nou");
		list.add("iterator");
		list.add("cuvant_vechi");
		list.add("java");
		Iterator it = list.iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
//		System.out.println(list);
	}
	
}
