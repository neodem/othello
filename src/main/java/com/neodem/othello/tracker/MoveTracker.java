package com.neodem.othello.tracker;

import java.util.HashSet;
import java.util.Set;

import com.neodem.othello.data.SpotState;

public class MoveTracker {

	private Node root;
	private Node pointer;
	private Node white;
	private Node black;

	class Node {
		public Node parent;

		public int move;

		public Set<Node> children = new HashSet<Node>();

		/**
		 * the number of children that are winners
		 */
		public int winners;
	}

	public int getBestNextMove() {
		Set<Node> nextMoves = pointer.children;
		
		int winners = 0;
		Node bestNode = null;
		if (nextMoves != null) {
			for (Node node : nextMoves) {
				if(node.winners > winners) {
					bestNode = node;
				}
			}
		}
		
		if(bestNode != null) {
			return bestNode.move;
		}
		
		return -1;
	}
	
	public MoveTracker() {
		white = new Node();
		black = new Node();
		pointer = root;
	}

	public void startNewGame(SpotState starter) {
		if (starter == SpotState.BLACK) {
			root = black;
		} else if (starter == SpotState.WHITE) {
			root = white;
		}
		pointer = root;
	}

	public void endGame(SpotState winner) {
		pointer.winners++;

		// go up the tree and update the winners
		while (pointer != root) {
			pointer = pointer.parent;
			pointer.winners++;
		}
	}

	public void addMove(int move) {
		Set<Node> nextMoves = pointer.children;

		Node correctChild = findCorrectChild(move, nextMoves);

		if (correctChild != null) {
			pointer = correctChild;
		} else {
			Node newChild = new Node();
			newChild.parent = pointer;
			newChild.move = move;
			nextMoves.add(newChild);
			pointer = newChild;
		}
	}

	protected Node findCorrectChild(int move, Set<Node> nextMoves) {
		if (nextMoves != null) {
			for (Node node : nextMoves) {
				if (node.move == move) {
					return node;
				}
			}
		}
		return null;
	}

}
