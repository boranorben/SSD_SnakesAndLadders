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
		// add snake and ladder square here
		addSnakeSquare();
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
		return squares[pos].getType() == "Ladder" ? true : false;
	}
	
	public boolean hasSnakeSquare(int pos) {
		return squares[pos].getType() == "Snake" ? true : false;
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
	
	public void addSnakeSquare() {
		squares[17] = new SnakeSquare(17, -10);
		squares[54] = new SnakeSquare(54, -20);
		squares[62] = new SnakeSquare(62, -43);
		squares[64] = new SnakeSquare(64, -4);
		squares[87] = new SnakeSquare(87, -60);
		squares[93] = new SnakeSquare(93, -20);
		squares[95] = new SnakeSquare(95, -20);
		squares[99] = new SnakeSquare(99, -21);
	}
	
	// method for checking the square's type
	public Square[] getSquare() {
		return this.squares;
	}

}
