import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Bot {
	
	public static final int LINES = 13;
	public static final int COLUMNS = 9;
	public static final int GOAL_SIZE = 2;
	
	public static final int MAX = 987654;
	public static final int MIN = -987654;
	
	public static final int DEPTH = 8;

	int[][] scores;
	
	public void readScores (String fileName, int lines, int cols) throws Exception {

		scores = new int[lines][cols];

		RandomAccessFile in = new RandomAccessFile(fileName, "r");
		int i = 0, j = 0;
		String line = "";
		
		while ((line = in.readLine()) != null) {
			j = 0;
			StringTokenizer st = new StringTokenizer(line, " ");
			while (st.hasMoreTokens()) {
				String tok = st.nextToken();
				int nr = Integer.parseInt(tok);
				scores[i][j] = nr;
				j++;
			}
			i++;
		}
		
		in.close();
	}

	/**
	 * 
	 * @param in - va fi stdin de fiecare data
	 * @return array cu mutarile facute de adversar
	 *  -- daca array.size == 0, s-a primit mesaj de tip S
	 *  -- daca array.size == 9, s-a primit mesaj de tip F
	 *  -- else normal
	 */
	public Pair <MESSAGE_TYPE, ArrayList<Integer>> read (Scanner in) {

		ArrayList<Integer> moves = new ArrayList<Integer>();

		String msg = in.nextLine();
		StringTokenizer st = new StringTokenizer(msg, " ");
		char messageType = st.nextToken().charAt(0);
		if (messageType == 'S') {
			return new Pair<MESSAGE_TYPE, ArrayList<Integer>>(MESSAGE_TYPE.S, null);
		}
		else if (messageType == 'F') {
			return new Pair<MESSAGE_TYPE, ArrayList<Integer>>(MESSAGE_TYPE.F, null);
		}

		st.nextToken(); // numarul de mutari facute

		while (st.hasMoreTokens()) {
			moves.add(Integer.parseInt(st.nextToken()));
		}
		
		return new Pair<MESSAGE_TYPE, ArrayList<Integer>>(MESSAGE_TYPE.M, moves);
	}

	/**
	 *
	 * @param moves -- vectorul cu mutarile facute
	 *  care trebuie transmise server-ului
	 */
	public void write (ArrayList<Integer> moves) {
		// se scrie la stdout
		
		String msg = "";
		msg += "M ";
		msg += moves.size() + " ";
		
		for (int i = 0; i < moves.size(); i++) {
			if (i != moves.size() - 1) {
				msg += moves.get(i) + " ";
			}
			else {
				msg += moves.get(i);
			}
		}

		System.out.println(msg);
		
	}
	
	/**
	 * 
	 * @param b -- board-ul curent
	 * @param play_as -- specifica locul unde trebuie sa atace
	 * @return o mutare -- {0, 1, 2, 3, 4, 5, 6, 7}
	 *  unde fiecare mutare de la 0 la 7 reprezinta un punct cardinal (vezi clasa Board) 
	 */
	public int playNextMove (Board b, PLAYER play_as) {

		SingleIntValue res = new SingleIntValue(-1);

		alphaBetaMaxi(MIN - 10, MAX + 10, b, play_as, DEPTH, res);
//		maxi (b, play_as, DEPTH, res);
		
		return res.val;

	}
	
	public int maxi (Board b, PLAYER play_as, int depth, SingleIntValue nextMove) {
		
		HashSet<Integer> availableMoves = b.getPossibleMoves();
		Iterator<Integer> it = availableMoves.iterator();
		
		if (depth == 0 || availableMoves.size() == 0 || b.getWinner() != WINNER.NOBODY) {
			return evaluate (b, play_as);
		}
		
		int max = MIN;
		
		while (it.hasNext()) {
			
			Board b1 = new Board(b);
			SingleIntValue nextMove1 = new SingleIntValue(-1);
			Integer currentElementInIterator = it.next();
			int score = 0;

			if (play_as == PLAYER.ATTACKING_TO_NORTH) {
				b1.makeMove(currentElementInIterator);
				if (b1.stillMyTurn(currentElementInIterator)) {
					score = maxi (b1, play_as, depth - 1, nextMove1);
				}
				else {
					score = mini (b1, PLAYER.ATTACKING_TO_SOUTH, depth - 1, nextMove1);
				}
			}
			else {
				b1.makeMove(currentElementInIterator);
				if (b1.stillMyTurn(currentElementInIterator)) {
					score = maxi (b1, play_as, depth - 1, nextMove1);
				}
				else {
					score = mini ( b1, PLAYER.ATTACKING_TO_NORTH, depth - 1, nextMove1);
				}
			}

			b1.undoMove(Board.complementDirection(currentElementInIterator));
			
			if (score >= max) {
				max = score;
				nextMove.val = currentElementInIterator;
			}
			
		}
		
		return max;

	}

	public int mini (Board b, PLAYER play_as, int depth, SingleIntValue nextMove) {
		
		HashSet<Integer> availableMoves = b.getPossibleMoves();
		Iterator<Integer> it = availableMoves.iterator();
		
		if (depth == 0 || availableMoves.size() == 0 || b.getWinner() != WINNER.NOBODY) {
			return -evaluate (b, play_as);
		}
		
		int min = MAX;
		
		while (it.hasNext()) {
			
			Board b1 = new Board(b);
			SingleIntValue nextMove1 = new SingleIntValue(-1);
			Integer currentElementInIterator = it.next();
			int score = 0;

			if (play_as == PLAYER.ATTACKING_TO_NORTH) {
				b1.makeMove(currentElementInIterator);
				if (b1.stillMyTurn(currentElementInIterator)) {
					score = mini (b1, play_as, depth - 1, nextMove1);
				}
				else {
					score = maxi (b1, PLAYER.ATTACKING_TO_SOUTH, depth - 1, nextMove1);
				}
			}
			else {
				b1.makeMove(currentElementInIterator);
				if (b1.stillMyTurn(currentElementInIterator)) {
					score = mini (b1, play_as, depth - 1, nextMove1);
				}
				else {
					score = maxi ( b1, PLAYER.ATTACKING_TO_NORTH, depth - 1, nextMove1);
				}
			}

			b1.undoMove(Board.complementDirection(currentElementInIterator));
			
			if (score <= min) {
				min = score;
				nextMove.val = currentElementInIterator;
			}
			
		}
		
		return min;

	}
	
	public int alphaBetaMaxi  (int alpha, int beta, Board b, PLAYER play_as, int depth, SingleIntValue nextMove) {
		
		HashSet<Integer> availableMoves = b.getPossibleMoves();
		Iterator<Integer> it = availableMoves.iterator();

		if (depth == 0 || availableMoves.size() == 0 || b.getWinner() != WINNER.NOBODY) {
			return evaluate (b, play_as);
		}

		while (it.hasNext()) {

			Board b1 = new Board(b);
			SingleIntValue nextMove1 = new SingleIntValue(-1);
			Integer currentElementInIterator = it.next();
			int score = 0;

			if (play_as == PLAYER.ATTACKING_TO_NORTH) {
				b1.makeMove(currentElementInIterator);
				if (b1.stillMyTurn(currentElementInIterator)) {
					score = alphaBetaMaxi(alpha, beta, b1, play_as, depth - 1, nextMove1);
				}
				else {
					score = alphaBetaMini (alpha, beta, b1, PLAYER.ATTACKING_TO_SOUTH, depth - 1, nextMove1);
				}
			}
			else {
				b1.makeMove(currentElementInIterator);
				if (b1.stillMyTurn(currentElementInIterator)) {
					score = alphaBetaMaxi(alpha, beta, b1, play_as, depth - 1, nextMove1);
				}
				else {
					score = alphaBetaMini (alpha, beta, b1, PLAYER.ATTACKING_TO_NORTH, depth - 1, nextMove1);
				}
			}

			b1.undoMove(Board.complementDirection(currentElementInIterator));

			if (alpha < score) {
    			alpha = score;
    			nextMove.val = currentElementInIterator;
    		}
    		
			if (score >= beta) {
    			return beta;
    		}

		}

		return alpha;

	}
	
	public int alphaBetaMini  (int alpha, int beta, Board b, PLAYER play_as, int depth, SingleIntValue nextMove) {
		
		HashSet<Integer> availableMoves = b.getPossibleMoves();
		Iterator<Integer> it = availableMoves.iterator();
		
		if (depth == 0 || availableMoves.size() == 0 || b.getWinner() != WINNER.NOBODY) {
			return -evaluate (b, play_as);
		}

		while (it.hasNext()) {
			
			Board b1 = new Board(b);
			SingleIntValue nextMove1 = new SingleIntValue(-1);
			Integer currentElementInIterator = it.next();
			int score = 0;
			
			if (play_as == PLAYER.ATTACKING_TO_NORTH) {
				b1.makeMove(currentElementInIterator);
				if (b1.stillMyTurn(currentElementInIterator)) {
					score = alphaBetaMini(alpha, beta, b1, play_as, depth - 1, nextMove1);
				}
				else {
					score = alphaBetaMaxi (alpha, beta, b1, PLAYER.ATTACKING_TO_SOUTH, depth - 1, nextMove1);
				}
			}
			else {
				b1.makeMove(currentElementInIterator);
				if (b1.stillMyTurn(currentElementInIterator)) {
					score = alphaBetaMini(alpha, beta, b1, play_as, depth - 1, nextMove1);
				}
				else {
					score = alphaBetaMaxi (alpha, beta, b1, PLAYER.ATTACKING_TO_NORTH, depth - 1, nextMove1);
				}
			}

			b1.undoMove(Board.complementDirection(currentElementInIterator));

			if (beta > score) {
    			beta = score;
    			nextMove.val = currentElementInIterator;
    		}
    		
			if (score <= alpha) {
    			return alpha;
    		}

		}

		return beta;

	}
	
	private int evaluate (Board b, PLAYER play_as) {
		
		int i = b.getCurrentPosition().getFirst();
		int j = b.getCurrentPosition().getSecond();

		if (play_as == PLAYER.ATTACKING_TO_NORTH) {

			if (b.getWinner() == WINNER.PLAYER_ATTACKING_TO_NORTH) {
				return MAX;
			}
			if (b.getWinner() == WINNER.PLAYER_ATTACKING_TO_SOUTH 
					|| b.getWinner() == WINNER.NO_MOVES_LEFT) {
				return MIN;
			}
			
			return scores[i][j];
			
		}
		
		else {
			
			if (b.getWinner() == WINNER.PLAYER_ATTACKING_TO_NORTH || b.getWinner() == WINNER.NO_MOVES_LEFT) {
				return MIN;
			}
			if (b.getWinner() == WINNER.PLAYER_ATTACKING_TO_SOUTH) {
				return MAX;
			}

			return -scores[i][j];
		
		}
		
	}
	
	public static void main(String[] args) throws Exception {
		
		Scanner in = new Scanner(System.in);
		Bot m = new Bot ();
		m.readScores("scores.txt", LINES, COLUMNS);

		Board b = new Board(LINES, COLUMNS, GOAL_SIZE);
		PLAYER play_as = PLAYER.ATTACKING_TO_NORTH;
		
		Pair<MESSAGE_TYPE, ArrayList<Integer>> p = m.read(in);

		while (p.getFirst() != MESSAGE_TYPE.F) {

			ArrayList<Integer> movesMade = new ArrayList<Integer>();

			if (p.getFirst() == MESSAGE_TYPE.S) {
				
				int moveMade = m.playNextMove(b, play_as);
				b.makeMove(moveMade);
				movesMade.add(moveMade);

				while (b.stillMyTurn(moveMade)) {
					moveMade = m.playNextMove(b, play_as);
					b.makeMove(moveMade);
					movesMade.add(moveMade);
				}
			}

			else {
				// daca s-a primit un mesaj de tip M
				for (int i = 0; i < p.getSecond().size(); i++) {
					b.makeMove(p.getSecond().get(i));
				}
				
				int moveMade = m.playNextMove(b, play_as);
				b.makeMove(moveMade);
				movesMade.add(moveMade);

				while (b.stillMyTurn(moveMade)) {
					moveMade = m.playNextMove(b, play_as);
					b.makeMove(moveMade);
					movesMade.add(moveMade);
				}
			}

			m.write(movesMade);			
			p = m.read(in);
			
		}

	}

}


class SingleIntValue {
	int val;
	public SingleIntValue (int x) {
		val = x;
	}
}