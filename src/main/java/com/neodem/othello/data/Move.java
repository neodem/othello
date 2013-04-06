package com.neodem.othello.data;

public class Move {
	private int row;
	private int col;
	private SpotState color;
	
	public String toString() {
		return "" + (char)('A' + col) + row;
	}
	
	public Move(int row, int col, SpotState color) {
		this.row = row;
		this.col = col;
		this.color = color;
	}
 
	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public SpotState getColor() {
		return color;
	}

	public void setColor(SpotState color) {
		this.color = color;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}
}
