package graphics;

import enums.MARK;
import enums.PLAYER;
import enums.WINNER;
import gamelogic.Board;
import gamelogic.BoardScore;
import gamelogic.Bot;

import java.util.LinkedList;

import structs.Pair;


public class GameFlowManager {

	private int whitesLeft, blacksLeft;
	private final LinkedList<PiecePoint> points;
	private final Board board;
	private final Bot bot;

	private boolean lineCompleted = false;


	public GameFlowManager (int whitesLeft, int blacksLeft, LinkedList<PiecePoint> points,
			Board board, Bot bot) {

		this.whitesLeft = whitesLeft;
		this.blacksLeft = blacksLeft;
		this.points = points;
		this.board = board;
		this.bot = bot;

	}

	public void putPiece (int x, int y, PLAYER player) {

		putPiece (new Pair<Integer, Integer>(x, y), player);

	}

	public void putPiece (Pair<Integer, Integer> pos, PLAYER player) {

		lineCompleted = false;

		if (player == PLAYER.BLACK) {
			board.putPiece(pos, MARK.BLACK);
			blacksLeft--;
		} else {
			board.putPiece(pos, MARK.WHITE);
			whitesLeft--;
		}

		BoardScore bscore = new BoardScore(board);
		if (bscore.checkLineCompleted(pos, player)) {
			lineCompleted = true;
		}

	}

	public void movePiece (Pair<Integer, Integer> from, Pair<Integer, Integer> to, PLAYER player) {

		lineCompleted = false;

		board.movePiece(from, to);

		BoardScore bscore = new BoardScore(board);
		if (bscore.checkLineCompleted(to, player)) {
			lineCompleted = true;
		}

	}

	public Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> doBotMove () {

		lineCompleted = false;

		Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> move =
				bot.move(board, blacksLeft, whitesLeft);

		Pair<Integer, Integer> to;
		to = move.getSecond();
		PLAYER bot_player = bot.getPlayer();

		BoardScore bscore = new BoardScore(board);
		if (bscore.checkLineCompleted(to, bot_player)) {
			lineCompleted = true;
		}

		return move;

	}

	public void removePiece (int line, int col) {

		board.removePiece(line, col);
		lineCompleted = false;

	}

	public Pair<Integer, Integer> removePointAfterCompletingLine (PLAYER player) {

		Pair<Integer, Integer> pos =
				bot.getPieceRemover(board).removePieceAfterCompletingLine(player);
		lineCompleted = false;

		return pos;

	}

	public int getWhitesLeft () {

		return whitesLeft;

	}

	public int getBlacksLeft () {

		return blacksLeft;

	}

	public int getPlayerPiecesLeft (PLAYER player) {

		if (player == PLAYER.BLACK) {
			return blacksLeft;
		}
		else {
			return whitesLeft;
		}

	}

	public int getOpponentPiecesLeft (PLAYER opponent) {

		if (opponent == PLAYER.BLACK) {
			return blacksLeft;
		}
		else {
			return whitesLeft;
		}

	}


	public boolean checkLineCompletion () {

		return lineCompleted;

	}

	public void resetLineCompleted () {

		lineCompleted = false;

	}

	public WINNER getWinner () {

		return board.getWinner();

	}

	public void printBoard () {

		System.out.println(board);

	}

}
