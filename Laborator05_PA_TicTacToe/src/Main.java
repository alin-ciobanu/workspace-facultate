import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import sun.awt.Win32ColorModel24;

public class Main {

    private static final int INF = 999999;
    private static final int MAXDEPTH = 20;
    
    private static int NR_APELURI = 0;


    // static function
    static Pair PlayMove(Board b, PLAYER play_as) {
        Pair move = new Pair(-1, -1);

//        maxi (b, play_as, 20000, move);
        alphaBetaMaxi (-INF, INF, 1000, b, play_as, move);

        System.out.println(NR_APELURI);

        // calculeaza cea mai buna mutare din pozitia data de tabla b
        // Mutarea este data de perechea (rand, coloana) unde va fi plasat X sau
        // O

        // Intoarce noua mutare
        return move;
    }
    
    static int maxi (Board b, PLAYER play_as, int depth, Pair p) {

    	NR_APELURI++;
  
    	ArrayList<Pair> freeSquares = b.GetEmptySquares();

    	if (depth <= 0 || freeSquares.size() == 0 || 
    			b.GetWinner() == WINNER.PLAYER_X || b.GetWinner() == WINNER.PLAYER_ZERO)
    		return evaluate(b, play_as);

	    int max = -INF;

    	for (int i = 0; i < freeSquares.size(); i++) {
    	   	Board b1 = new Board(b);
    	   	Pair p1 = new Pair(-1, -1);
    	    int score;
    	    if (play_as == PLAYER.PLAY_AS_X) {
        	    b1.MarkX(freeSquares.get(i).first, freeSquares.get(i).second);
    	    	score = mini (b1, PLAYER.PLAY_AS_ZERO, depth - 1, p1);
    	    }
    	    else {
    	    	b1.MarkZero(freeSquares.get(i).first, freeSquares.get(i).second);
    	    	score = mini (b1, PLAYER.PLAY_AS_X, depth - 1, p1);
    	    }

	    	b1.ClearSquare(freeSquares.get(i).first, freeSquares.get(i).second);
    	    if (score >= max) {
    	    	max = score;
    			p.first = freeSquares.get(i).first;
    			p.second = freeSquares.get(i).second;
    	    }
    	}

    	return max;

    }
    
    static int mini (Board b, PLAYER play_as, int depth, Pair p) {

    	NR_APELURI++;
    	
    	ArrayList<Pair> freeSquares = b.GetEmptySquares();

    	if (depth <= 0 || freeSquares.size() == 0 || 
    			b.GetWinner() == WINNER.PLAYER_X || b.GetWinner() == WINNER.PLAYER_ZERO)
    		return -evaluate(b, play_as);

		int min = INF;
    	
    	for (int i = 0; i < freeSquares.size(); i++) {
    		Board b1 = new Board(b);
    		int score;
    	   	Pair p1 = new Pair(-1, -1);
    		if (play_as == PLAYER.PLAY_AS_X) {
    			b1.MarkX(freeSquares.get(i).first, freeSquares.get(i).second);
    			score = maxi (b1, PLAYER.PLAY_AS_ZERO, depth - 1, p1);
    		}
    		else {
    			b1.MarkZero(freeSquares.get(i).first, freeSquares.get(i).second);
    			score = maxi (b1, PLAYER.PLAY_AS_X, depth - 1, p1);
    		}
 
    		b1.ClearSquare(freeSquares.get(i).first, freeSquares.get(i).second);
    		if (score <= min) {
    			min = score;
    			p.first = freeSquares.get(i).first;
    			p.second = freeSquares.get(i).second;
    		}
    	}
    	return min;
    	
    }
    
    private static int alphaBetaMaxi(int alpha, int beta, int depth, Board b, PLAYER play_as, Pair nextMove) {
    	NR_APELURI++;
    	
    	ArrayList<Pair> freeSquares = b.GetEmptySquares();
    	
    	if (depth <= 0 || freeSquares.size() == 0 || 
    			b.GetWinner() == WINNER.PLAYER_X || b.GetWinner() == WINNER.PLAYER_ZERO)
    		return evaluate(b, play_as);
    
    	int n = freeSquares.size();
    	
    	for (int i = 0; i < n; i++) {
    		Board copie = new Board(b);
    		Pair p1 = new Pair(-1, -1);
    		int r = 0;
    		if (play_as == PLAYER.PLAY_AS_X) {
    			copie.MarkX(freeSquares.get(i).first, freeSquares.get(i).second);
    			r = alphaBetaMini(alpha, beta, depth - 1, copie, PLAYER.PLAY_AS_ZERO, p1);
    		} else {
    			copie.MarkZero(freeSquares.get(i).first, freeSquares.get(i).second);
    			r = alphaBetaMini(alpha, beta, depth - 1, copie, PLAYER.PLAY_AS_X, p1);
    		}
    		copie.ClearSquare(freeSquares.get(i).first, freeSquares.get(i).second);
    		if (alpha < r) {
    			alpha = r;
    			nextMove.first = freeSquares.get(i).first;
    			nextMove.second = freeSquares.get(i).second;
    		}
    		if (r >= beta) {
    			return beta;
    		}
    	}
    	return alpha;
    }
    
    private static int alphaBetaMini(int alpha, int beta, int depth, Board b, PLAYER play_as, Pair nextMove) {
    	NR_APELURI++;
    	
    	ArrayList<Pair> freeSquares = b.GetEmptySquares();
    	
    	if (depth <= 0 || freeSquares.size() == 0 || 
    			b.GetWinner() == WINNER.PLAYER_X || b.GetWinner() == WINNER.PLAYER_ZERO)
    		return -evaluate(b, play_as);
    
    	int n = freeSquares.size();
    	
    	for (int i = 0; i < n; i++) {
    		Board copie = new Board(b);
    		int r = 0;
    		Pair p1 = new Pair(-1, -1);
    		if (play_as == PLAYER.PLAY_AS_X) {
    			copie.MarkX(freeSquares.get(i).first, freeSquares.get(i).second);
    			r = alphaBetaMaxi(alpha, beta, depth - 1, copie, PLAYER.PLAY_AS_ZERO, p1);
    		} else {
    			copie.MarkZero(freeSquares.get(i).first, freeSquares.get(i).second);
    			r = alphaBetaMaxi(alpha, beta, depth - 1, copie, PLAYER.PLAY_AS_X, p1);
    		}
    		copie.ClearSquare(freeSquares.get(i).first, freeSquares.get(i).second);
    		if (beta > r) {
    			beta = r;
    			nextMove.first = freeSquares.get(i).first;
    			nextMove.second = freeSquares.get(i).second;
    		}
    		if (r <= alpha) {
    			return alpha;
    		}
    	}
    	return beta;
    }
    
    static int evaluate (Board b, PLAYER play_as) {
    	
    	int score = 0;
    	WINNER w = b.GetWinner();

    	if (play_as == PLAYER.PLAY_AS_X) {
    		switch (w) {
			case DRAW:
				score = 0;
				break;
			case INVALID:
				// e degeaba
				score = 44;
				break;
			case PLAYER_NONE:
				// e degeaba
				score = 0;
				break;
			case PLAYER_X:
				score = INF;
				break;
			case PLAYER_ZERO:
				score = -INF;
				break;
			}
    	}
    	else {
    		switch (w) {
			case DRAW:
				score = 0;
				break;
			case INVALID:
				// e degeaba
				score = 44;
				break;
			case PLAYER_NONE:
				// e degeaba
				score = 0;
				break;
			case PLAYER_X:
				score = -INF;
				break;
			case PLAYER_ZERO:
				score = INF;
				break;
			}
    	}

    	if (score == 44)
    		System.out.println(score);
    	return score;
    	
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Board b = new Board();
        Scanner in = new Scanner(System.in);

        Random r = new Random(System.currentTimeMillis());
        int start = Math.abs(r.nextInt()) % 2;
        int init = start;
        // Daca start este 1, incepe calculatorul, altfel jucatorul
        PLAYER perspective = PLAYER.PLAY_AS_X;
        // X mereu va avea prima mutare


        while (b.GetWinner() == WINNER.PLAYER_NONE) {
            Pair current_move;
            if (start == 1) {
                System.out.println("Computer is thinking...");
                Board newBoard = new Board(b);
                current_move = PlayMove(newBoard, perspective);
                System.out.println("Computer chose move: (" + current_move.first +" " + current_move.second + ")");
            } else {
                int row, col;
                System.out.println("It's your turn, enter move:");
                do {
                    System.out.println("Row:");
                    row = in.nextInt();
                } while (row < 0 || row > 2);
                do {
                    System.out.println("Column:");
                    col = in.nextInt();
                } while (col < 0 || col > 2);
                current_move = new Pair(row, col);
            }
            if (b.GetMark(current_move.first, current_move.second) != MARK.NONE) {
                System.out.println("Invalid move, exiting");
                return;
            }
            if (perspective == PLAYER.PLAY_AS_X) {
                b.MarkX(current_move.first, current_move.second);
            } else {
                b.MarkZero(current_move.first, current_move.second);
            }
            start = 1 - start;
            perspective = (perspective == PLAYER.PLAY_AS_X) ? PLAYER.PLAY_AS_ZERO : PLAYER.PLAY_AS_X;
            System.out.println("Current board is:\n" + b);
        }

        System.out.println("Game is finished");
        if (b.GetWinner() == WINNER.PLAYER_X) {
            if (init == 1) {
                System.out.println("Computer won");
                return;
            }
            System.out.println("Player won, you should at most draw, keep working on the AI");
            return;
        }
        if (b.GetWinner() == WINNER.PLAYER_ZERO) {
            if (init == 1) {
                System.out.println("Player won, you should at most draw, keep working on the AI");
                return;
            }
            System.out.println("Computer won");
            return;
        }
        System.out.println("Draw, Well Done!");
        return;
    }
}
