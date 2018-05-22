package game;

import square.BackwardSquare;
import square.FreezeSquare;
import square.LadderSquare;
import square.SnakeSquare;
import square.Square;

public class Board {
	public static final int SIZE = 101;
	private Square[] squares;

	public Board() {
		this.squares = new Square[SIZE];
		for (int i = 0; i < SIZE; i++) {
			squares[i] = new Square(i);
		}
		squares[squares.length - 1].setGoal(true);
	}

	public void addPiece(Piece piece, int pos) {
		squares[pos].addPiece(piece);
	}

	public void restartPiece(Piece piece) {
		this.squares[getPeicePosition(piece)].removePiece(piece);
		addPiece(piece, 0);
	}

	public void movePiece(Piece piece, int steps) {
		int pos = getPeicePosition(piece);
		this.squares[pos].removePiece(piece);
		int newPos = pos + steps;
		if (newPos >= squares.length) {
			newPos = squares.length - 1;
		}
		addPiece(piece, newPos);
	}

	public int getPeicePosition(Piece piece) {
		for (Square s : squares) {
			if (s.hasPiece(piece)) {
				return s.getNumber();
			}
		}
		return -1;
	}

	public boolean pieceIsAtGoal(Piece piece) {
		return squares[getPeicePosition(piece)].isGoal();
	}

	public boolean hasSpecialSquare(String type, int pos) {
		switch (type) {
		case "Snake":
			return squares[pos] instanceof SnakeSquare ? true : false;
		case "Ladder":
			return squares[pos] instanceof LadderSquare ? true : false;
		case "Freeze":
			return squares[pos] instanceof FreezeSquare ? true : false;
		case "Backward":
			return squares[pos] instanceof BackwardSquare ? true : false;
		default:
			return false;
		}
	}

	public Square[] getSquare() {
		return this.squares;
	}

	public void createSpecialSquares(String type, int block) {
		switch (type) {
		case "Freeze":
			squares[block] = new FreezeSquare(block);
			break;

		case "Backward":
			squares[block] = new BackwardSquare(block);
		default:
			break;
		}

	}

	public void createSpecialSquares(String type, int block, int steps) {
		switch (type) {
		case "Ladder":
			squares[block] = new LadderSquare(block, steps);
			break;

		case "Snake":
			squares[block] = new SnakeSquare(block, steps);
			break;

		default:
			break;
		}

	}

}