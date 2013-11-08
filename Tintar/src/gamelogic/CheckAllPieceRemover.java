package gamelogic;

import java.util.Iterator;
import java.util.TreeSet;

import structs.Pair;
import enums.MARK;
import enums.PLAYER;
import exceptions.NoMorePiecesOnTableException;

public class CheckAllPieceRemover extends PieceRemover {

	public CheckAllPieceRemover(Board b) {
		super(b);
	}

	/**
	 * Ia fiecare piesa in parte si verifica scorul dupa ce s-a scos piesa.
	 * Pentru a scoate eficienta o piesa (adica sa rezulte un scor mare pentru
	 * jucatorul care ere dreptul sa o scoata: adica !player) trebuie ca scorul pentru
	 * @player sa fie cat mai mic dupa scoaterea piesei
	 * 
	 * @player - player-ul pentru care se sterge o piesa
	 */
	@Override
	public Pair<Integer, Integer> getRemovingPiecePosition(PLAYER player)
			throws NoMorePiecesOnTableException {

		MARK mark;
		if (player == PLAYER.BLACK) {
			mark = MARK.BLACK;
		}
		else {
			mark = MARK.WHITE;
		}

		TreeSet<Pair<Integer, Integer>> activePoints;
		activePoints = b.getAllPointsOfBoard();
		Iterator<Pair<Integer, Integer>> it = activePoints.iterator();

		int k = 0; // verifica daca exista piese pe tabla

		Pair<Integer, Integer> best = null; // piesa care urmeaza sa fie removed
		int minScore = Integer.MAX_VALUE; // scorul piesei scoase (trebuie sa fie minim)

		while (it.hasNext()) {

			Pair<Integer, Integer> current = it.next();
			int score;

			if (b.getMark(current) == mark) {

				k++;

				if (k == 1) {
					// daca este primul element, initializam best cu current
					// pentru a nu returna null in cazul in care toate piesele
					// sunt intr-o linie (formeaza o linie)
					best = current;
				}

				BoardScore bscore = new BoardScore(b);
				boolean line = bscore.checkLineCompleted(current, mark);

				b.removePiece(current);
				score = b.getScore(player);

				if (!line && score < minScore) {
					minScore = score;
					best = current;
				}

				b.putPiece(current, mark);

			}

		}

		if (k == 0) {
			throw new NoMorePiecesOnTableException();
		}

		return best;

	}

	/**
	 * Sterge o piesa a jucatorului @player dupa strategia clasei
	 */
	@Override
	public Pair<Integer, Integer> removePieceAfterCompletingLine(PLAYER player) {

		Pair<Integer, Integer> piece = null;

		try {
			piece = this.getRemovingPiecePosition(player);
		} catch (NoMorePiecesOnTableException e) {
			e.printStackTrace();
		}

		b.removePiece(piece);
		return piece;

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
