package ku.util;

public class LadderSquare extends Square {

	public LadderSquare(int number) {
		super(number);
		this.type = "Ladder";
	}
	
	@Override
	public String getType() {
		return this.type;
	}
}
