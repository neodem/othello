package com.neodem.othello.core;

import java.io.File;

import com.neodem.othello.players.Player;
import com.neodem.othello.players.ai.LearningAI;
import com.neodem.othello.players.human.ConsolePlayer;

public class ConsoleGame {
	private String dataFileName = "data.out";
	private GameManager g;
	public ConsoleGame() {
		File dataFile = new File(dataFileName);
		Player white = new LearningAI(dataFile);
		Player black = new ConsolePlayer(System.out, System.in);
		g = new GameManager(white,black);
		g.play();
		
	}

//	private boolean playAgain() {
//		if(white.isHuman()) {
//			return white.playAgain();
//		}
//		if(black.isHuman()) {
//			return black.playAgain();
//		}	
//	
//		true;
//	}



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new ConsoleGame();
	}
}
