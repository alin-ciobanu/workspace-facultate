package gamelogic;

public class Board {

	private MARK[][] board;
	
	public static final int MAT_DIM = 7; // dimensiunea matricei
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
	 */
	
	/**
	 * initilizeaza board-ul
	 */
	public Board () {
		
		board = new MARK[MAT_DIM][MAT_DIM];
		
		for (int i = 0; i < MAT_DIM; i++) {
			for (int j = 0; j < MAT_DIM; j++) {
				board[i][j] = MARK.EMPTY;
			}
		}
		
	}
	
	/**
	 * Creeaza un board nou identic cu board-ul @param b (dar cu memorie proprie/alocata)
	 * 
	 */
	public Board (Board b) {
		
		board = new MARK[MAT_DIM][MAT_DIM];
		
		for (int i = 0; i < MAT_DIM; i++) {
			for (int j = 0; j < MAT_DIM; j++) {
				board[i][j] = b.board[i][j];
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
		
		board[line][col] = mark;
		
	}
	
	/**
	 * Sterge piesa de la pozitia indicata de pe tabla
	 * @param line - lina
	 * @param col - coloana
	 */
	public void removePiece (int line, int col) {
		
		board[line][col] = MARK.EMPTY;
		
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
				if (board[i][j] == MARK.BLACK)
					blacks++;
				if (board[i][j] == MARK.WHITE)
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
