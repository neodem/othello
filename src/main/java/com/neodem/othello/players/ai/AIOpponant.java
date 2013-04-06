package com.neodem.othello.players.ai;

import com.neodem.othello.data.Board;
import com.neodem.othello.data.Move;
import com.neodem.othello.data.SpotState;
import com.neodem.othello.players.Player;

public abstract class AIOpponant implements Player {

	protected SpotState myColor;
	protected SpotState starter;

	public void gameOver(SpotState winner) {
	}
	
	public void registerOpponantMove(Move move) {
	}

	public void newGame(SpotState assigned, SpotState starter, Board board) {
		myColor = assigned;
		this.starter = starter;	
	}
}
