package com.neodem.othello.data;

public enum SpotDirection {
	NORTH, NORTH_EAST, EAST, SOUTH_EAST, SOUTH, SOUTH_WEST, WEST, NORTH_WEST;
	
	/**
	 * colums are numbered from 0 over (East) to 7
	 * @return
	 */
	public int colOffset() {
		switch(this) {
		case NORTH_EAST:
		case SOUTH_EAST:
		case EAST:	
			return 1;	
		case NORTH_WEST:
		case SOUTH_WEST:
		case WEST:	
			return -1;	
		default:
			return 0;
		}
	}
	
	/**
	 * rows are numbered from 0 down (South) to 7
	 * @return
	 */
	public int rowOffset() {
		switch(this) {
		case NORTH:
		case NORTH_EAST:
		case NORTH_WEST:	
			return -1;	
		case SOUTH:
		case SOUTH_WEST:
		case SOUTH_EAST:
			return 1;	
		default:
			return 0;
		}
	}
}
