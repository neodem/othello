package com.neodem.othello.test;

import com.neodem.othello.data.Board;
import com.neodem.othello.data.DefaultBoard;

public class TestDisplay {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Board b = new DefaultBoard();
		b.print(System.out);
		
	}

}
