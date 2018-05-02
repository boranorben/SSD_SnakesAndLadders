package ku.util;

public class SnakeSquare extends Square {

	public SnakeSquare(int number, int steps) {
		super(number);
		this.type = "Snake";
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
