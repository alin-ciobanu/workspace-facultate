package gamelogic;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

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
		
		setActivePoints();
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

			if (board[i + oriz][j].getFirst() == MARK.EMPTY) { // DREAPTA
				Pair<Integer, Integer> currPos = new Pair<Integer, Integer>(i, j);
				Pair<Integer, Integer> mvSpace = new Pair<Integer, Integer>(i + oriz, j);
				moves.add(new Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>(currPos, mvSpace));
			}
	
			
			if (board[i - oriz][j].getFirst() == MARK.EMPTY) { // STANGA
				Pair<Integer, Integer> currPos = new Pair<Integer, Integer>(i, j);
				Pair<Integer, Integer> mvSpace = new Pair<Integer, Integer>(i - oriz, j);
				moves.add(new Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>(currPos, mvSpace));
			}
			
			if (board[i][j + vert].getFirst() == MARK.EMPTY) { // SUS
				Pair<Integer, Integer> currPos = new Pair<Integer, Integer>(i, j);
				Pair<Integer, Integer> mvSpace = new Pair<Integer, Integer>(i, j + vert);
				moves.add(new Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>(currPos, mvSpace));
			}
			
			if (board[i][j - vert].getFirst() == MARK.EMPTY) { // JOS
				Pair<Integer, Integer> currPos = new Pair<Integer, Integer>(i, j);
				Pair<Integer, Integer> mvSpace = new Pair<Integer, Integer>(i, j - vert);
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
	 * Metoda folosita pentru calcularea scorului in metoda getScore()
	 * @param blacks - numarul de piese negre de pe o linie
	 * @param whites - numarul de piese albe de pe o linie
	 * @param blackScore - scorul pentru jucatorul cu piese negre
	 * @param whiteScore - scorul pentru jucatorul cu piese albe
	 * @return pereche cu scorul pentru jucatorul cu negrele si pentru cel cu albele
	 */
	private IntegerPair updateScore (int blacks, int whites, int blackScore, int whiteScore) {
		
		if (whites == 1 && blacks == 0) {
			whiteScore += 1;
			blackScore -= 2;
		}
		else if (whites == 2 && blacks == 0) {
			whiteScore += 3;
			blackScore -= 4;
		}
		else if (whites == 3 && blacks == 0) {
			whiteScore += 10;
			blackScore -= 15;
		}
		else if (whites == 2 && blacks == 1) {
			whiteScore += 1;
			blackScore -= 1;
		}
		else if (blacks == 1 && whites == 0) {
			blackScore += 1;
			whiteScore -=2;
		}
		else if (blacks == 2 && whites == 0) {
			blackScore += 3;
			whiteScore -= 4;
		}
		else if (blacks == 3 && whites == 0) {
			blackScore += 10;
			whiteScore -= 15;
		}
		else if (blacks == 2 && whites == 1) {
			blackScore += 1;
			whiteScore -= 1;
		}
		
		return new IntegerPair(blackScore, whiteScore);
		
	}
	
	
	/**
	 * 
	 * @return scorul pentru o tabla (din perspectiva jucatorului play_as)
	 * Cu cat o tabla este mai buna (aduce un jucator mai aproape de castig), 
	 *  cu atat va avea un scor mai mare
	 */
	public int getScore (PLAYER play_as) {

		int whiteScore, blackScore;
		int whites, blacks;
		whites = blacks = whiteScore = blackScore = 0;

		/*
		 * Verificare scor pentru prima parte a tablei (o fata)
		 */
		int i = 3, j;
		
		for (j = 3; j < 10; j = j + 3) {
			if (board[i][j].getFirst() == MARK.BLACK)
				blacks++;
			if (board[i][j].getFirst() == MARK.WHITE)
				whites++;
		}
		IntegerPair score = updateScore(blacks, whites, blackScore, whiteScore);
		blackScore = score.getFirst();
		whiteScore = score.getSecond();
		
		i = 4;
		for (j = 4; j < 9; j = j + 2) {
			if (board[i][j].getFirst() == MARK.BLACK)
				blacks++;
			if (board[i][j].getFirst() == MARK.WHITE)
				whites++;
		}
		score = updateScore(blacks, whites, blackScore, whiteScore);
		blackScore = score.getFirst();
		whiteScore = score.getSecond();
		
		i = 5;
		for (j = 5; j < 8; j++) {
			if (board[i][j].getFirst() == MARK.BLACK)
				blacks++;
			if (board[i][j].getFirst() == MARK.WHITE)
				whites++;
		}
		score = updateScore(blacks, whites, blackScore, whiteScore);
		blackScore = score.getFirst();
		whiteScore = score.getSecond();
		
		
		i = 7;
		for (j = 5; j < 8; j++) {
			if (board[i][j].getFirst() == MARK.BLACK)
				blacks++;
			if (board[i][j].getFirst() == MARK.WHITE)
				whites++;
		}
		score = updateScore(blacks, whites, blackScore, whiteScore);
		blackScore = score.getFirst();
		whiteScore = score.getSecond();
		
		i = 8;
		for (j = 4; j < 9; j = j + 2) {
			if (board[i][j].getFirst() == MARK.BLACK)
				blacks++;
			if (board[i][j].getFirst() == MARK.WHITE)
				whites++;
		}
		score = updateScore(blacks, whites, blackScore, whiteScore);
		blackScore = score.getFirst();
		whiteScore = score.getSecond();
		
		i = 9;
		for (j = 3; j < 10; j = j + 3) {
			if (board[i][j].getFirst() == MARK.BLACK)
				blacks++;
			if (board[i][j].getFirst() == MARK.WHITE)
				whites++;
		}
		score = updateScore(blacks, whites, blackScore, whiteScore);
		blackScore = score.getFirst();
		whiteScore = score.getSecond();
		
		i = 6;
		for (j = 3; j < 6; j++) {
			if (board[i][j].getFirst() == MARK.BLACK)
				blacks++;
			if (board[i][j].getFirst() == MARK.WHITE)
				whites++;
		}
		score = updateScore(blacks, whites, blackScore, whiteScore);
		blackScore = score.getFirst();
		whiteScore = score.getSecond();
		
		for (j = 7; j < 10; j++) {
			if (board[i][j].getFirst() == MARK.BLACK)
				blacks++;
			if (board[i][j].getFirst() == MARK.WHITE)
				whites++;
		}
		score = updateScore(blacks, whites, blackScore, whiteScore);
		blackScore = score.getFirst();
		whiteScore = score.getSecond();
		
		
		
		/*
		 * Verificarea tablei pentru cealalta fata
		 */
		
		i = 3;
		for (j = 3; j < 10; j = j + 3) {
			if (board[j][i].getFirst() == MARK.BLACK)
				blacks++;
			if (board[j][i].getFirst() == MARK.WHITE)
				whites++;
		}
		score = updateScore(blacks, whites, blackScore, whiteScore);
		blackScore = score.getFirst();
		whiteScore = score.getSecond();
		
		i = 4;
		for (j = 4; j < 9; j = j + 2) {
			if (board[j][i].getFirst() == MARK.BLACK)
				blacks++;
			if (board[j][i].getFirst() == MARK.WHITE)
				whites++;
		}
		score = updateScore(blacks, whites, blackScore, whiteScore);
		blackScore = score.getFirst();
		whiteScore = score.getSecond();
		
		i = 5;
		for (j = 5; j < 8; j++) {
			if (board[j][i].getFirst() == MARK.BLACK)
				blacks++;
			if (board[j][i].getFirst() == MARK.WHITE)
				whites++;
		}
		score = updateScore(blacks, whites, blackScore, whiteScore);
		blackScore = score.getFirst();
		whiteScore = score.getSecond();
		
		
		i = 7;
		for (j = 5; j < 8; j++) {
			if (board[j][i].getFirst() == MARK.BLACK)
				blacks++;
			if (board[j][i].getFirst() == MARK.WHITE)
				whites++;
		}
		score = updateScore(blacks, whites, blackScore, whiteScore);
		blackScore = score.getFirst();
		whiteScore = score.getSecond();
		
		i = 8;
		for (j = 4; j < 9; j = j + 2) {
			if (board[j][i].getFirst() == MARK.BLACK)
				blacks++;
			if (board[j][i].getFirst() == MARK.WHITE)
				whites++;
		}
		score = updateScore(blacks, whites, blackScore, whiteScore);
		blackScore = score.getFirst();
		whiteScore = score.getSecond();
		
		i = 9;
		for (j = 3; j < 10; j = j + 3) {
			if (board[j][i].getFirst() == MARK.BLACK)
				blacks++;
			if (board[j][i].getFirst() == MARK.WHITE)
				whites++;
		}
		score = updateScore(blacks, whites, blackScore, whiteScore);
		blackScore = score.getFirst();
		whiteScore = score.getSecond();
		
		i = 6;
		for (j = 3; j < 6; j++) {
			if (board[j][i].getFirst() == MARK.BLACK)
				blacks++;
			if (board[j][i].getFirst() == MARK.WHITE)
				whites++;
		}
		score = updateScore(blacks, whites, blackScore, whiteScore);
		blackScore = score.getFirst();
		whiteScore = score.getSecond();
		
		for (j = 7; j < 10; j++) {
			if (board[j][i].getFirst() == MARK.BLACK)
				blacks++;
			if (board[j][i].getFirst() == MARK.WHITE)
				whites++;
		}
		score = updateScore(blacks, whites, blackScore, whiteScore);
		blackScore = score.getFirst();
		whiteScore = score.getSecond();



		if (play_as == PLAYER.WHITE) { // din perspectiva jucatorului WHITE
			return whiteScore;
		}
		
		else { // din perspectiva jucatorului BLACK
			return blackScore;
		}
		
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
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

class MarkPair extends Pair<MARK, IntegerPair> {
}
