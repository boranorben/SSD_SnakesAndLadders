package square;

public class SnakeSquare extends Square {

	public SnakeSquare(int number, int steps) {
		super(number);
		this.type = "Snake";
		this.steps = steps;
	}
}
