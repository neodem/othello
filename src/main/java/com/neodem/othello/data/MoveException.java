package com.neodem.othello.data;

public class MoveException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MoveException() {
	}

	public MoveException(String arg0) {
		super(arg0);
	}

	public MoveException(Throwable arg0) {
		super(arg0);
	}

	public MoveException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
