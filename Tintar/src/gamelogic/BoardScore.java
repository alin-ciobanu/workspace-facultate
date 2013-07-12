package gamelogic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import structs.Pair;

public class BoardScore {

	Board b;
	Pair<MARK, IntegerPair>[][] board;
	private final int MAT_DIM = Board.getMatrixDimension(); 
	
	// clasa aceasta comunica mult cu clasa Board
	
	
	public BoardScore (Board board) {
		
		this.b = board;
		this.board = b.getBoardMatrix();
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
	 * Metoda care verifica daca jucatorul a forma o morisca pe tabla de joc.
	 * 
	 * @param play_as - jucatorul pentru care se verifica
	 * @return true daca jucatorul a format o morisca
	 */
	private boolean checkMorisca (PLAYER play_as) {
		
		HashSet<Pair<Integer, Integer>> points = b.getAllPointsOfBoard();
		Iterator<Pair<Integer, Integer>> it = points.iterator();
		MARK mark;
		if (play_as == PLAYER.WHITE) {
			mark = MARK.WHITE;
		}
		else {
			mark = MARK.BLACK;
		}
		
		while (it.hasNext()) {
			
			Pair<Integer, Integer> currentPoint = it.next();
			int i, j;
			i = currentPoint.getFirst();
			j = currentPoint.getSecond();
			
			if (board[i][j].getFirst() == mark) {
				if (checkMoriscaForOnePoint(currentPoint, mark)) {
					return true;
				}
			}
			
		}
		
		return false;
		
	}

	/**
	 * 
	 * Metoda verifica daca se poate forma morisca dintr-un punct.
	 * 
	 * @param currentPoint - punctul pentru care se verifica
	 * @return true daca se poate forma morisca din punctul respectiv
	 */
	private boolean checkMoriscaForOnePoint (Pair<Integer, Integer> currentPoint, MARK mark) {
		
		if (!checkLineCompleted(currentPoint, mark)) {
			// punctul nu formeaza o linie
			return false;
		}
		
		// daca punctul formeaza o linie, 
		// incercam sa vedem daca exista vreo miscare care sa formeze o alta linie
		PLAYER player;
		if (mark == MARK.WHITE) {
			player = PLAYER.WHITE;
		}
		else {
			player = PLAYER.BLACK;
		}
		
		ArrayList<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> mvs = 
				b.getPossibleMoves(currentPoint, player);
		
		for (Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> move : mvs) {
			
			Pair<Integer, Integer> nextPoint = move.getSecond();

			// do move
			b.movePiece(currentPoint, nextPoint);

			
			if (checkLineCompleted(nextPoint, mark)) {
				return true;
			}

			//undo move
			b.movePiece(nextPoint, currentPoint);
			
		}
		
		return false;
		
	}
	
	/**
	 * Metoda care verifica daca o pozitie este in componenta unei linii complete.
	 * 
	 * @param pos - pozitia pentru care se verifica
	 * @param mark - mark-ul pentru care se verifica
	 * @return true daca pozitia pos se afla intr-o linie completa
	 */
	private boolean checkLineCompleted (Pair<Integer, Integer> pos, MARK mark) {
		
		int i, j;
		i = pos.getFirst();
		j = pos.getSecond();
		
		int nr = 0;

		if (i == 6) {
			
			if (j < 6) {
				for (int k = 3; k < 6; k++) {
					if (board[i][k].getFirst() == mark) {
						nr++;
					}
				}
			}
			
			else {
				for (int k = 7; k < 10; k++) {
					if (board[i][k].getFirst() == mark) {
						nr++;
					}
				}
			}
			
		}
		
		else {
			
			for (int k = 3; k < 10; k++) {
				if (board[i][k].getFirst() == mark) {
					nr++;
				}
			}
			
		}
		
		if (nr == 3)
			return true;
		else
			nr = 0;
		
		if (j == 6) {
			
			if (i < 6) {
				for (int k = 3; k < 6; k++) {
					if (board[k][j].getFirst() == mark) {
						nr++;
					}
				}
			}
			else {
				for (int k = 7; k < 10; k++) {
					if (board[k][j].getFirst() == mark) {
						nr++;
					}
				}
			}
			
		}
		
		else {
			
			for (int k = 3; k < 10; k++) {
				if (board[k][j].getFirst() == mark) {
					nr++;
				}
			}
			
		}
		
		if (nr == 3)
			return true;
		else
			return false;
		
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
		whites = blacks = 0;
		
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
		whites = blacks = 0;
		
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
		whites = blacks = 0;
		
		
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
		whites = blacks = 0;
		
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
		whites = blacks = 0;
		
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
		whites = blacks = 0;
		
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
		whites = blacks = 0;
		
		for (j = 7; j < 10; j++) {
			if (board[i][j].getFirst() == MARK.BLACK)
				blacks++;
			if (board[i][j].getFirst() == MARK.WHITE)
				whites++;
		}
		score = updateScore(blacks, whites, blackScore, whiteScore);
		blackScore = score.getFirst();
		whiteScore = score.getSecond();
		whites = blacks = 0;
		
		
		
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
		whites = blacks = 0;
		
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
		whites = blacks = 0;
		
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
		whites = blacks = 0;

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
		whites = blacks = 0;
		
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
		whites = blacks = 0;
		
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
		whites = blacks = 0;

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
		whites = blacks = 0;
		
		for (j = 7; j < 10; j++) {
			if (board[j][i].getFirst() == MARK.BLACK)
				blacks++;
			if (board[j][i].getFirst() == MARK.WHITE)
				whites++;
		}
		score = updateScore(blacks, whites, blackScore, whiteScore);
		blackScore = score.getFirst();
		whiteScore = score.getSecond();
		whites = blacks = 0;

		if (play_as == PLAYER.WHITE) { // din perspectiva jucatorului WHITE
			if (checkMorisca(PLAYER.WHITE)) {
				whiteScore += 40;
			}
			if (checkMorisca(PLAYER.BLACK)) {
				whiteScore -= 50;
			}
			return whiteScore;
		}
		
		else { // din perspectiva jucatorului BLACK
			if (checkMorisca(PLAYER.BLACK)) {
				blackScore += 40;
			}
			if (checkMorisca(PLAYER.WHITE)) {
				blackScore -= 50;
			}
			return blackScore;
		}
		
	}

	/**
	 * 
	 * @return castigatorul tablei
	 * metoda asta e oleaca mai lunga decat ar trebui
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
		
		Board b = new Board();
		b.putPiece(new Pair<Integer, Integer>(3, 3), MARK.BLACK);
		b.putPiece(new Pair<Integer, Integer>(3, 6), MARK.BLACK);
		b.putPiece(new Pair<Integer, Integer>(3, 9), MARK.BLACK);
		b.putPiece(new Pair<Integer, Integer>(4, 4), MARK.BLACK);
		b.putPiece(new Pair<Integer, Integer>(4, 8), MARK.BLACK);
		b.putPiece(new Pair<Integer, Integer>(6, 7), MARK.WHITE);

		System.out.println(b);
		
		BoardScore sc = new BoardScore(b);
		System.out.println(sc.checkMorisca(PLAYER.BLACK));
		System.out.println(sc.getScore(PLAYER.BLACK));

	}

}
