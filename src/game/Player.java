package game;

public class Player {
	private String name;
	private Piece piece;
	private boolean freeze;
	
	public Player(String name) {
		this.name = name;
		this.piece = new Piece();
		this.freeze = false;
	}
	
	public int roll(Die die) {
		die.roll();
		return die.getFace();
	}
	
	public void movePiece(Board board, int steps) {
		System.out.println(this.name);
		board.movePiece(piece, steps);
	}
	
	public String getName() {
		return this.name;
	}
	
	public Piece getPiece() {
		return this.piece;
	}
	
	public boolean getFreeze() {
		return this.freeze;
	}
	
	public void setFreeze(boolean freeze) {
		this.freeze = freeze;
	}
}
