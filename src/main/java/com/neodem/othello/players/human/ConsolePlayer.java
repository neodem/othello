package com.neodem.othello.players.human;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import com.neodem.othello.data.Board;
import com.neodem.othello.data.Move;
import com.neodem.othello.data.SpotState;
import com.neodem.othello.players.Player;

public class ConsolePlayer implements Player {

	private PrintStream out;
	private InputStream in;
	private SpotState myColor;
	
	public ConsolePlayer(PrintStream out, InputStream in) {
		this.out = out;
		this.in = in;
	}

	public Move getMove(Board board) {
		String input = getLine();
		if ("".equals(input))
			return null;
		
		char colChar = input.charAt(0);
		if(colChar >= 97) {
			colChar -= 32;
		}
		char rowChar = input.charAt(1);
		int row = rowChar - 48;
		int col = colChar - 65;
		
		return new Move(row, col, myColor);
	}

	public void newGame(SpotState assigned, SpotState starter, Board board) {
		myColor = assigned;	
	}

	private String getLine() {
		InputStreamReader inputStreamReader = new InputStreamReader(in);
		BufferedReader stdin = new BufferedReader(inputStreamReader);

		String line = null;
		try {
			line = stdin.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return line;

	}

	public void gameOver(SpotState winner) {
	}

	public void registerOpponantMove(Move move) {
	}	
	
	public InputStream getIn() {
		return in;
	}

	public void setIn(InputStream in) {
		this.in = in;
	}

	public PrintStream getOut() {
		return out;
	}

	public void setOut(PrintStream out) {
		this.out = out;
	}

}
