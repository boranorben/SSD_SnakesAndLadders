package square;

import java.util.ArrayList;
import java.util.List;

import game.Piece;

public class Square {
	private List<Piece> pieces;
	private int number;
	private boolean goal;
	
	protected String type = "Square";
	protected int steps;
	
	public Square(int number) {
		this.pieces = new ArrayList<Piece>();
		this.number = number;
		this.goal = false;
		this.steps = 0;
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
	
	public int getSteps() {
		return this.steps;
	}
	
}
