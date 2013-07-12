import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;



public class TestGround {
	
	public void changePair (Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> p) {
		
		p.setBoth(new Pair<Integer, Integer>(1, 3), new Pair<Integer, Integer>(5, 7));
		
	}

	
	public static void main (String[] args) {
		
		TestGround t = new TestGround();
		
		Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> p = new Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>();
		p.setBoth(new Pair<Integer,Integer>(0, 0), new Pair<Integer, Integer>(0, 0));

		System.out.println(p);
		t.changePair(p);
		
		System.out.println(p);
		
	}
	
}