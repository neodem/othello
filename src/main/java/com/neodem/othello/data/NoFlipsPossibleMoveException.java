package com.neodem.othello.data;

public class NoFlipsPossibleMoveException extends MoveException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoFlipsPossibleMoveException() {
	}

	public NoFlipsPossibleMoveException(String message) {
		super(message);
	}

	public NoFlipsPossibleMoveException(Throwable cause) {
		super(cause);
	}

	public NoFlipsPossibleMoveException(String message, Throwable cause) {
		super(message, cause);
	}

}
