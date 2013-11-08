package gamelogic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import structs.Pair;
import enums.MARK;
import enums.PLAYER;
import enums.WINNER;
import exceptions.NoMorePiecesOnTableException;

public class Board {

	private final Pair<MARK, IntegerPair>[][] board;

	/*
	 board-ul este reprezentat ca o pereche de MARK si
	 distanta pe orizontala si verticala pana la punctul adiacent
	 Pe exemplul de mai jos, punctul aflat la [0][3] va avea alaturata perechea
	 (3, 1) - pentru ca elementele adiacente de pe orizontala sunt pe a treia pozitie
	 de la pozitia punctului [0][3], iar cele de pe verticala la 1
	 NOTE! IntegerPair din board-> distanta pe orizontala in fst si pe verticala in snd
	 */

	private TreeSet<Pair<Integer, Integer>> activePoints;
	// punctele prin care se reprezinta efectiv harta
	// vezi metoda setActivePoints()

	public static final int MAT_DIM = 13; // dimensiunea matricei
	private static final int INF = Integer.MAX_VALUE; // infinity

	/*
	 * Board-ul este reprezentat ca o matrice astfel:
	 * 			  0 1 2 3 4 5 6
	 * 			0 *     *     *
	 * 			1   *   *   *
	 * 			2     * * *
	 * 			3 * * *   * * *
	 * 			4     * * *
	 * 			5   *   *   *
	 * 			6 *     *     *
	 * Se observa ca nu toate pozitiile din matrice vor fi completate.
	 * In plus, matricea se va borda cu 3 linii/coloane.
	 * Deci dimensiunea matricei va fi 13x13. Matricea de sus este DOAR un exemplu!!!
	 */

	/**
	 * Seteaza punctele active de pe board.
	 * Puncte active = punctele unde vor fi puse piesele
	 * Pe restul de puncte vor fi puse MARK.OBSTACLE
	 */
	private void setActivePoints () {

		activePoints = new TreeSet<Pair<Integer, Integer>>(new PointComparator());

		for (int i = 3; i < 10; i = i + 6) {
			for (int j = 3; j < 10; j = j + 3) {
				activePoints.add(new Pair<Integer, Integer>(i, j));
			}
		}

		for (int i = 4; i < 9; i = i + 4) {
			for (int j = 4; j < 9; j = j + 2) {
				activePoints.add(new Pair<Integer, Integer>(i, j));
			}
		}

		for (int i = 5; i < 8; i = i + 2) {
			for (int j = 5; j < 8; j++) {
				activePoints.add(new Pair<Integer, Integer>(i, j));
			}
		}

		int i = 6;
		for (int j = 3; j < 6; j++) {
			activePoints.add(new Pair<Integer, Integer>(i, j));
		}
		for (int j = 7; j < 10; j++) {
			activePoints.add(new Pair<Integer, Integer>(i, j));
		}

	}

	/**
	 * 
	 * @return toate punctele de pe tabla
	 */
	public TreeSet<Pair<Integer, Integer>> getAllPointsOfBoard () {

		return activePoints;

	}

	/**
	 * initilizeaza board-ul
	 */
	public Board () {

		setActivePoints();
		board = new MarkPair[MAT_DIM][MAT_DIM];
		/*
		 * MarkPair = pereche formata din (MARK, (Integer, Integer))
		 * unde (,) reprezinta pereche
		 */

		for (int i = 0; i < MAT_DIM; i++) {
			for (int j = 0; j < MAT_DIM; j++) {
				board[i][j] = new MarkPair();
				board[i][j].setFirst(MARK.OBSTACLE); // seteaza obstacol
				board[i][j].setSecond(new IntegerPair(-INF, -INF));
				if (activePoints.contains(new Pair<Integer, Integer>(i, j))) {
					board[i][j].setFirst(MARK.EMPTY); // seteaza empty
				}
			}
		}

		// setare distante
		// hardcodate
		// NOTE! IntegerPair din board-> distanta pe orizontala in fst si pe verticala in snd

		for (int i = 3; i < 10; i = i + 6) {
			for (int j = 3; j < 10; j = j + 6) {
				board[i][j].setSecond(new IntegerPair(3,3));
			}
		}

		for (int i = 4; i < 9; i = i + 4) {
			for (int j = 4; j < 9; j = j + 4) {
				board[i][j].setSecond(new IntegerPair(2, 2));
			}
		}

		for (int i = 5; i < 8; i = i + 2) {
			for (int j = 5; j < 8; j++) {
				board[i][j].setSecond(new IntegerPair(1, 1));
			}
		}

		int k = 3;
		for (int i = 3; i < 6; i++) {
			board[6][i].setSecond(new IntegerPair(1, k));
			k--;
		}

		k = 1;
		for (int i = 7; i < 10; i++) {
			board[6][i].setSecond(new IntegerPair(1, k));
			k++;
		}

		k = 3;
		for (int i = 3; i < 6; i++) {
			board[i][6].setSecond(new IntegerPair(k, 1));
			k--;
		}

		k = 1;
		for (int i = 7; i < 10; i++) {
			board[i][6].setSecond(new IntegerPair(k, 1));
			k++;
		}

	}

	/**
	 * Creeaza un board nou identic cu board-ul @param b (dar cu memorie proprie/alocata)
	 * 
	 */
	public Board (Board b) {

		setActivePoints();
		board = new MarkPair[MAT_DIM][MAT_DIM];

		for (int i = 0; i < MAT_DIM; i++) {
			for (int j = 0; j < MAT_DIM; j++) {
				board[i][j] = new MarkPair();
				IntegerPair p = b.board[i][j].getSecond();
				int pi, pj;
				pi = p.getFirst();
				pj = p.getSecond();
				board[i][j].setBoth(b.board[i][j].getFirst(), new IntegerPair(pi, pj));
			}
		}

	}

	/**
	 * 
	 * @return matricea care tine minte Board-ul
	 */
	public Pair<MARK, IntegerPair>[][] getBoardMatrix () {

		return this.board;

	}

	/**
	 * 
	 * @return dimensiunea matricei
	 */
	public static int getMatrixDimension () {

		return MAT_DIM;

	}

	/**
	 * Pune piesa @param mark pe tabla la pozitia indicata
	 * @param line - linia
	 * @param col - coloana
	 * @param mark - cine a facut mutarea
	 */
	public void putPiece (int line, int col, MARK mark) {

		board[line][col].setFirst(mark);

	}

	/**
	 * Pune piesa @ param mark pe pozitia @ param pos
	 * @param pos - pozitita
	 * @param mark - piesa
	 */
	public void putPiece (Pair<Integer, Integer> pos, MARK mark) {

		int i = pos.getFirst();
		int j = pos.getSecond();

		board[i][j].setFirst(mark);

	}

	/**
	 * Pune piesa @ param mark pe pozitia @ param pos
	 * @param pos - pozitita
	 * @param mark - piesa
	 */
	public void putPiece (IntegerPair pos, MARK mark) {

		int i = pos.getFirst();
		int j = pos.getSecond();

		board[i][j].setFirst(mark);

	}

	/**
	 * Sterge piesa de la pozitia indicata de pe tabla
	 * @param line - lina
	 * @param col - coloana
	 */
	public void removePiece (int line, int col) {

		board[line][col].setFirst(MARK.EMPTY);

	}

	/**
	 * Sterge piesa de la pozitia @ param pos
	 * @param pos - pozitia
	 */
	public void removePiece (Pair<Integer, Integer> pos) {

		int i = pos.getFirst();
		int j = pos.getSecond();

		board[i][j].setFirst(MARK.EMPTY);

	}

	/**
	 * Sterge piesa de la pozitia @ param pos
	 * @param pos - pozitia
	 */
	public void removePiece (IntegerPair pos) {

		int i = pos.getFirst();
		int j = pos.getSecond();

		board[i][j].setFirst(MARK.EMPTY);

	}

	/**
	 * Muta o piesa de la o pozitie la alta.
	 * 
	 * @param from - pozitia din care se muta
	 * @param to - pozitia pe care se muta
	 */
	public void movePiece (Pair<Integer, Integer> from, Pair<Integer, Integer> to) {

		int i, j;
		i = from.getFirst();
		j = from.getSecond();
		MARK mark = board[i][j].getFirst();

		board[i][j].setFirst(MARK.EMPTY);

		i = to.getFirst();
		j = to.getSecond();
		board[i][j].setFirst(mark);

	}

	/**
	 * 
	 * @return - pozitiile libere
	 */
	public ArrayList<Pair<Integer, Integer>> getFreeSquares () {

		ArrayList<Pair<Integer, Integer>> freeSquares = new ArrayList<Pair<Integer, Integer>>();
		Iterator<Pair<Integer, Integer>> it = activePoints.iterator();

		while (it.hasNext()) {

			Pair<Integer, Integer> current = it.next();
			int i, j;
			i = current.getFirst();
			j = current.getSecond();

			if (board[i][j].getFirst() == MARK.EMPTY) {
				freeSquares.add (current);
			}

		}

		return freeSquares;

	}

	/**
	 * Sterge o piesa dintr-o pozitie.
	 * 
	 * @param pos - pozitia care trebuie curatata
	 */
	public void clearSquare (Pair<Integer, Integer> pos) {

		int i, j;
		i = pos.getFirst();
		j = pos.getSecond();

		board[i][j].setFirst(MARK.EMPTY);

	}

	/**
	 * Intoarce mutarile posibile dintr-o pozitie @ param pos pentru jucatorul @param play_as
	 * 
	 * @param pos - pozitia curenta
	 * @param play_as - jucatorul pentru care se face verificarea
	 * @return - mutarile posibile sub forma de pereche de perechi astfel:
	 * 		(pozitiaCurenta, mutarePosibila)
	 */
	public ArrayList<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>>
	getPossibleMoves (Pair<Integer, Integer> pos, PLAYER play_as) {

		ArrayList<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> moves
		= new ArrayList<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>>();
		int i = pos.getFirst();
		int j = pos.getSecond();
		int oriz = board[i][j].getSecond().getFirst();
		int vert = board[i][j].getSecond().getSecond();

		MARK mark_to_check;
		if (play_as == PLAYER.BLACK) {
			mark_to_check = MARK.BLACK;
		}
		else {
			mark_to_check = MARK.WHITE;
		}

		if (board[i][j].getFirst() == mark_to_check) {

			if (board[i][j + oriz].getFirst() == MARK.EMPTY) { // DREAPTA
				Pair<Integer, Integer> currPos = new Pair<Integer, Integer>(i, j);
				Pair<Integer, Integer> mvSpace = new Pair<Integer, Integer>(i, j + oriz);
				moves.add(new Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>(currPos, mvSpace));
			}


			if (board[i][j - oriz].getFirst() == MARK.EMPTY) { // STANGA
				Pair<Integer, Integer> currPos = new Pair<Integer, Integer>(i, j);
				Pair<Integer, Integer> mvSpace = new Pair<Integer, Integer>(i, j - oriz);
				moves.add(new Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>(currPos, mvSpace));
			}

			if (board[i + vert][j].getFirst() == MARK.EMPTY) { // SUS
				Pair<Integer, Integer> currPos = new Pair<Integer, Integer>(i, j);
				Pair<Integer, Integer> mvSpace = new Pair<Integer, Integer>(i + vert, j);
				moves.add(new Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>(currPos, mvSpace));
			}

			if (board[i - vert][j].getFirst() == MARK.EMPTY) { // JOS
				Pair<Integer, Integer> currPos = new Pair<Integer, Integer>(i, j);
				Pair<Integer, Integer> mvSpace = new Pair<Integer, Integer>(i - vert, j);
				moves.add(new Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>(currPos, mvSpace));
			}

		}

		return moves;

	}

	/**
	 * 
	 * @param play_as - jucatorul pentru care se verifica mutarile posibile
	 * @return toate mutarile posibile (din orice pozitie)
	 */
	public ArrayList<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>>
	getPossibleMoves(PLAYER play_as) {

		ArrayList<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> mvs =
				new ArrayList<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>>();

		Iterator<Pair<Integer, Integer>> it = activePoints.iterator();
		while (it.hasNext()) {
			ArrayList<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> mvsCurrent =
					new ArrayList<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>>();
			mvsCurrent = getPossibleMoves(it.next(), play_as);
			mvs.addAll(mvsCurrent);
		}

		return mvs;

	}

	/**
	 * 
	 * @param pos - pozitia
	 * @return - piesa care se afla pe pozitia @pos
	 */
	public MARK getMark (Pair<Integer, Integer> pos) {

		int i, j;
		i = pos.getFirst();
		j = pos.getSecond();

		return board[i][j].getFirst();

	}

	/**
	 * 
	 * @param play_as - jucatorul din perspectiva caruia trebuie sa determinam scorul
	 * @return scorul tablei
	 * 
	 * Metoda este o referinta la clasa BoardScore care se ocupa de determinarea scorului
	 * 
	 */
	public int getScore (PLAYER play_as) {

		BoardScore score = new BoardScore(this);
		return score.getScore(play_as);
	}

	/**
	 * 
	 * @return castigatorul
	 * 
	 * metoda face referinta la clasa BoardScore
	 * 
	 */
	public WINNER getWinner() {

		BoardScore score = new BoardScore(this);
		return score.getWinner();

	}

	@Override
	public String toString () {

		String s = "";

		for (int i = 3; i < 10; i++) {
			for (int j = 3; j < 10; j++) {
				if (board[i][j].getFirst() == MARK.EMPTY) {
					s += "_";
				}
				if (board[i][j].getFirst() == MARK.OBSTACLE) {
					s += " ";
				}
				if (board[i][j].getFirst() == MARK.BLACK) {
					s += "#";
				}
				if (board[i][j].getFirst() == MARK.WHITE) {
					s += "*";
				}
			}
			s += '\n';
		}

		return s;

	}


	public static void main(String[] args) throws NoMorePiecesOnTableException {

		Board b = new Board();

		System.out.println(b.getAllPointsOfBoard());

		b.putPiece(new Pair<Integer, Integer>(3, 3), MARK.BLACK);
		b.putPiece(new Pair<Integer, Integer>(3, 6), MARK.BLACK);
		b.putPiece(new Pair<Integer, Integer>(3, 9), MARK.BLACK);
		b.putPiece(new Pair<Integer, Integer>(4, 4), MARK.BLACK);
		b.putPiece(new Pair<Integer, Integer>(4, 8), MARK.BLACK);
		b.putPiece(new Pair<Integer, Integer>(8, 4), MARK.BLACK);
		b.putPiece(new Pair<Integer, Integer>(6, 7), MARK.WHITE);

		System.out.println(b.getScore(PLAYER.BLACK));
		System.out.println(b);


		PieceRemover remover = new CheckAllPieceRemover(b);
		remover.removePieceAfterCompletingLine(PLAYER.BLACK);

		System.out.println(b.getScore(PLAYER.BLACK));
		System.out.println(b);

	}

}

class MarkPair extends Pair<MARK, IntegerPair> {
}
