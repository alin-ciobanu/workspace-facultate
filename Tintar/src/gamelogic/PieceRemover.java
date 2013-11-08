package gamelogic;

import structs.Pair;
import enums.PLAYER;
import exceptions.NoMorePiecesOnTableException;

public abstract class PieceRemover implements IPieceRemover {

	protected Board b;

	public PieceRemover (Board b) {
		this.b = b;
	}

	public abstract Pair<Integer, Integer> getRemovingPiecePosition (PLAYER player)
			throws NoMorePiecesOnTableException;

	public abstract Pair<Integer, Integer> removePieceAfterCompletingLine (PLAYER player);

	/**
	 * Updateaza board-ul.
	 * 
	 * @param b - Board-ul
	 */
	public void updateBoard(Board b) {

		this.b = b;

	}

}
