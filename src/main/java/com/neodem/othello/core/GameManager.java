package com.neodem.othello.core;

import java.util.Random;

import com.neodem.othello.data.Board;
import com.neodem.othello.data.DefaultBoard;
import com.neodem.othello.data.Move;
import com.neodem.othello.data.MoveException;
import com.neodem.othello.data.SpotState;
import com.neodem.othello.players.Player;

/**
 * This will manage a single game.
 * 
 * @author VFumo000
 * 
 */
public class GameManager {
	protected static final Random r = new Random(System.currentTimeMillis());

	private SpotState turn;

	private Board board;
	private Player black;
	private Player white;

	public GameManager(Player black, Player white) {
		this.black = black;
		this.white = white;
	}

	/**
	 * 
	 * @return the winner
	 */
	public void play() {
		setup();
		displayBoard(board);
		run();
		gameOver();
	}

	public void reset() {
		board = new DefaultBoard();
		boolean whiteFirst = r.nextBoolean();
		if (whiteFirst) {
			turn = SpotState.WHITE;
		} else {
			turn = SpotState.BLACK;
		}
		black.newGame(SpotState.BLACK, turn, board);
		white.newGame(SpotState.WHITE, turn, board);
	}

	private void setup() {
		reset();
	}

	private void run() {
		while (true) {
			Move move = getMove(getCurrentPlayer(turn), turn);
			Player otherPlayer = getCurrentPlayer(turn.otherColor());
			otherPlayer.registerOpponantMove(move);
			displayBoard(board);
			if (board.boardFull()) {
				break;
			}
			if (!board.movesAvailable(turn)) {
				break;
			}
			if (board.movesAvailable(turn.otherColor())) {
				turn = turn.otherColor();
			}
		}
	}

	private void displayBoard(Board board) {
		StringBuffer b = new StringBuffer();
		b.append('\n');
		b.append("Score : ");
		b.append(SpotState.BLACK);
		b.append(" ");
		b.append(board.getBlackScore());
		b.append(", ");
		b.append(SpotState.WHITE);
		b.append(" ");
		b.append(board.getWhiteScore());
		b.append('\n');

		b.append(board.getBoardString());
		b.append("turn : " + turn);
		System.out.println(b.toString());
	}

	private void gameOver() {
		StringBuffer b = new StringBuffer();
		b.append('\n');
		b.append("Game Over");
		b.append('\n');
		b.append("Score : ");
		b.append(SpotState.BLACK);
		b.append(" ");
		int blackScore = board.getBlackScore();
		b.append(blackScore);
		b.append(", ");
		b.append(SpotState.WHITE);
		b.append(" ");
		int whiteScore = board.getWhiteScore();
		b.append(whiteScore);
		b.append('\n');

		System.out.println(b.toString());

		if (whiteScore > blackScore) {
			white.gameOver(SpotState.WHITE);
			black.gameOver(SpotState.WHITE);
		} else if (whiteScore < blackScore) {
			white.gameOver(SpotState.BLACK);
			black.gameOver(SpotState.BLACK);
		} else {
			white.gameOver(SpotState.EMPTY);
			black.gameOver(SpotState.EMPTY);
		}
	}

	private Player getCurrentPlayer(SpotState turn) {
		if (turn == SpotState.BLACK) {
			return black;
		}

		return white;
	}

	private Move getMove(Player player, SpotState turn) {
		System.out.print("move : ");
		Move move = player.getMove(board);
		System.out.println(move);

		try {
			board.move(move, turn);
		} catch (MoveException e) {
			System.out.println(e.getMessage());
			System.out.println(" ");
			getMove(player, turn);
		}

		return move;
	}
}
