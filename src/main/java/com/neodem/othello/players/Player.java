package com.neodem.othello.players;

import com.neodem.othello.data.Board;
import com.neodem.othello.data.Move;
import com.neodem.othello.data.SpotState;

public interface Player {
	
	/**
	 * get a move from the player.
	 * @param color is the color the player is moving for
	 * @param board the board that the player has to work with
	 * @return
	 */
	public Move getMove(Board board);
	
	/**
	 * Let the player know where their opponant moved.
	 * 
	 * @param move
	 */
	public void registerOpponantMove(Move move);
	
	/**
	 * 
	 * @param assigned the color assigned to this player
	 * @param starter the color starting the game
	 * @param board the current board
	 */
	public void newGame(SpotState assigned, SpotState starter, Board board);
	
	/**
	 * alert the player that the game is over.
	 * @param winner
	 */
	public void gameOver(SpotState winner);

}
