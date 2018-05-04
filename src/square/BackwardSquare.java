package square;

public class BackwardSquare extends Square{

	public BackwardSquare(int number, int steps) {
		super(number);
		this.type = "Backward";
		this.steps = -steps;
	}
}
