import java.util.Arrays;


public class Name implements Comparable {

	String nume;
	String prenume;
	
	public Name (String nume, String prenume) {
		this.nume = nume;
		this.prenume = prenume;
	}
	
	public boolean equals (Object o) {
		Name n = (Name) o;
		return nume.equals(n.nume) && prenume.equals(n.prenume);
	}

	public int compareTo(Object arg0) {
		Name n = (Name)arg0;
		if (nume.equals(n.nume))
			return nume.compareTo(n.nume);
		else
			return prenume.compareTo (n.prenume);
	}
	
	public String toString () {
		return nume + " " + prenume;
	}
	
	public static void main (String[] args) {
		Name[] vect = { new Name("Popa", "Mihai"),
			new Name("Gigel", "Gheorghel"),
			new Name("Popa", "Mihaela")
		};
		
		Arrays.sort(vect);
		for (int i=0; i<3; i++)
			System.out.println(vect[i]);
	}

}
