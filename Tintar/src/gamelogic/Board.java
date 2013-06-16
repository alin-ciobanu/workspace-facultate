package gamelogic;

import java.util.HashSet;
import structs.*;

public class Board {

	private Pair<MARK, IntegerPair>[][] board;
	
	/*
	 board-ul este reprezentat ca o pereche de MARK si
	 distanta pe orizontala si verticala pana la punctul adiacent
	 Pe exemplul de mai jos, punctul aflat la [0][3] va avea alaturata perechea
	 (3, 1) - pentru ca elementele adiacente de pe orizontala sunt pe a treia pozitie
	 de la pozitia punctului [0][3], iar cele de pe verticala la 1
	 NOTE! IntegerPair din board-> distanta pe orizontala in fst si pe verticala in snd
	 */

	private HashSet<Pair<Integer, Integer>> activePoints;
	// punctele prin care se reprezinta efectiv harta
	// vezi metoda setActivePoints()
	
	public static final int MAT_DIM = 13; // dimensiunea matricei
	public static final int INF = Integer.MAX_VALUE; // infinity
	
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
		
		activePoints = new HashSet<Pair<Integer, Integer>>();
		
		for (int i = 3; i < 10; i = i + 6) {
			for (int j = 3; j < 10; i = i + 3) {
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
				board[i][j].setFirst(MARK.OBSTACLE); // seteaza obstacol 
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
		
		board = new MarkPair[MAT_DIM][MAT_DIM];
		
		for (int i = 0; i < MAT_DIM; i++) {
			for (int j = 0; j < MAT_DIM; j++) {
				IntegerPair p = b.board[i][j].getSecond();
				int pi, pj;
				pi = p.getFirst();
				pj = p.getSecond();
				board[i][j].setBoth(b.board[i][j].getFirst(), new IntegerPair(pi, pj));
			}
		}
		
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
	 * Sterge piesa de la pozitia indicata de pe tabla
	 * @param line - lina
	 * @param col - coloana
	 */
	public void removePiece (int line, int col) {
		
		board[line][col].setFirst(MARK.EMPTY);
		
	}
	
	/**
	 * 
	 * @return scorul pentru o tabla (din perspectiva jucatorului play_as)
	 * Cu cat o tabla este mai buna (aduce un jucator mai aproape de castig), 
	 *  cu atat va avea un scor mai mare
	 */
	public int getScore (PLAYER play_as) {

		int score = 0;
		
		if (play_as == PLAYER.WHITE) { // din perspectiva jucatorului WHITE
			
		}
		
		else { // din perspectiva jucatorului BLACK
			
		}
		
		return score;
		
	}
	
	/**
	 * 
	 * @return castigatorul
	 */
	public WINNER getWinner () {
		
		// numarul de piese de pe tabla pentru fiecare jucator
		int blacks = 0;
		int whites = 0;
		
		for (int i = 0; i < MAT_DIM; i++) {
			for (int j = 0; j < MAT_DIM; j++) {
				if (board[i][j].getFirst() == MARK.BLACK)
					blacks++;
				if (board[i][j].getFirst() == MARK.WHITE)
					whites++;
			}
		}

		if (blacks < 3)
			return WINNER.WHITE;
		else if (whites < 3)
			return WINNER.BLACK;
		else if (blacks == 3 && whites == 3)
			return WINNER.DRAW;
		else
			return WINNER.NOBODY;
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

class MarkPair extends Pair<MARK, IntegerPair> {
}
