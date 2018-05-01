package ku.util;

public class Board {
	public static final int SIZE = 100;
	private Square[] squares;
	
	public Board() {
		this.squares = new Square[SIZE];
		for (int i = 0; i < SIZE; i++) {
			squares[i] = new Square(i);
		}
		squares[squares.length-1].setGoal(true);
		// add ladder square in square
		addLadderSquare();

	}
	
	public void addPiece(Piece piece, int pos) {
		squares[pos].addPiece(piece);
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
	
	public boolean hasLadderSquare(int pos) {
		return squares[pos].getType() == "SQUARE" ? false : true;
	}
	
	public void addLadderSquare() {
		squares[4] = new LadderSquare(4, 10);
		squares[9] = new LadderSquare(9, 22);
		squares[20] = new LadderSquare(20, 18);
		squares[28] = new LadderSquare(28, 56);
		squares[40] = new LadderSquare(40, 19);
		squares[51] = new LadderSquare(51, 16);
		squares[63] = new LadderSquare(63, 18);
		squares[71] = new LadderSquare(71, 20);
	}
	
	// method for checking the square's type
	public Square[] getSquare() {
		return this.squares;
	}

}
