package gamelogic;

import java.util.Comparator;

import structs.Pair;

public class PointComparator implements Comparator<Pair<Integer, Integer>> {

	@Override
	public int compare(Pair<Integer, Integer> o1, Pair<Integer, Integer> o2) {

		return o1.compareTo(o2);

	}

}
