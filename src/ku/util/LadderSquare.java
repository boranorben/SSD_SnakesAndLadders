package ku.util;

public class LadderSquare extends Square {

	public LadderSquare(int from, int to) {
		super(from);
		this.type = "Ladder";
		this.toNumber = to;
	}
	
	@Override
	public String getType() {
		return this.type;
	}
	
	@Override
	public int getToNumber() {
		return this.toNumber;
	}
}
