package p2;

/**
 * Proiectarea Algoritmilor, 2013
 * Lab 3: Greedy si Programare Dinamica
 * Task 2: Problema rucsacului, varianta continua si discreta
 *
 * @author 	Sorina Sandu, Mihai Bivol, Alex Olteanu
 * @email	alexandru.olteanu@cs.pub.ro
 */

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;

public class Backpack {

	public static final int NO_TESTS = 2;
	public static final int UNDEF = -1;
	public int[] cap = new int[NO_TESTS];				// capacitatea
	public Obiect[][] obiecte = new Obiect[NO_TESTS][];	// lista de obiecte

	public static void main(String[] args) {
		Backpack b = new Backpack();
		b.readData("date2.in");
		b.test();
	}

	/**
	 * for each of the tests
	 * find the longest common substring
	 */
	private void test () {
		/* for each of the tests */
		for (int test=0; test<NO_TESTS; test++) {

			System.out.print("Capacitate "+cap[test]+" si obiectele ");
			printv(obiecte[test]);
			System.out.println();

			Obiect[] solutie = chooseObjectsGreedy(cap[test], obiecte[test]);
			System.out.print("Folosind Greedy, obtinem selectia ");
			printv(solutie);
			printValue(solutie);
			System.out.println();

			solutie = chooseObjectsPD(cap[test], obiecte[test]);
			System.out.print("Folosind PD, obtinem selectia ");
			printv(solutie);
			printValue(solutie);
			System.out.println();
			System.out.println();
		}
	}

	/**
	 * functie ce alege obiecte din vectorul obiecte pentru capacitatea cap folosind Greedy
	 */
	private Obiect[] chooseObjectsGreedy(int cap, Obiect[] obiecte) {

		Vector<Obiect> solutie = new Vector<Obiect>();

		Arrays.sort(obiecte);

		int remainingSpace = cap;
		for (Obiect o : obiecte) {
			if (remainingSpace == 0) break;

			/* daca putem lua obiectul in intregime */
			if (remainingSpace>=o.weight) {
				solutie.add(o);
				remainingSpace -= o.weight;

			/* altfel luam cat incape */
			} else {
				solutie.add(new Obiect(remainingSpace,
						Math.floor(1e4 * o.value*remainingSpace/o.weight)/1e4));
				break;
			}

		}

		return solutie.toArray(new Obiect[solutie.size()]);

	}

	/**
	 * functie ce alege obiecte din vectorul obiecte pentru capacitatea cap folosind PD
	 */
	private Obiect[] chooseObjectsPD(int cap, Obiect[] obiecte) {

		Vector<Integer> solutieIndex = new Vector<Integer>();	  // initial vom pune indecsii solutiei aici
		Obiect[] solutie;										  // iar in acest vector vom pune setul de obiecte
		double[][] valMax = new double[obiecte.length + 1][cap + 1]; // valMax[i][j] = valoarea maxima pentru
												// obiecte de la 0 la i, folosite pentru capacitatea maxima j

		// TODO Initializam prima coloana cu 0
		for (int i = 0; i < obiecte.length; i++)
			valMax[i][0] = 0;

		// TODO Calculam pentru fiecare încarcare mai mica sau egala cu capacitatea
		// cea mai mare valoare pe care o putem obtine. Pentru aceasta,
		// avem nevoie de valorile pentru greutatile mai mici.
		//
		// Pentru a folosi fiecare obiect o singura data, consideram treptat toate
		// solutiile ce tin cont de primele {1, 2, ..., n} obiecte.
		//
		
		for (int i = 1; i < obiecte.length; i++) {
			for (int j = 1; j <= cap; j++) {
				if (j > obiecte[i].weight) {
					valMax[i][j] = Math.max(valMax[i - 1][j], valMax[i - 1][j - obiecte[i].weight] + obiecte[i].weight);
				}
				else {
					valMax[i][j] = valMax[i-1][j];
				}
			}
		}
		
		int result = UNDEF;
		// TODO Cautam pe ultima linie a matricei valoarea maxima si salvam
		// indicele acesteia in variabila result.
		
		ArrayList<Double> vect = new ArrayList<Double>();
		for (int i = 0; i <= cap; i++)
			vect.add(valMax[obiecte.length - 1][i]);
		result = indiceResult(vect);
		
		// TODO Dupa ce am aflat pe ce pozitie se afla valoarea maxima care poate
		// fi atinsa, plecam de la pozitia maximului pentru a reconstitui solutia.
		
		int k = obiecte.length - 1;
		while (valMax[k][result] != 0) {
			if (valMax[k][result] == valMax[k - 1][result]) {
				k--;
			}
			else {
				result = obiecte[k].weight;
				solutieIndex.add(k);
			}
		}
		

		solutie = new Obiect[solutieIndex.size()];
		int i=0;
		for (Integer idx: solutieIndex) {
			solutie[i++] = obiecte[idx];
		}

		return solutie;

	}

	int indiceResult (ArrayList<Double> v) {
		double max = v.get(0);
		int pos = 0;
		for (int i = 1; i < v.size(); i++) {
			if (v.get(i) > max) {
				max = v.get(i);
				pos = i;
			}
		}
		return pos;
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
				cap[i] = scanner.nextInt();			// capacitate
				int n = scanner.nextInt();			// numar de obiecte
				obiecte[i] = new Obiect[n];
				for (int j=0;j<n;j++) {
					obiecte[i][j] = new Obiect(scanner.nextInt(), scanner.nextDouble());
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
	private void printv(Object[] v) {
		System.out.print("{ ");
		for (Object elem:v){
			System.out.print(elem+" ");
		}
		System.out.print("}");
	}
	private void printValue(Obiect[] os) {
		double totalValue = 0;
		for (Obiect o:os){
			totalValue += o.value;
		}
		System.out.print(" valoare totala = "+totalValue);
	}

	private class Obiect implements Comparable<Obiect>{
		int weight;
		double value;

		public Obiect(int weight, double value) {
			super();
			this.weight = weight;
			this.value = value;
		}

		@Override
		public String toString() {
			return "[w=" + weight + ", v=" + value + "] ";
		}

		public int compareTo(Obiect o) {
			double thisWeight = (double) this.weight;
			double oWeight = (double) o.weight;
			
			double retVal = (this.value / thisWeight) - (o.value / oWeight);

			if (retVal > 0)
				return -1;
			else
				return 1;
			
		}
	}
}
