package gamelogic;

import java.util.ArrayList;
import java.util.Iterator;

import structs.Pair;

public class Minimax {

	private static int alphaBetaMaxi (int alpha, int beta, int depth, Board b, PLAYER play_as, 
			Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> nextMove, int blacksLeft, int whitesLeft) {
		
		if (depth == 0) {
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
					p1.setBoth(new Pair<Integer, Integer>(-1, -1), new Pair<Integer, Integer>(-1, -1));

					int score = 0;
					
					b1.putPiece(current, MARK.BLACK);
					score = alphaBetaMini(alpha, beta, depth - 1, b1, PLAYER.WHITE, p1, blacksLeft - 1, whitesLeft);
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
				
				
				
			}

		}
		
		else {
			// daca player-ul e WHITE

			if (whitesLeft > 0) {

				

			}


		}
		
		
		// DELETE this
		return 0;
		
	}
	
	private static int alphaBetaMini (int alpha, int beta, int depth, Board b, PLAYER play_as, 
			Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> nextMove, int blacksLeft, int whitesLeft) {
		
		return 0;
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
