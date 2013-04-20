import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class EliminareGard {

	int N, M, L;
	ArrayList<Integer> v; // vectorul de intrare
	
	int distance; // distanta minima cautata
	
	public static final String FISIER_INTRARE = "date.in";
	public static final String FISIER_IESIRE = "date.out";
	
	public EliminareGard () {
		v = new ArrayList<Integer>();
	}
	
	public void read (String fileName) throws IOException {

		RandomAccessFile in = new RandomAccessFile(fileName, "r");
		String line = in.readLine();
		StringTokenizer tok = new StringTokenizer(line, " ");
		N = Integer.parseInt(tok.nextToken());
		M = Integer.parseInt(tok.nextToken());
		L = Integer.parseInt(tok.nextToken());

		for (int i = 0; i < N; i++) {
			v.add(Integer.parseInt(in.readLine()));
		}
		
		in.close();
		
	}
	
	public void write (String fileName, ArrayList<Integer> result) throws IOException {

		RandomAccessFile out = new RandomAccessFile(fileName, "rw");
		
		out.writeBytes(distance + "\n");
		out.writeBytes(result.size() + "\n");

		for (int i = 0; i < result.size(); i++) {
			out.writeBytes(result.get(i).toString());
			if ( i != (result.size() - 1) )
				out.writeBytes("\n");
		}

		out.close();

	}

	public ArrayList<Integer> giveSolution () {
		
		ArrayList<Integer> rez = new ArrayList<Integer>();
		
		int lower = v.get(0);
		int upper = L;
		
		int d = lower + (upper - lower) / 2;
		int lastElementInRez = v.get(0);
		int rezSize = 2; // dimensiunea rezultatului
		// incepe de la 2 pentru ca se stie ca primul si ultimul element nu pot fi eliminate
		
		for (int i = 1; i < N - 1; i++) {
			if ( (v.get(i) - lastElementInRez >= d) && (v.get(N - 1) - v.get(i) >= d) ) {
				lastElementInRez = v.get(i);
				rezSize++;
			}
		}

		/*
		 * Cautare binara pentru aflarea distantei maxime care se poate obtine.
		 * Dupa aflarea distantei, se construieste solutia tinand cont de distanta aflata.
		 * Complexitate: O(N*lg(L))
		 */
		while (lower + 1 != upper) {

			if (rezSize < (N - M)) {
				upper = d;
				rezSize = 2;
				lastElementInRez = v.get(0);
			}
			else {
				lower = d;
				rezSize = 2;
				lastElementInRez = v.get(0);
			}
			
			d = lower + (upper - lower) / 2;
			
			for (int i = 1; i < N - 1; i++) {
				if ( (v.get(i) - lastElementInRez >= d) && (v.get(N - 1) - v.get(i) >= d) ) {
					lastElementInRez = v.get(i);
					rezSize++;
				}
			}
						
		}

		distance = d; // salveaza in campul distance distanta minima
		
		/*
		 * Constructia vectorului rezultat
		 */
		rez.add(v.get(0));
		lastElementInRez = rez.get(rez.size() - 1);
		for (int i = 1; i < N - 1; i++) {
			if ( (v.get(i) - lastElementInRez >= d) && (v.get(v.size() - 1) - v.get(i) >= d) ) {
				lastElementInRez = v.get(i);
				rez.add(lastElementInRez);
			}
		}
		rez.add(v.get(v.size() - 1));
		
		return rez;
		
	}


	public static void main(String[] args) throws IOException {

		EliminareGard p = new EliminareGard();
		p.read(FISIER_INTRARE);

		ArrayList<Integer> rez;
		rez = p.giveSolution();
		
		p.write(FISIER_IESIRE, rez);

	}

}
