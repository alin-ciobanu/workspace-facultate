package gamelogic;

/**
 * 
 * @author yozmo
 *
 *	Clasa se ocupa de implementarea diferitelor nivele de dificultate.
 *	Metodele clasei vor apela minimax (pusa intr-o alta clasa)
 *
 */

public class BotMove {

	private int depth;

	private static final int EASY_DEPTH = 10;
	private static final int NORMAL_DEPTH = 10;
	private static final int HARD_DEPTH = 10;
	// TODO restul de depth-uri

	public void setDifficulty (DIFFICULTY difficulty) {

		if (difficulty == DIFFICULTY.EASY) {
			depth = EASY_DEPTH;
		}
		else if (difficulty == DIFFICULTY.NORMAL) {
			depth = NORMAL_DEPTH;
		}
		else if (difficulty == DIFFICULTY.HARD) {
			depth = HARD_DEPTH;
		}
		else {
			System.out.println("Difficulty does not exist.");
		}

	}

	public void move () {

// TODO		minimax (DEPTH);

	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
