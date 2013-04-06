package com.neodem.othello.data;

public class NonEmptyMoveException extends MoveException {

	public NonEmptyMoveException() {
	}

	public NonEmptyMoveException(String arg0) {
		super(arg0);
	}

	public NonEmptyMoveException(Throwable arg0) {
		super(arg0);
	}

	public NonEmptyMoveException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
