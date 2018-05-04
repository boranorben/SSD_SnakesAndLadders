package game;

import java.util.Random;

public class Die {
	public static final int MAX_FACE = 6;
	private int face;

	public Die() {
		face = 1;
	}
	
	public void roll() {
		Random random = new Random();
		face = random.nextInt(MAX_FACE) + 1;
	}
	
	public int getFace() {
		return face;
	}
}
