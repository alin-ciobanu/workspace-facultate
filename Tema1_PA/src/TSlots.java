import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class TSlots {

	int N, T;
	ArrayList<Integer> v = new ArrayList<Integer>(); // vectorul citit din fisier
	
	int bestSum; // suma maxima obtinuta din adunarea a T sloturi

	public static final String FISIER_INTRARE = "date.in";
	public static final String FISIER_IESIRE = "date.out";


	public void read (String fileName) throws IOException {
		
		RandomAccessFile in = new RandomAccessFile(fileName, "r");
		String line = in.readLine();
		StringTokenizer tok = new StringTokenizer(line, " ");
		N = Integer.parseInt(tok.nextToken());
		T = Integer.parseInt(tok.nextToken());

		for (int i = 0; i < N; i++) {
			v.add(Integer.parseInt(in.readLine()));
		}

		in.close();
		
	}
	
	public void write (String fileName, ArrayList<Interval> result) throws IOException {

		RandomAccessFile out = new RandomAccessFile(fileName, "rw");
		out.writeBytes(bestSum + "\n");

		out.writeBytes(result.size() + "\n");
		
		for (int i = result.size() - 1; i >= 0; i--) {
			Interval current = result.get(i);
			out.writeBytes(current.getStart() + " " + current.getSecond() + "\n");
		}

		out.close();
		
	}
	
	/**
	 * @param void (luate din campurile clasei)
	 * @return intervalele care dau castigul maxim (in ordine inversa)
	 */
	public ArrayList<Interval> chooseSlots () {

		ArrayList<Interval> solution = new ArrayList<Interval>();

		Entity[][] maxSum = new Entity[N][T + 1];
		// maxSum[i][j] - suma maxima obtinuta prin adunarea a exact j elemete din primele i ale vectorului

		for (int i = 0; i < N; i++) {
			for (int j = 0; j <= T; j++) {
				maxSum[i][j] = new Entity();
			}
		}

		for (int i = 0; i < N; i++) {
			maxSum[i][0] = new Entity(0, 0); // suma maxima a niciunui element nu are sens
			maxSum[i][1] = new Entity(0, 0); // suma maxima a unui element este 0 (inceput de secventa)
		}

		for (int i = 0; i <= T; i++) {
			maxSum[0][i] = new Entity(0, 0); // primul slot din vector da mereu profit 0
		}


		/*
		 * Daca suma maxSum[i - 1][j - 1] + v[i] > max (maxSum[0:i - 1][j - 1]) atunci
		 * maxSum[i][j] = maxSum[i - 1][j - 1] + v[i] pentru ca se elementul i mareste profitul
		 * altfel maxSum[i][j] ia valoarea maximului coloanei precedente (a sumei maxime de pana atunci)
		 * 
		 * Pentru optimizare, am retinut de fiecare data maximul intr-un camp.
		 * Complexitate: O(N*T)
		 */
		for (int i = 1; i < N; i++) {
			for (int j = 2; j <= T; j++) {
				if (maxSum[i - 1][j - 1].getValue() + v.get(i) > maxSum[i - 1][j - 1].getMax()) {
					maxSum[i][j].setValue(maxSum[i - 1][j - 1].getValue() + v.get(i));
					if (maxSum[i][j].getValue() > maxSum[i - 1][j].getMax())
						maxSum[i][j].setMax(maxSum[i][j].getValue());
					else
						maxSum[i][j].setMax(maxSum[i - 1][j].getMax());
				}
				else {
					maxSum[i][j].setValue(maxSum[i - 1][j - 1].getMax());
					if (maxSum[i][j].getValue() > maxSum[i - 1][j].getMax())
						maxSum[i][j].setMax(maxSum[i][j].getValue());
					else
						maxSum[i][j].setMax(maxSum[i - 1][j].getMax());
				}
			}
		}

		int result; // indexul maximului de pe ultima coloana
		int j = T; // index ultima coloana
		result = maxIndexOfLastCol(maxSum, T);

		this.bestSum = maxSum[result][T].getValue();
		// salvare suma maxima in campul aferent al clasei

		int index = T; // trebuie sa avem T sloturi selectate

		/*
		 * Constructia solutiei
		 * Se pun intervale in ordine descrescatoare. De la cel mai din dreapta la cel mai din stanga.
		 */
		boolean intervalOpened = false;
		Interval interval = new Interval();

		while (index != 0 && result > 0) {

			Entity current = maxSum[result][j];
			Entity inDiagonal = maxSum[result - 1][j - 1];

			if (! intervalOpened) {

				// pentru limita din dreapta a unui interval, se cauta maximul
				// maximul trebuie sa fie format din maxSum[i - 1][j - 1] + v[i]
				if (current.getValue() == current.getMax() && 
						current.getValue() - v.get(result) == inDiagonal.getValue()) {	
					intervalOpened = ! intervalOpened; // se atentioneaza ca s-a deschis interval
					interval.setStop(result + 1); // outputul cere indexare incepand de la 1
					result--;
					j--;
					index--; // s-a gasit un slot
				}

				// caz special: intervale gen [b1 b2] cu b1 = b2 (de un singur element)
				else if (current.getValue() - v.get(result) != inDiagonal.getValue() && 
						current.getValue() == inDiagonal.getValue() && current.getValue() == current.getMax()) {
					interval = new Interval(result + 1, result + 1);
					solution.add(interval);
					interval = new Interval();
					result--;
					j--;
					index--;
				}
				
				else { // nu este maxim
					result--; // incercam urmatorul element
				}
			}

			else { // intervalul este deschis

				// elementul curent trebuie sa fie suma de elementul de la i - 1 si j - 1 adunat cu v[i]
				// in plus, elementul curent nu trebuie sa fie maximul coloanei de mai sus
				if (current.getValue() - v.get(result) == inDiagonal.getValue() && 
						current.getValue() != inDiagonal.getMax()) {
					result--;
					j--;
					index--; // se decrementeaza numarul de elemente necesare pentru completare
				}

				else { // elementul nu satisface conditiile --> este capat de interval
					intervalOpened = ! intervalOpened;
					interval.setStart(result + 1); // modificare start interval
					index--;
					solution.add(interval);
					interval = new Interval(); // brand new interval :)
					result--;
					j--;
				}
			}
		}

		if (index == 1) {
			// daca while-ul a iesit pe result > 0, avem de adaugat ultimul element
			interval.setStart(result + 1);
			solution.add(interval);
		}

		return solution;
		/*
		["hip" "hip"]
		hip hip array! :)) Am gasit solutia.
		*/

	}

	/**
	 * 
	 * @param m - matricea
	 * @param T - indexul ultimei coloane
	 * @return indexul maximului ultimei coloane
	 */
	private int maxIndexOfLastCol (Entity[][] m, int T) {

		int max = m[0][T].getValue();
		int pos = 0;
		int size = m.length;

		for (int i = 1; i < size; i++) {
			if (m[i][T].getValue() >= max) {
				max = m[i][T].getValue();
				pos = i;
			}
		}

		return pos;
	}

	public static void main(String[] args) throws IOException {

		TSlots p = new TSlots();
		p.read(FISIER_INTRARE);
		
		ArrayList<Interval> result;

		result = p.chooseSlots();
		p.write(FISIER_IESIRE, result);

	}

}

abstract class Pair<T, U> {
	
	private T first;
	private U second;

	public Pair (T first, U second) {
		this.first = first;
		this.second = second;
	}
	
	public Pair () {
	}
	
	public void setFirst (T first) {
		this.first = first;
	}
	
	public void setSecond (U second) {
		this.second = second;
	}
	
	public T getFirst () {
		return first;
	}
	
	public U getSecond () {
		return second;
	}

	public String toString () {
		return "[" + first + ", " + second + "]";
	}

	
}

/*
 * Structura care se pune in fiecare element al matricei maxSum
 * Contine un camp pentru valoarea elementului si unul pentru
 * valoarea maximului coloanei de pana la el
 */
class Entity extends Pair<Integer, Integer> {

	public Entity (Integer value, Integer max) {
		super(value, max);
	}
	
	public Entity () {
		super(0, 0);
	}

	public void setValue (Integer value) {
		super.setFirst(value);
	}

	public void setMax (Integer max) {
		super.setSecond(max);
	}
	
	public Integer getValue () {
		return super.getFirst();
	}
	
	public Integer getMax () {
		return super.getSecond();
	}

}

class Interval extends Pair<Integer, Integer> {

	public Interval () {
		super(0, 0);
	}

	public Interval (Integer start, Integer stop) {
		super(start, stop);
	}

	public Integer getStart () {
		return super.getFirst();
	}

	public void setStart (Integer start) {
		super.setFirst(start);
	}

	public Integer getStop () {
		return super.getSecond();
	}

	public void setStop (Integer stop) {
		super.setSecond(stop);
	}

}
