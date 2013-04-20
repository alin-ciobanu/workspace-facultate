
import java.util.*;

public class Graph extends Vector<Vector<Integer>> {
	
	int n; // n = nr de arce minus 1 (arcul 0)
	
	public Graph (int n) {
		this.n = n+1;
		for (int i=1; i<n+1; i++)
			addElement(new Vector<Integer>());
	}
	
	public int size () {
		return n-1;
	}
	
	public void addArc (int v, int w) {
		if (!isArc(v,w))
			get(v).add(w);
	}
	
	public boolean isArc (int v, int w) {
		if (get(v).contains(w))
			return true;
		else
			return false;
	}
	
	
	public String toString () {
		String s = new String("");
//		System.out.println("Size = " + size());
		for (int i=1; i< size(); i++){
//			System.out.println("i=" + i + " " + get(i));
			for (int j=0; j< get(i).size(); j++)
				s = s + i + "-" + get(i).get(j) + '\n';
		}
		return s;
	}
	
	public static void main (String[] args) {
		Graph graf = new Graph(5);
		graf.addArc(2, 4);
		graf.addArc(3, 5);
		
		System.out.println(graf);
	}
	
}
