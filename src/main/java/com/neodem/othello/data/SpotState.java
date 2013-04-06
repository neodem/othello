package com.neodem.othello.data;

public enum SpotState {
	BLACK,
	WHITE,
	EMPTY;
	
	public static SpotState make(String string) {
		if(string.equalsIgnoreCase("X")) {
			return BLACK;
		}
		if(string.equalsIgnoreCase("O")) {
			return WHITE;
		}
		return EMPTY;
	}
	
	public SpotState otherColor() {
		if(this == BLACK) {
			return WHITE;
		}
		if(this == WHITE) {
			return BLACK;
		}
		return EMPTY;
	}
	
	@Override
	public String toString() {
		if(this == BLACK) {
			return "X";
		}
		if(this == WHITE) {
			return "O";
		}
		return " ";
	}
}
