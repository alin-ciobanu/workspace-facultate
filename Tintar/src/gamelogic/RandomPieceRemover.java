package gamelogic;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeSet;

import structs.Pair;
import enums.MARK;
import enums.PLAYER;
import exceptions.NoMorePiecesOnTableException;

public class RandomPieceRemover extends PieceRemover {

	public RandomPieceRemover(Board b) {
		super(b);
	}

	/**
	 * 
	 * @player - player-ul a carui piesa este stearsa
	 * @return - pozitia de pe care piesa trebuie stearsa
	 * @throws NoMorePiecesOnTableException
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

		LinkedList<Pair<Integer, Integer>> points;
		points = new LinkedList<Pair<Integer, Integer>>();

		while (it.hasNext()) {

			Pair<Integer, Integer> point = it.next();
			MARK currentMARK = b.getMark(point);

			if (currentMARK == mark) {
				points.add(point);
			}

		}

		if (points.size() == 0) {
			throw new NoMorePiecesOnTableException();
		}

		int size = points.size();
		long time;
		int index;
		Pair<Integer, Integer> pos = null;

		while (size > 0) {

			time = System.currentTimeMillis();
			index = (int) (time % size);
			pos = points.get(index);
			BoardScore bscore = new BoardScore(b);

			if (bscore.checkLineCompleted(pos, mark)) {
				points.remove(index);
				size--;
			}
			else {
				size = 0;
			}

		}

		return pos;

	}

	/**
	 * Sterge o piesa (random) a jucatorului @player de pe board
	 * @throws NoMorePiecesOnTableException
	 */
	@Override
	public Pair<Integer, Integer> removePieceAfterCompletingLine(PLAYER player) {

		Pair<Integer, Integer> removed = null;
		try {
			removed = this.getRemovingPiecePosition(player);
		} catch (NoMorePiecesOnTableException e) {
			e.printStackTrace();
		}

		b.removePiece(removed);

		return removed;

	}


	public static void main(String[] args) {

	}

}
