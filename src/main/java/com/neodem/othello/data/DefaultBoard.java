package com.neodem.othello.data;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DefaultBoard implements Board {
	public static int BOARDSIZE = 8;

	public SpotState[][] board = new SpotState[BOARDSIZE][BOARDSIZE];

	public int emptyCount = 0;
	public int blackCount = 0;
	public int whiteCount = 0;
	
	public DefaultBoard() {
		// clear board
		for (int row = 0; row < getBoardSize(); row++) {
			for (int col = 0; col < getBoardSize(); col++) {
				clearSpot(row, col);
			}
		}
		
		// set centers
		fillSpot(new Move(3,3,SpotState.BLACK));
		fillSpot(new Move(4,4,SpotState.BLACK));
		fillSpot(new Move(3,4,SpotState.WHITE));
		fillSpot(new Move(4,3,SpotState.WHITE));
	}
	
	private void clearSpot(Move move) {
		clearSpot(move.getRow(), move.getCol());
	}
	
	private void clearSpot(int row, int col) {
		SpotState current = board[row][col];
		if(current == SpotState.EMPTY) {
			return;
		}
		
		if(current == SpotState.BLACK) {
			blackCount--;
		}
		if(current == SpotState.WHITE) {
			whiteCount--;
		}
		
		board[row][col] = SpotState.EMPTY;
		emptyCount++;
	}
	
	private void fillSpot(Move move) {
		clearSpot(move);
		board[move.getRow()][move.getCol()] = move.getColor();
		if(move.getColor() == SpotState.WHITE) {
			whiteCount++;
		}
		else {
			blackCount++;
		}
		emptyCount--;
	}
	
	public String getBoardString() {
		StringBuffer b = new StringBuffer();
		
		//top row
		b.append(' ');
		for (int col = 0; col < getBoardSize(); col++) {
			b.append(' ');
			b.append((char)('A' + col));
		}
		b.append(' ');
		b.append('\n');

		// board
		for (int row = 0; row < getBoardSize(); row++) {
			b.append(row);
			for (int col = 0; col < getBoardSize(); col++) {
				b.append("|");
				b.append(board[row][col]);
			}
			b.append('|');
			b.append('\n');
		}

		// draw bottom line
		b.append(' ');
		for (int col = 0; col < getBoardSize(); col++) {
			b.append("--");
		}
		b.append('-');
		b.append('\n');
		
		return b.toString();
	}
	
	
	/* (non-Javadoc)
	 * @see com.neodem.othello.data.Board#print(java.io.PrintStream)
	 */
	public void print(PrintStream out) {
		out.println(getBoardString());
	}

	/* (non-Javadoc)
	 * @see com.neodem.othello.data.Board#getBoard()
	 */
	public SpotState[][] getBoard() {
		return board;
	}
	
	/* (non-Javadoc)
	 * @see com.neodem.othello.data.Board#gameOver()
	 */
	public boolean boardFull() {
		if(emptyCount == 0) return true;
		return false;
	}
	
	
	/**
	 * Check to see if a given move is valid.. Throw
	 * an exception if it's not.
	 * @param move
	 * @param state
	 * @throws NonEmptyMoveException 
	 * @throws NoAdjacentMoveException 
	 * @throws NoFlipsPossibleMoveException 
	 * @return a list of possible move directions.
	 */
	protected List<SpotDirection> validate(Move move) throws MoveException {
		int row = move.getRow();
		int col = move.getCol();
		SpotState state = move.getColor();
		
		// 1) is that move empty?
		if (board[row][col] != SpotState.EMPTY) {
			throw new NonEmptyMoveException("move at row[" + row + "], col["
					+ makeCol(col) + "] is not valid.. it's occupied by "
					+ board[row][col]);
		}

		// 2) is the move legal for the color?
		// 2a) is the move adjasent to a piece of another color?
		List<SpotDirection> directions = getAdjacents(row, col, state
				.otherColor());
		if ((directions == null) || (directions.size() == 0)) {
			throw new NoAdjacentMoveException(
					"move at row["
							+ row
							+ "], col["
							+ makeCol(col)
							+ "] is not valid.. there are no adjacent pices of the opposite color");
		}

		// 2b) are there sandwitch opportunities
		for (Iterator iter = directions.iterator(); iter.hasNext();) {
			SpotDirection direction = (SpotDirection) iter.next();
			if (flipPossible(move, state, direction)) {
				return directions;
			} 
		}
		
		throw new NoFlipsPossibleMoveException("move at row[" + row
				+ "], col[" + makeCol(col)
				+ "] is not valid.. there are no flip possibilities");
		
	}

	/* (non-Javadoc)
	 * @see com.neodem.othello.data.Board#move(com.neodem.othello.data.Move, com.neodem.othello.data.SpotState)
	 */
	public void move(Move move, SpotState state) throws MoveException {
		
		List<SpotDirection> directions = validate(move);
		
		for (Iterator iter = directions.iterator(); iter.hasNext();) {
			SpotDirection direction = (SpotDirection) iter.next();
			if (flipPossible(move, state, direction)) {
				fillSpot(move);
				doFlip(move, direction);
			} 
		}
	}

	private String makeCol(int col) {
		return "" + (char)('A' + col);
	}


	private boolean flipPossible(Move move, SpotState state,
			SpotDirection direction) {
		return flipPossible(move, state, direction, false);
	}

	/**
	 * detemine if a flip is possible.
	 * 
	 * @param row
	 *            the starting row
	 * @param col
	 *            the starting col
	 * @param initialColor
	 *            the current spot color
	 * @param direction
	 *            the direction of the intended flip
	 * @return
	 */
	private boolean flipPossible(Move move, SpotState initialColor,
			SpotDirection direction, boolean inflip) {
		if (initialColor == SpotState.EMPTY) {
			return false;
		}
		
		
		int testRow = direction.rowOffset() + move.getRow();
		int testCol = direction.colOffset() + move.getCol();
		// are we on an edge?
		if ((testRow < 0) || (testRow == getBoardSize())) {
			return false;
		}
		if ((testCol < 0) || (testCol == getBoardSize())) {
			return false;
		}

		SpotState adjacent = board[testRow][testCol];

		// is the next piece a different color?
		if (adjacent == initialColor.otherColor()) {
			Move testMove = new Move(testRow, testCol, null);
			return flipPossible(testMove, initialColor, direction, true);
		}

		if (inflip) {
			if (adjacent == initialColor) {
				return true;
			}
		}

		return false;
	}

	/**
	 * do a flip in the selected direction (if possible). Return true if done,
	 * false if not possible
	 * 
	 * @param row
	 * @param col
	 * @param initialColor
	 * @param direction
	 * @return
	 */
	private void doFlip(Move move, SpotDirection direction) {
		int testRow = move.getRow();
		int testCol = move.getCol();
		boolean notDone = true;
		do {
			testRow += direction.rowOffset();
			testCol += direction.colOffset();
			if (board[testRow][testCol] != move.getColor()) {
				fillSpot(new Move(testRow, testCol, move.getColor()));
			} else {
				notDone = false;
			}
		} while (notDone);
	}

	/**
	 * return an array of directions where there is a piece that matches the
	 * given color
	 * 
	 * @param row
	 * @param col
	 * @param state
	 * @return
	 */
	private List<SpotDirection> getAdjacents(int row, int col, SpotState state) {
		List<SpotDirection> dirs = new ArrayList<SpotDirection>();

		for (SpotDirection dir : SpotDirection.values()) {
			int testRow = dir.rowOffset() + row;
			int testCol = dir.colOffset() + col;
			if ((testRow < 0) || (testRow == getBoardSize())) {
				continue;
			}
			if ((testCol < 0) || (testCol == getBoardSize())) {
				continue;
			}

			if (board[testRow][testCol] == state) {
				dirs.add(dir);
			}
		}

		return dirs;
	}

	/* (non-Javadoc)
	 * @see com.neodem.othello.data.Board#movesAvailable(com.neodem.othello.data.SpotState)
	 */
	public boolean movesAvailable(SpotState state) {
		for (int row = 0; row < getBoardSize(); row++) {
			for (int col = 0; col < getBoardSize(); col++) {
				if (board[row][col] != SpotState.EMPTY) {
					continue;
				}
				Move move = new Move(row, col, state);
				if(isMoveLegal(move)) {
					return true;
				}
			}
			
		}
		return false;
	}

	public int getBoardSize() {
		return BOARDSIZE;
	}

	public boolean isMoveLegal(Move move) {
		try {
			validate(move);
			return true;
		} catch (MoveException e) {
			return false;
		}
	}

	public int getBlackScore() {
		return blackCount;
	}

	public int getWhiteScore() {
		return whiteCount;
	}


}
