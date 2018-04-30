package ku.util;

import java.util.ArrayList;
import java.util.List;

public class Square {
	private List<Piece> pieces;
	private int number;
	private boolean goal;
	
	protected String type = "SQUARE";
	
	public Square(int number) {
		this.pieces = new ArrayList<Piece>();
		this.number = number;
		this.goal = false;
	}

	public void addPiece(Piece piece) {
		this.pieces.add(piece);
	}
	
	public void removePiece(Piece piece) {
		this.pieces.remove(piece);
	}
	
	public boolean hasPiece(Piece piece) {
		return this.pieces.contains(piece);
	}
	
	public void setGoal(boolean goal) {
		this.goal = goal;
	}
	
	public boolean isGoal() {
		return this.goal;
	}
	
	public int getNumber() {
		return this.number;
	}
	
	public String getType() {
		return this.type;
	}
	

}
