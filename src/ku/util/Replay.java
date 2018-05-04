package ku.util;

public class Replay {
	private Player player;
	private int steps;
	
	public Replay(Player player, int steps) {
		this.player = player;
		this.steps = steps;
	}
	
	public int getSteps() {
		return this.steps;
	}
	
	public String getPlayerName() {
		return this.player.getName();
	}
}
