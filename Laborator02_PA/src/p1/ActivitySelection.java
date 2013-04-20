package p1;

/**
 * Proiectarea Algoritmilor, 2013
 * Lab 2: Greedy si Programare Dinamica
 * Task 1.2: Activity Selection
 *
 * @author 	Sorina Sandu, Mihai Bivol, Alex Olteanu
 * @email	alexandru.olteanu@cs.pub.ro
 */

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

@SuppressWarnings("unused")
public class ActivitySelection {

	public static final int NO_TESTS = 2;
	public Activity[][] activities = new Activity[NO_TESTS][];			

	public static void main(String[] args) {
		ActivitySelection as = new ActivitySelection();
		as.readData("date1.in");
		as.test();
	}

	/**
	 * for each of the tests
	 * find the longest common substring
	 */
	private void test () {
		/* for each of the tests */
		for (int test=0; test<NO_TESTS; test++) {
			Activity[] rez = as(activities[test]);
			System.out.print("Programarea optima pentru ");
			printv(activities[test]);
			System.out.print(" este ");
			printv(rez);
			System.out.println();
		}
	}

	/**
	 * functie ce calculeaza si returneaza programarea optima a spectacolelor din activities
	 */
	private Activity[] as(Activity[] activities) {

		ArrayList<Activity> rez = new ArrayList<Activity>(); 	// rezultatul
		
		Arrays.sort(activities, new Comparator<Activity>() {

			@Override
			public int compare(Activity o1, Activity o2) {
				return o1.compareTo(o2);
			}
		});
		
		int irez, iact;
		irez = iact = 0;
		rez.add(irez, activities[iact++]);
		
		while (iact < activities.length) {
			
			if (activities[iact].oi >= rez.get(irez).os) {
				rez.add(++irez, activities[iact]);
			}
			
			iact++;
			
		}
		
		return rez.toArray(new Activity[rez.size()]);
	}

	/**
	 * Function to read all the tests as pairs of arrays
	 * @param filename
	 */
	private void readData ( String filename ) {
		Scanner scanner = null;

		/* you should use try-catch for proper error handling! */
		try {

			scanner = new Scanner(new File(filename));

			for (int i=0;i<NO_TESTS;i++){

				/* read the array in which to look for data */
				int n = scanner.nextInt();			// number of activities
				activities[i] = new Activity[n];
				for (int j=0;j<n;j++) {
					activities[i][j] = new Activity(scanner.nextInt(), scanner.nextInt());
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			/* trebuie sa inchidem buffered reader-ul */
			try {
				if (scanner != null) scanner.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	/** 
	 * Functie ce afiseaza pe ecran un vector
	 */
	private void printv(Activity[] a) {
		System.out.print("{ ");
		for (Activity elem:a){
			System.out.print(elem+" ");
		}
		System.out.print("}");
	}

	public class Activity implements Comparable<Activity> {
		int oi;	// ora de incepere
		int os; // ora de sfarsit
		
		public Activity(int oi, int os) {
			super();
			this.oi = oi;
			this.os = os;
		}
		
		@Override
		public String toString() {
			return String.format("(%d,%d) ", oi, os);
		}

		public int compareTo(Activity a) {
			
			if (this.os != a.os)
				return this.os - a.os;
			else 
				return this.oi - a.oi;

		}
	}
}
