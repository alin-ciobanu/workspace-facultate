package gamelogic;

import java.util.Scanner;

import enums.DIFFICULTY;
import enums.MARK;
import enums.PLAYER;
import enums.WINNER;

public class GameFlow {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Board b = new Board();
		int whitesLeft, blacksLeft;
		whitesLeft = blacksLeft = 9;
		MARK human_mark = MARK.BLACK;

		Bot bot = new Bot(PLAYER.WHITE, DIFFICULTY.HARD);

		System.out.println(b);

		Scanner in = new Scanner(System.in);

		while (whitesLeft > 0 && blacksLeft > 0) {

			System.out.println("Make your turn. Where would you like to put your piece? Position: ");
			String next = in.nextLine();
			int i, j;
			i = Integer.parseInt(next.substring(0, 1));
			j = Integer.parseInt(next.substring(2, 3));
			b.putPiece(new IntegerPair(i, j), human_mark);

			System.out.println(b);

			bot.move(b, blacksLeft, whitesLeft);
			System.out.println(b);

		}

		while (b.getWinner() != WINNER.BLACK || b.getWinner() != WINNER.WHITE) {

			System.out.println("Make your turn. Move a piece from a place to another.");
			System.out.println("From: ");
			String next = in.nextLine();
			int i, j;
			i = Integer.parseInt(next.substring(0, 1));
			j = Integer.parseInt(next.substring(2, 3));
			IntegerPair from = new IntegerPair(i, j);
			System.out.println("To: ");
			next = in.nextLine();
			i = Integer.parseInt(next.substring(0, 1));
			j = Integer.parseInt(next.substring(2, 3));
			IntegerPair to = new IntegerPair(i, j);

			b.movePiece(from, to);

			System.out.println(b);

			bot.move(b, blacksLeft, whitesLeft);
			System.out.println(b);

		}

		if (b.getWinner() == WINNER.BLACK) {
			System.out.println("Congrats! You win.");
		}
		else {
			System.out.println("Fail! You lose.");
		}
		in.close();

	}

}
