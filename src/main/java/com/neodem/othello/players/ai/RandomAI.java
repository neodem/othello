package com.neodem.othello.players.ai;

import java.io.PrintStream;
import java.util.Random;

import com.neodem.othello.data.Board;
import com.neodem.othello.data.Move;
import com.neodem.othello.data.SpotState;

public class RandomAI extends AIOpponant {

	protected static final Random r = new Random(System.currentTimeMillis());

	protected boolean playful = false;

	private PrintStream out;

	public RandomAI() {
	}

	public RandomAI(boolean playful, PrintStream out) {
		this.playful = playful;
		this.out = out;
	}

	public Move getMove(Board board) {
		Move move = determineNextMove(board, myColor);
		
		if (playful) {
			out.print("Thinking");
			try {
				Thread.sleep(r.nextInt(500));
				out.print('.');
				Thread.sleep(r.nextInt(900));
				out.print('.');
				Thread.sleep(r.nextInt(1100));
				out.print('.');
				Thread.sleep(r.nextInt(1100));
				out.print("ahh!! ");
			} catch (InterruptedException e) {
			}
		}

		return move;
	}
	
	protected Move getRandomMove(Board board, SpotState color) {
		int max = board.getBoardSize();
		int row;
		int col;
		Move move = null;

		do {
			row = r.nextInt(max);
			col = r.nextInt(max);

			move = new Move(row, col, color);
			if (board.isMoveLegal(move)) {
				break;
			}
		} while (true);
		return move;
	}

	protected Move determineNextMove(Board board, SpotState color) {
		return getRandomMove(board, color);
	}

	public PrintStream getOut() {
		return out;
	}

	public void setOut(PrintStream out) {
		this.out = out;
	}

	public boolean isPlayful() {
		return playful;
	}

	public void setPlayful(boolean playful) {
		this.playful = playful;
	}
}
