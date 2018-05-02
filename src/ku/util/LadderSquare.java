package ku.util;

public class LadderSquare extends Square {

	public LadderSquare(int number, int finalBox) {
		super(number);	
		this.type = "Ladder";
	}
	
	@Override
	public String getType() {
		return this.type;
	}
}
