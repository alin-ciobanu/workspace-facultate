package gamelogic;

import java.util.ArrayList;
import java.util.Iterator;

import structs.Pair;
import enums.MARK;
import enums.PLAYER;

public class Minimax {

	private static final int INF = Integer.MAX_VALUE;

	public static Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> getMove (int depth,
			Board b, PLAYER play_as, int blacksLeft, int whitesLeft, PieceRemover remover) {

		Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> move;
		move = new Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>();

		alphaBetaMaxi(-INF, INF, depth, b, play_as, move, blacksLeft, whitesLeft, false, remover);

		return move;

	}

	/**
	 * 
	 * @param alpha - limita inferioara din minimax
	 * @param beta - limita superioara din minimax
	 * @param depth - adancimea arborelui starilor de joc
	 * @param b - Board-ul
	 * @param play_as - jucatorul care este la mutare
	 * @param nextMove - mutarea care va fi facuta (data de cea mai buna stare din arbore)
	 * @param blacksLeft - numarul de piese negre care mai trebuie puse pe tabla (din cele 9)
	 * @param whitesLeft - numarul de piese albe care mai trebuie puse pe tabla
	 * @return scorul pentru o tabla (folosit la apelarea in recursivitate)
	 */
	private static int alphaBetaMaxi (int alpha, int beta, int depth, Board b, PLAYER play_as,
			Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> nextMove,
			int blacksLeft, int whitesLeft, boolean lineCompleted, PieceRemover remover) {

		if (depth == 0 || lineCompleted) {
			return b.getScore(play_as);
		}

		if (play_as == PLAYER.BLACK) {

			if (blacksLeft > 0) {
				// daca nu s-au terminat cele 9 piese de pus

				ArrayList<Pair<Integer, Integer>> freeSquares = b.getFreeSquares();
				Iterator<Pair<Integer, Integer>> it = freeSquares.iterator();

				while (it.hasNext()) {

					Pair<Integer, Integer> current = it.next();
					Board b1 = new Board(b);
					Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> p1 =
							new Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>();
					p1.setBoth(new Pair<Integer, Integer>(-1, -1),
							new Pair<Integer, Integer>(-1, -1));

					int score = 0;

					b1.putPiece(current, MARK.BLACK);
					remover.updateBoard(b1);

					BoardScore bscore = new BoardScore(b1);
					if (bscore.checkLineCompleted(current, MARK.BLACK)) {
						Pair<Integer, Integer> removed;
						removed = remover.removePieceAfterCompletingLine(PLAYER.WHITE);
						score = alphaBetaMini(alpha, beta, depth - 1, b1, PLAYER.WHITE, p1,
								blacksLeft - 1, whitesLeft, true, remover);
						b1.putPiece(removed, MARK.WHITE);
					}
					else {
						score = alphaBetaMini(alpha, beta, depth - 1, b1, PLAYER.WHITE, p1,
								blacksLeft - 1, whitesLeft, false, remover);
					}

					b1.clearSquare(current);

					if (alpha < score) {
						alpha = score;
						nextMove.setSecond(current);
					}
					if (score >= beta) {
						return beta;
					}

				}

				return alpha;

			}

			else {
				// s-au terminat piesele de pus
				//urmeaza mutarile

				ArrayList<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> moves =
						b.getPossibleMoves(PLAYER.BLACK);
				Iterator<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> it =
						moves.iterator();

				while (it.hasNext()) {

					Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> current = it.next();
					Board b1 = new Board(b);
					Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> p1 =
							new Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>();
					p1.setBoth(new Pair<Integer, Integer>(-1, -1),
							new Pair<Integer, Integer>(-1, -1));

					int score = 0;

					Pair<Integer, Integer> from = current.getFirst();
					Pair<Integer, Integer> to = current.getSecond();
					b1.movePiece(from, to); // do move
					remover.updateBoard(b1);

					BoardScore bscore = new BoardScore(b1);
					if (bscore.checkLineCompleted(to, MARK.BLACK)) {
						Pair<Integer, Integer> removed;
						removed = remover.removePieceAfterCompletingLine(PLAYER.WHITE);
						score = alphaBetaMini(alpha, beta, depth - 1, b1, PLAYER.WHITE, p1,
								blacksLeft - 1, whitesLeft, true, remover);
						b1.putPiece(removed, MARK.WHITE);
					}
					else {
						score = alphaBetaMini(alpha, beta, depth - 1, b1, PLAYER.WHITE, p1,
								blacksLeft - 1, whitesLeft, false, remover);
					}

					b1.movePiece(to, from); // undo move

					if (alpha < score) {
						alpha = score;
						nextMove.setBoth(from, to);
					}
					if (score >= beta) {
						return beta;
					}

				}

				return alpha;

			}

		}

		else {
			// daca player-ul e WHITE

			if (whitesLeft > 0) {
				// daca nu s-au terminat cele 9 piese de pus

				ArrayList<Pair<Integer, Integer>> freeSquares = b.getFreeSquares();
				Iterator<Pair<Integer, Integer>> it = freeSquares.iterator();

				while (it.hasNext()) {

					Pair<Integer, Integer> current = it.next();
					Board b1 = new Board(b);
					Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> p1 =
							new Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>();
					p1.setBoth(new Pair<Integer, Integer>(-1, -1),
							new Pair<Integer, Integer>(-1, -1));

					int score = 0;

					b1.putPiece(current, MARK.WHITE);
					remover.updateBoard(b1);

					BoardScore bscore = new BoardScore(b1);
					if (bscore.checkLineCompleted(current, MARK.WHITE)) {
						Pair<Integer, Integer> removed;
						removed = remover.removePieceAfterCompletingLine(PLAYER.BLACK);
						score = alphaBetaMini(alpha, beta, depth - 1, b1, PLAYER.BLACK, p1,
								blacksLeft - 1, whitesLeft, true, remover);
						b1.putPiece(removed, MARK.BLACK);
					}
					else {
						score = alphaBetaMini(alpha, beta, depth - 1, b1, PLAYER.BLACK, p1,
								blacksLeft - 1, whitesLeft, false, remover);
					}

					b1.clearSquare(current);

					if (alpha < score) {
						alpha = score;
						nextMove.setSecond(current);
					}
					if (score >= beta) {
						return beta;
					}

				}

				return alpha;

			}

			else {
				// daca s-au terminat cele 9 piese
				// si urmeaza mutarile

				ArrayList<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> moves =
						b.getPossibleMoves(PLAYER.WHITE);
				Iterator<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> it =
						moves.iterator();

				while (it.hasNext()) {

					Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> current = it.next();
					Board b1 = new Board(b);
					Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> p1 =
							new Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>();
					p1.setBoth(new Pair<Integer, Integer>(-1, -1),
							new Pair<Integer, Integer>(-1, -1));

					int score = 0;

					Pair<Integer, Integer> from = current.getFirst();
					Pair<Integer, Integer> to = current.getSecond();
					b1.movePiece(from, to); // do move
					remover.updateBoard(b1);

					BoardScore bscore = new BoardScore(b1);
					if (bscore.checkLineCompleted(to, MARK.WHITE)) {
						Pair<Integer, Integer> removed;
						removed = remover.removePieceAfterCompletingLine(PLAYER.BLACK);
						score = alphaBetaMini(alpha, beta, depth - 1, b1, PLAYER.BLACK, p1,
								blacksLeft - 1, whitesLeft, true, remover);
						b1.putPiece(removed, MARK.BLACK);
					}
					else {
						score = alphaBetaMini(alpha, beta, depth - 1, b1, PLAYER.BLACK, p1,
								blacksLeft - 1, whitesLeft, false, remover);
					}

					b1.movePiece(to, from); // undo move

					if (alpha < score) {
						alpha = score;
						nextMove.setBoth(from, to);
					}
					if (score >= beta) {
						return beta;
					}

				}

				return alpha;

			}

		}

	}

	private static int alphaBetaMini (int alpha, int beta, int depth, Board b, PLAYER play_as,
			Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> nextMove,
			int blacksLeft, int whitesLeft, boolean lineCompleted, PieceRemover remover) {

		if (depth == 0 || lineCompleted) {
			return -b.getScore(play_as);
		}

		if (play_as == PLAYER.BLACK) {

			if (blacksLeft > 0) {
				// daca nu s-au terminat cele 9 piese de pus

				ArrayList<Pair<Integer, Integer>> freeSquares = b.getFreeSquares();
				Iterator<Pair<Integer, Integer>> it = freeSquares.iterator();

				while (it.hasNext()) {

					Pair<Integer, Integer> current = it.next();
					Board b1 = new Board(b);
					Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> p1 =
							new Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>();
					p1.setBoth(new Pair<Integer, Integer>(-1, -1),
							new Pair<Integer, Integer>(-1, -1));

					int score = 0;

					b1.putPiece(current, MARK.BLACK);
					remover.updateBoard(b1);

					BoardScore bscore = new BoardScore(b1);
					if (bscore.checkLineCompleted(current, MARK.BLACK)) {
						Pair<Integer, Integer> removed;
						removed = remover.removePieceAfterCompletingLine(PLAYER.WHITE);
						score = alphaBetaMaxi(alpha, beta, depth - 1, b1, PLAYER.WHITE, p1,
								blacksLeft - 1, whitesLeft, true, remover);
						b1.putPiece(removed, MARK.WHITE);
					}
					else {
						score = alphaBetaMaxi(alpha, beta, depth - 1, b1, PLAYER.WHITE, p1,
								blacksLeft - 1, whitesLeft, false, remover);
					}

					b1.clearSquare(current);

					if (beta > score) {
						beta = score;
						nextMove.setSecond(current);
					}
					if (score <= alpha) {
						return alpha;
					}

				}

				return beta;

			}

			else {
				// s-au terminat piesele de pus
				//urmeaza mutarile

				ArrayList<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> moves =
						b.getPossibleMoves(PLAYER.BLACK);
				Iterator<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> it =
						moves.iterator();

				while (it.hasNext()) {

					Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> current = it.next();
					Board b1 = new Board(b);
					Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> p1 =
							new Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>();
					p1.setBoth(new Pair<Integer, Integer>(-1, -1),
							new Pair<Integer, Integer>(-1, -1));

					int score = 0;

					Pair<Integer, Integer> from = current.getFirst();
					Pair<Integer, Integer> to = current.getSecond();
					b1.movePiece(from, to); // do move
					remover.updateBoard(b1);

					BoardScore bscore = new BoardScore(b1);
					if (bscore.checkLineCompleted(to, MARK.BLACK)) {
						Pair<Integer, Integer> removed;
						removed = remover.removePieceAfterCompletingLine(PLAYER.WHITE);
						score = alphaBetaMaxi(alpha, beta, depth - 1, b1, PLAYER.WHITE, p1,
								blacksLeft - 1, whitesLeft, true, remover);
						b1.putPiece(removed, MARK.WHITE);
					}
					else {
						score = alphaBetaMaxi(alpha, beta, depth - 1, b1, PLAYER.WHITE, p1,
								blacksLeft - 1, whitesLeft, false, remover);
					}

					b1.movePiece(to, from); // undo move

					if (beta > score) {
						beta = score;
						nextMove.setBoth(from, to);
					}
					if (score <= alpha) {
						return alpha;
					}

				}

				return beta;

			}

		}

		else {
			// daca player-ul e WHITE

			if (whitesLeft > 0) {
				// daca nu s-au terminat cele 9 piese de pus

				ArrayList<Pair<Integer, Integer>> freeSquares = b.getFreeSquares();
				Iterator<Pair<Integer, Integer>> it = freeSquares.iterator();

				while (it.hasNext()) {

					Pair<Integer, Integer> current = it.next();
					Board b1 = new Board(b);
					Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> p1 =
							new Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>();
					p1.setBoth(new Pair<Integer, Integer>(-1, -1),
							new Pair<Integer, Integer>(-1, -1));

					int score = 0;

					b1.putPiece(current, MARK.WHITE);
					remover.updateBoard(b1);

					BoardScore bscore = new BoardScore(b1);
					if (bscore.checkLineCompleted(current, MARK.WHITE)) {
						Pair<Integer, Integer> removed;
						removed = remover.removePieceAfterCompletingLine(PLAYER.BLACK);
						score = alphaBetaMaxi(alpha, beta, depth - 1, b1, PLAYER.BLACK, p1,
								blacksLeft - 1, whitesLeft, true, remover);
						b1.putPiece(removed, MARK.BLACK);
					}
					else {
						score = alphaBetaMaxi(alpha, beta, depth - 1, b1, PLAYER.BLACK, p1,
								blacksLeft - 1, whitesLeft, false, remover);
					}

					b1.clearSquare(current);

					if (beta > score) {
						beta = score;
						nextMove.setSecond(current);
					}
					if (score <= alpha) {
						return alpha;
					}

				}

				return beta;

			}

			else {
				// daca s-au terminat cele 9 piese
				// si urmeaza mutarile

				ArrayList<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> moves =
						b.getPossibleMoves(PLAYER.WHITE);
				Iterator<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> it =
						moves.iterator();

				while (it.hasNext()) {

					Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> current = it.next();
					Board b1 = new Board(b);
					Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> p1 =
							new Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>();
					p1.setBoth(new Pair<Integer, Integer>(-1, -1),
							new Pair<Integer, Integer>(-1, -1));

					int score = 0;

					Pair<Integer, Integer> from = current.getFirst();
					Pair<Integer, Integer> to = current.getSecond();
					b1.movePiece(from, to); // do move
					remover.updateBoard(b1);

					BoardScore bscore = new BoardScore(b1);
					if (bscore.checkLineCompleted(to, MARK.WHITE)) {
						Pair<Integer, Integer> removed;
						removed = remover.removePieceAfterCompletingLine(PLAYER.BLACK);
						score = alphaBetaMaxi (alpha, beta, depth - 1, b1, PLAYER.BLACK, p1,
								blacksLeft - 1, whitesLeft, true, remover);
						b1.putPiece(removed, MARK.BLACK);
					}
					else {
						score = alphaBetaMaxi (alpha, beta, depth - 1, b1, PLAYER.BLACK, p1,
								blacksLeft - 1, whitesLeft, false, remover);
					}

					b1.movePiece(to, from); // undo move

					if (beta > score) {
						beta = score;
						nextMove.setBoth(from, to);
					}
					if (score <= alpha) {
						return alpha;
					}

				}

				return beta;

			}

		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Board b = new Board();
		b.putPiece(new Pair<Integer, Integer>(3, 3), MARK.BLACK);
		b.putPiece(new Pair<Integer, Integer>(3, 9), MARK.BLACK);
		b.putPiece(new Pair<Integer, Integer>(4, 4), MARK.BLACK);
		b.putPiece(new Pair<Integer, Integer>(4, 8), MARK.BLACK);

		b.putPiece(new Pair<Integer, Integer>(6, 7), MARK.WHITE);
		b.putPiece(new Pair<Integer, Integer>(6, 8), MARK.WHITE);
		b.putPiece(new Pair<Integer, Integer>(6, 4), MARK.WHITE);
		b.putPiece(new Pair<Integer, Integer>(6, 5), MARK.WHITE);

		Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> move
		= Minimax.getMove(3, b, PLAYER.BLACK, 1, 0, new CheckAllPieceRemover(b));
		System.out.println(move);

		System.out.println(b);

		b.putPiece(move.getSecond(), MARK.BLACK);
		System.out.println(b);

	}

}
