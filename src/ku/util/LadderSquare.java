package ku.util;

public class LadderSquare extends Square {

	public LadderSquare(int from, int steps) {
		super(from);
		this.type = "Ladder";
		this.steps = steps;
	}
	
	@Override
	public String getType() {
		return this.type;
	}
	
	@Override
	public int getSteps() {
		return this.steps;
	}
}
