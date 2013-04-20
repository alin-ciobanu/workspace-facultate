package problema1;

/**
 * Proiectarea Algoritmilor, 2013
 * Lab 1: Divide et Impera
 * Task 1.2: Se da un numar natural n. Scrieti un algoritm de complexitate O(log n) 
 * care sa calculeze sqrt(n)
 * 
 * @author 	Mihai Bivol, Alex Olteanu
 * @email	alexandru.olteanu@cs.pub.ro
 */

public class ComputeSqrt {
	
	public static void main(String[] args) {
		
		
		double x = 100;
		double result = sqrt(x, 0, 213919, 0.001);

		System.out.println(result);
		
		/*TODO Calculati radicalul pentru trei valori alese de voi,
		 * folosind functia sqrt definita mai jos. Cel putin o valoare 
		 * trebuie sa fie subunitara. Precizia va fi de 0.001
		 * 
		 * Decideti care va fi valoarea upper folosita. 
		 * Hint: ce se intampla cand x<1?
		 */

	    
		
	}

	/**
	 * Function that says if two values are equal within precision
	 */
	private static boolean equal(double x, double y, double precision)
	{
	    return Math.abs(x - y) < precision;
	}

	/**
	 * Iterative function to compute sqrt
	 */
	private static double sqrt(double x, double lower, double upper, double precision)
	{
		
		double m = lower + (upper - lower) / 2;
		
		while (! equal(m * m, x, precision)) {
			if (m * m < x)
				lower = m + precision;
			else
				upper = m - precision;
			m = lower + (upper - lower) / 2;
		}

		return m;

	    // TODO Cautati intre lower si upper o valoare care ridicata
	    // la patrat sa dea x.
	    // La calcularea pozitiei de mijloc folositi
	    //       double m = lower + (upper - lower) / 2;
	    // pentru a evita overflow la adunarea pe double
	    // Folositi functia equal pentru a verifica cu aproximare egalitatea
	    
	}
}
