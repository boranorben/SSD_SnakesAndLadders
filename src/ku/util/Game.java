package ku.util;

public class Game {
	private Player[] players;
	private Die die;
	private Board board;
	private int currentPlayerIndex;
	private boolean ended;
	
	public Game() {
		this.players = new Player[2];
		this.players[0] = new Player("P1");
		this.players[1] = new Player("P2");
		this.die = new Die();
		this.board = new Board();
		this.ended = false;
		
		board.addPiece(players[0].getPiece(), 0);
		board.addPiece(players[1].getPiece(), 0);
	}
	
	public boolean isEnded() { return ended; }
	
	public Player currentPlayer() { return players[currentPlayerIndex]; }
	
	public int currentPlayerPosition() { return board.getPeicePosition(currentPlayer().getPiece()); }
	
	public int currentPlayerRollDice() { return currentPlayer().roll(die); }
	
	public void currentPlayerMovePiece(int steps) { currentPlayer().movePiece(board, steps);}
	
	public boolean currentPlayersWins() { return board.pieceIsAtGoal(currentPlayer().getPiece()); }
	
	public String currentPlayerName() { return currentPlayer().getName(); }
	
	public void end() { ended = true; };
	
	public void switchPlayer() { currentPlayerIndex = (currentPlayerIndex+1) % players.length; };
	
	// check if the current player are on ladder square
	public boolean hasLadder() {
		return board.hasLadderSquare(currentPlayerPosition());
	}
	
	// print the type of the square
	public void printTypes() {
		for (Square square : board.getSquare()) {
			System.out.println(square.getNumber() + ": " + square.getType() + " to " + square.getSteps());
		}
	}
	
	// when current player found ladder
	public void currentPlayerFoundLadder() {
		currentPlayer().movePiece(board, board.getSquare()[currentPlayerPosition()].getSteps());
	}

}
