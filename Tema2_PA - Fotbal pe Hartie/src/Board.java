
import java.util.HashSet;
import java.util.Iterator;

public class Board {

	MovesList[][] field; // matricea ce reprezinta terenul de joc
	// in matrice se vor pune set-uri care tin minte ce mutari s-au facut dintr-un punct
	// 0 - a fost facuta mutare catre Nord
	// 1 - Nord-Est
	// 2 - Est
	// ..
	// 7 - Nord-Vest
	
	int lines; // numarul de linii ale terenului
	int columns; // numarul de coloane
	int goalLineUp; // linia portii din partea de sus
	int goalLineDown; // linia portii din partea de jos
	int barLeft; // bara stanga;
	int barRight; // bara dreapta
	int outLineLeft; // linia de out din stanga
	int outLineRight; // linia de out din dreapta
	int goalSize; // dimensiunea portii

	Pair<Integer, Integer> currentPosition; // pozitia curenta

	/**
	 * @param lines = numarul de puncte de pe linia de out
	 * @param columns = numarul de puncte de pe linia de out de poarta
	 * @param goalSize = diferenta dintre bare (numarul de patratele ale portii)
	 */
	public Board (int lines, int columns, int goalSize) {
		
		/*
		 * Initializari, alocare memorie, salvare campuri
		 */
		field = new MovesList[lines][columns];
		for (int i = 0; i < lines; i++) {
			for (int j = 0; j < columns; j++) {
				field[i][j] = new MovesList();
			}
		}
		this.lines = lines;
		this.columns = columns;
		this.goalLineUp = 1;
		this.goalLineDown = lines - 2;
		this.outLineLeft = 0;
		this.outLineRight = columns - 1;
		this.goalSize = goalSize;

		/*
		 *  Marcare puncte de margine (pentru a nu iesi din teren)
		 */
		int middle = columns / 2;
		barLeft = middle - goalSize / 2;
		barRight = middle + goalSize / 2;

		this.currentPosition = new Pair<Integer, Integer> (lines / 2, middle);
		// pozitia curenta este centrul terenului
		
		for (int i = 0; i < columns; i++) {
			// marcarea punctelor de pe linia de out de poarta
			// mai putin marcarea barelor si a punctelor de pe linia portii
			if (i < barLeft || i > barRight) {

				field[goalLineUp][i].add(0);
				field[goalLineUp][i].add(1);
				field[goalLineUp][i].add(2);
				field[goalLineUp][i].add(6);
				field[goalLineUp][i].add(7);
	
				field[goalLineDown][i].add(2);
				field[goalLineDown][i].add(3);
				field[goalLineDown][i].add(4);
				field[goalLineDown][i].add(5);
				field[goalLineDown][i].add(6);

			}
		}

		//marcarea barelor
		field[goalLineUp][barLeft].add(0);
		field[goalLineUp][barLeft].add(6);
		field[goalLineUp][barLeft].add(7);
		
		field[goalLineUp][barRight].add(0);
		field[goalLineUp][barRight].add(1);
		field[goalLineUp][barRight].add(2);
		
		field[goalLineDown][barLeft].add(4);
		field[goalLineDown][barLeft].add(5);
		field[goalLineDown][barLeft].add(6);
		
		field[goalLineDown][barRight].add(2);
		field[goalLineDown][barRight].add(3);
		field[goalLineDown][barRight].add(4);
		
		for (int i = goalLineUp; i <= goalLineDown; i++) {
			// marcarea punctelor de pe liniile de out (liniile laterale)
			field[i][outLineLeft].add(0);
			field[i][outLineLeft].add(4);
			field[i][outLineLeft].add(5);
			field[i][outLineLeft].add(6);
			field[i][outLineLeft].add(7);
			
			field[i][outLineRight].add(0);
			field[i][outLineRight].add(1);
			field[i][outLineRight].add(2);
			field[i][outLineRight].add(3);
			field[i][outLineRight].add(4);
		}
		
	}
	
	public Board (Board b) {
		
		/*
		 * Initializari, alocare memorie, salvare campuri
		 */
		field = new MovesList[b.lines][b.columns];
		for (int i = 0; i < b.lines; i++) {
			for (int j = 0; j < b.columns; j++) {
				field[i][j] = new MovesList();
			}
		}
		this.lines = b.lines;
		this.columns = b.columns;
		this.goalLineUp = 1;
		this.goalLineDown = b.lines - 2;
		this.outLineLeft = 0;
		this.outLineRight = b.columns - 1;
		
		this.currentPosition = b.currentPosition;
		
		int middle = b.columns / 2;
		barLeft = middle - b.goalSize / 2;
		barRight = middle + b.goalSize / 2;
		
		/*
		 * Copiere mutari facute din fiecare punct
		 */
		for (int i = 0; i < b.lines; i++) {
			for (int j = 0; j < b.columns; j++) {
				field[i][j].addAll(b.field[i][j]);
			}
		}
	}
	
	/**
	 * 
	 * @param direction - directia in care se muta
	 * direction poate fi o valoare din multimea
	 * {0, 1, 2, 3, 4, 5, 6, 7}
	 */
	public void makeMove (int direction) {
		
		int i = currentPosition.getFirst();
		int j = currentPosition.getSecond();
		field[i][j].add(direction);
		
		switch (direction) {
		case 0:
			currentPosition.setBoth(i - 1, j);
			break;
		case 1:
			currentPosition.setBoth(i - 1,  j + 1);
			break;
		case 2:
			currentPosition.setBoth(i, j + 1);
			break;
		case 3:
			currentPosition.setBoth(i + 1, j + 1);
			break;
		case 4:
			currentPosition.setBoth(i + 1, j);
			break;
		case 5:
			currentPosition.setBoth(i + 1, j - 1);
			break;
		case 6:
			currentPosition.setBoth(i, j - 1);
			break;
		case 7:
			currentPosition.setBoth(i - 1, j - 1);
			break;
		default:
			System.out.println("Incorrect direction.\n");
			break;
		}
		
		i = currentPosition.getFirst();
		j = currentPosition.getSecond();
		
		field[i][j].add(complementDirection(direction));
		// se marcheaza si din noua pozitie arcul facut
		
	}
	
	public void undoMove (int direction) {
		
		int i = currentPosition.getFirst();
		int j = currentPosition.getSecond();
		field[i][j].remove(direction);

		switch (direction) {
		case 0:
			currentPosition.setBoth(i - 1, j);
			break;
		case 1:
			currentPosition.setBoth(i - 1,  j + 1);
			break;
		case 2:
			currentPosition.setBoth(i, j + 1);
			break;
		case 3:
			currentPosition.setBoth(i + 1, j + 1);
			break;
		case 4:
			currentPosition.setBoth(i + 1, j);
			break;
		case 5:
			currentPosition.setBoth(i + 1, j - 1);
			break;
		case 6:
			currentPosition.setBoth(i, j - 1);
			break;
		case 7:
			currentPosition.setBoth(i - 1, j - 1);
			break;
		default:
			System.out.println("Incorrect direction.\n");
			break;
		}
		
		i = currentPosition.getFirst();
		j = currentPosition.getSecond();

		field[i][j].remove(complementDirection(direction));
		
	}
	
	/**
	 * @param direction - directia al carui complement il vrem
	 * @return - complementul directiei date ca parametru ( invers 180 de grade) 
	 * -1 daca directia data este gresita
	 */
	public static Integer complementDirection (int direction) {

		switch (direction) {
		case 0:
			return 4;
		case 1:
			return 5;
		case 2:
			return 6;
		case 3:
			return 7;
		case 4:
			return 0;
		case 5:
			return 1;
		case 6:
			return 2;
		case 7:
			return 3;
		default:
			System.out.println("Incorrect direction.\n");
			return -1;
		}		
	}
	
	/**
	 * 
	 * @return - multimea de mutari posibile din pozitia curenta
	 */
	public HashSet<Integer> getPossibleMoves () {

		HashSet<Integer> moves = new HashSet<Integer>();
		int i = currentPosition.getFirst();
		int j = currentPosition.getSecond();

		for (int k = 0; k <= 7; k++) {
			if (!field[i][j].contains(k)) {
				moves.add(k);
			}
		}

		return moves;

	}
	
	public Pair<Integer, Integer> getCurrentPosition () {
		return currentPosition;
	}
	
	/**
	 * 
	 * @return - castigatorul meciului
	 */
	public WINNER getWinner () {
		
		int i = currentPosition.getFirst();
		int j = currentPosition.getSecond();

		if (i < goalLineUp) {
			return WINNER.PLAYER_ATTACKING_TO_NORTH;
		}
		if (i > goalLineDown) {
			return WINNER.PLAYER_ATTACKING_TO_SOUTH;
		}
		
		if (field[i][j].size() == 8) {
			return WINNER.NO_MOVES_LEFT;
		}
		
		return WINNER.NOBODY;
		
	}
	
	/**
	 * 
	 * @param lastMove - ultima mutare facuta
	 * @return determina daca jucatorul mai are dreptul la o mutare
	 */
	public boolean stillMyTurn (int lastMove) {

		int i = currentPosition.getFirst();
		int j = currentPosition.getSecond();
		
		HashSet<Integer> currentCopy = new HashSet<Integer>();
		currentCopy.addAll(field[i][j]);
		currentCopy.remove(complementDirection(lastMove));
		
		return currentCopy.size() != 0;

	}
	
	public static void main(String[] args) {

		Board b1 = new Board(13, 9, 2);
		Board b = new Board(b1);
		
		System.out.println(b.field[1][3]);

		while (b.getWinner() == WINNER.NOBODY) {
			System.out.println("Current position is " + b.currentPosition);
			Iterator<Integer> it = b.getPossibleMoves().iterator();
			System.out.println("Possible moves from these position are " + b.getPossibleMoves());
			int mv = 0;
			int k = ((int) (System.currentTimeMillis() % 7)) + 1;
			while (it.hasNext() && k-- != 0) {
				mv = it.next();
			}
			System.out.println("Move made this turn is " + mv +  "\n");
			b.makeMove(mv);
		}
		
		System.out.println(b.getWinner());
		
	}

}


class MovesList extends HashSet<Integer> {}