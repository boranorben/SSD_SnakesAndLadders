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
		return squares[pos].getType().equals("Ladder") ? true : false;
	}
	
	public boolean hasSnakeSquare(int pos) {
		return squares[pos].getType().equals("Snake") ? true : false;
	}
	
	// method for checking the square's type
	public Square[] getSquare() {
		return this.squares;
	}
	
	public void createSpecialSquares(String type, int block) {
		squares[block] = new FreezeSquare(block);
	}
	
	public void createSpecialSquares(String type, int block, int steps) {
		switch(type) {
			case "Ladder":
				squares[block] = new LadderSquare(block, steps);
				break;
				
			case "Snake":
				squares[block] = new SnakeSquare(block, steps);
				break;
				
			case "Backward":
				squares[block] = new BackwardSquare(block, steps);
				break;

			default:
				break;
		}
		
	}

}
