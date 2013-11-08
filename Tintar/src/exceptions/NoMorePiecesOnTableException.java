package exceptions;

public class NoMorePiecesOnTableException extends Exception {

	private static final long serialVersionUID = 1L;

	public NoMorePiecesOnTableException () {		
	}

	public NoMorePiecesOnTableException (String msg) {
		super(msg);
	}

}
