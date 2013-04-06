package com.neodem.othello.data;

import java.io.PrintStream;

public interface Board {

	public abstract void print(PrintStream out);

	public abstract SpotState[][] getBoard();
	
	public abstract int getBoardSize();
	
	public abstract boolean isMoveLegal(Move move);

	/**
	 * 
	 * @param state
	 * @return
	 */
	public abstract boolean boardFull();

	/**
	 * 
	 * @param row
	 * @param col
	 * @param state
	 * @return
	 * @throws NonEmptyMoveException
	 * @throws NoAdjacentMoveException
	 * @throws NoFlipsPossibleMoveException
	 */
	public abstract void move(Move move, SpotState state) throws MoveException;

	/**
	 * return true if there are availble moves for a given color.
	 * @param state
	 * @return
	 */
	public abstract boolean movesAvailable(SpotState state);
	public abstract int getBlackScore();
	public abstract int getWhiteScore();
	public abstract String getBoardString();

}