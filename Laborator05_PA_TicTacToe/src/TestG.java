
public class TestG {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Board b = new Board();
		
		b.MarkX(0, 0);
		b.MarkX(0, 1);
		b.MarkX(0, 2);
		b.MarkZero(1, 0);
		b.MarkZero(1, 1);
		b.MarkZero(1, 2);

		System.out.println(b);
		
		if (b.GetWinner() == WINNER.INVALID)
			System.out.println("yes");


	}

}
