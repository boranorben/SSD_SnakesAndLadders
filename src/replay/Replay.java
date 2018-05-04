package replay;

import game.Player;

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
	
	public Player getPlayer() {
		return this.player;
	}
}
