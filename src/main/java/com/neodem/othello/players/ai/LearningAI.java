package com.neodem.othello.players.ai;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

import com.neodem.othello.data.Board;
import com.neodem.othello.data.Move;
import com.neodem.othello.data.SpotState;
import com.neodem.othello.tracker.MoveTracker;

public class LearningAI extends RandomAI {

	private MoveTracker dataTree;
	private File dataFile;
	private int boardSize;

	public LearningAI(File dataFile) {
		this.dataFile = dataFile;
		dataTree = new MoveTracker();
		if (dataFile.exists()) {
			loadData(dataFile);
		}
	}

	public LearningAI(File dataFile, boolean playful, PrintStream out) {
		super(playful, out);
		dataTree = new MoveTracker();
		if (dataFile.exists()) {
			loadData(dataFile);
		}
	}

	private void loadData(File dataFile) {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(dataFile));
			String line;
			while ((line = in.readLine()) != null) {
				loadLine(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void loadLine(String line) {
		String[] strings = line.split(":");
		SpotState starter = SpotState.make(strings[0]);
		SpotState winner = SpotState.make(strings[1]);
		int boardSize = Integer.parseInt(strings[2]);
		String[] moves = strings[3].split(",");
		if (boardSize == this.boardSize) {
			dataTree.startNewGame(starter);
			for (int i = 0; i < moves.length; i++) {
				int rawMove = Integer.parseInt(moves[i]);
				dataTree.addMove(rawMove);
			}
			dataTree.endGame(winner);
		}
	}
	
	protected String makeLine(SpotState winner) {
		StringBuffer b = new StringBuffer();
		b.append(starter);
		b.append(":");
		if (winner == SpotState.EMPTY) {
			b.append(starter.otherColor());
		} else {
			b.append(winner);
		}
		b.append(":");
		b.append(boardSize);
		b.append(":");
		for (int i = 0; i < moveNumber; i++) {
			b.append(moves[i]);
			b.append(",");
		}
		return b.toString();
	}
	
	private int maxSize;
	protected int[] moves;
	private int moveNumber;
	public void storeMove(Move move) {
		if (moveNumber < maxSize) {
			moves[moveNumber++] = makeRawMove(move);
		}
	}
	

	public void gameOver(SpotState winner) {
		dataTree.endGame(winner);
		String gameString = makeLine(winner);
		PrintWriter out = null;
		try {
			out = new PrintWriter(new BufferedOutputStream(new FileOutputStream(dataFile, true)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		out.println(gameString);
		out.close();
	}
	
	protected int makeRawMove(Move move) {
		return move.getRow() * boardSize + move.getCol();
	}


	public void registerOpponantMove(Move move) {
		dataTree.addMove(makeRawMove(move));
	}

	public void newGame(SpotState assigned, SpotState starter, Board board) {
		super.newGame(assigned, starter, board);
		dataTree.startNewGame(starter);
		boardSize = board.getBoardSize();
		maxSize = boardSize * boardSize;
		moves = new int[maxSize];
		moveNumber = 0;
	}

	public Move determineNextMove(Board board, SpotState color) {
		Move move = getDeterminedMove(board, color);
		if (move == null) {
			move = super.getRandomMove(board, color);
		}
		dataTree.addMove(makeRawMove(move));

		return move;
	}

	/**
	 * use the data tree.
	 * 
	 * @param board
	 * @param color
	 * @return
	 */
	protected Move getDeterminedMove(Board board, SpotState color) {
		int rawMove = dataTree.getBestNextMove();
		if (rawMove != -1) {
			int row = rawMove / boardSize;
			int col = rawMove - (row * boardSize);
			return new Move(row, col, color);
		}
		return null;
	}
}
