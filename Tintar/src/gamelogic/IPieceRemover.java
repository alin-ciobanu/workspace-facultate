package gamelogic;

import structs.Pair;
import enums.PLAYER;
import exceptions.NoMorePiecesOnTableException;

public interface IPieceRemover {

	public abstract Pair<Integer, Integer> getRemovingPiecePosition (PLAYER player)
			throws NoMorePiecesOnTableException;

	public abstract Pair<Integer, Integer> removePieceAfterCompletingLine (PLAYER player);

}
