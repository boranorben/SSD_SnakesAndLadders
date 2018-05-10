package game;

public class Piece {
	private String face = "right";
	
	public String getFace() {
		return this.face;
	}
	
	public void switchFace() {
		if(face == "right") {
			face = "left";
		} else {
			face = "right";
		}
	}
}
