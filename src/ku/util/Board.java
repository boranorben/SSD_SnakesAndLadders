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
		squares[1] = new LadderSquare(1);
		squares[5] = new LadderSquare(5);
		squares[10] = new LadderSquare(10);
		squares[24] = new LadderSquare(24);
		squares[39] = new LadderSquare(39);
		squares[51] = new LadderSquare(51);
		squares[58] = new LadderSquare(58);
		squares[75] = new LadderSquare(75);
		squares[82] = new LadderSquare(82);
	}
	
	// method for checking the square's type
	public Square[] getSquare() {
		return this.squares;
	}

}
