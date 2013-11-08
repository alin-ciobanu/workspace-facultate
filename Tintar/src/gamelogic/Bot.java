package gamelogic;

import structs.Pair;
import enums.DIFFICULTY;
import enums.MARK;
import enums.PLAYER;

/**
 * 
 * @author yozmo
 *
 *	Clasa se ocupa de implementarea diferitelor nivele de dificultate.
 *	Metodele clasei vor apela minimax (pusa intr-o alta clasa)
 *
 */

public class Bot {

	private int depth;
	private PieceRemover remover;
	private final PLAYER player;

	private static final int EASY_DEPTH = 1;
	private static final int NORMAL_DEPTH = 4;
	private static final int HARD_DEPTH = 6;

	/**
	 * Seteaza player-ul cu care joaca bot-ul
	 * (cu albele sau cu negrele)
	 * @param player
	 */
	public Bot (PLAYER player) {
		this.player = player;
	}

	/**
	 * Seteaza Bot-ul
	 * @param player
	 * @param difficulty
	 */
	public Bot (PLAYER player, DIFFICULTY difficulty) {

		this.setDifficulty(difficulty);
		this.player = player;

	}

	public void setDifficulty (DIFFICULTY difficulty) {

		if (difficulty == DIFFICULTY.EASY) {
			depth = EASY_DEPTH;
			remover = new RandomPieceRemover(new Board());
		}
		else if (difficulty == DIFFICULTY.NORMAL) {
			depth = NORMAL_DEPTH;
			remover = new CheckAllPieceRemover(new Board());
		}
		else if (difficulty == DIFFICULTY.HARD) {
			depth = HARD_DEPTH;
			remover = new CheckAllPieceRemover(new Board());
		}
		else {
			System.out.println("Difficulty does not exist.");
		}

	}

	/**
	 * Face o mutare.
	 * 
	 * @param b - board
	 * @param blacksLeft - numarul de piese negre care mai trebuie puse (din cele 9)
	 * @param whitesLeft - numarul de piese albe care mai trebuie puse
	 * 
	 * @return mutarea facuta
	 */
	public Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> move (Board b,
			int blacksLeft, int whitesLeft) {

		Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> move;

		if (depth > (blacksLeft + whitesLeft) && (blacksLeft + whitesLeft) != 0) {
			move = Minimax.getMove((blacksLeft + whitesLeft), b, player, blacksLeft, whitesLeft, remover);
		}
		else {
			move = Minimax.getMove(depth, b, player, blacksLeft, whitesLeft, remover);
		}

		Pair<Integer, Integer> from, to;
		from = move.getFirst();
		to = move.getSecond();

		if (player == PLAYER.BLACK) {
			if (blacksLeft > 0) {
				b.putPiece(to, MARK.BLACK);
			}
			else {
				b.movePiece(from, to);
			}
		}
		else {
			// WHITE
			if (whitesLeft > 0) {
				b.putPiece(to, MARK.WHITE);
			}
			else {
				b.movePiece(from, to);
			}
		}

		return move;

	}

	public PLAYER getPlayer () {

		return player;

	}

	public PieceRemover getPieceRemover (Board b) {

		remover.updateBoard(b);
		return remover;

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
