package ku.util;

public class LadderSquare extends Square {
	private String type = "LadderSquare";

	public LadderSquare(int number) {
		super(number);
	}
	
	@Override
	public String getType() {
		return this.type;
	}

}
