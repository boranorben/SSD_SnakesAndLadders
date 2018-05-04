package square;

public class LadderSquare extends Square {
		
	public LadderSquare(int from, int steps) {
		super(from);
		this.type = "Ladder";
		this.steps = steps;
	}
}
