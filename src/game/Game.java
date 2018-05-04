package game;

import java.util.ArrayList;
import java.util.List;

import replay.Replay;
import square.Square;

public class Game {
	private Player[] players;
	private Die die;
	private Board board;
	private int currentPlayerIndex;
	private boolean ended;
	private List<Replay> replay = new ArrayList<Replay>();	

	public Game() {
		this.players = new Player[2];
		this.players[0] = new Player("P1");
		this.players[1] = new Player("P2");
		this.die = new Die();
		this.board = new Board();
		this.ended = false;
		
		//Create all special tiles
		this.createSpecial();

		board.addPiece(players[0].getPiece(), 0);
		board.addPiece(players[1].getPiece(), 0);
	}
	
	public void createSpecial() {
		int ladders[][] = {{4, 10}, {9, 22}, {20, 18}, {28, 56}, {40, 19}, {51, 16}, {63, 18}, {71, 20}};
		int snakes[][] = {{17, -10}, {54, -20}, {62, -43}, {64, -4}, {87, -60}, {93, -20}, {95, -20}, {99, -21}};
		
		for(int[] ladder: ladders) {
			board.createSpecialSquares("Ladder", ladder[0], ladder[1]);
		}
		
		for(int[] snake: snakes) {
			board.createSpecialSquares("Snake", snake[0], snake[1]);
		}
	}
	
	public void setFreeze(int block) {
		if(board.getSquare()[block].getType() == "Freeze") {
			players[currentPlayerIndex].setFreeze(true);
		}
	}

	public boolean isEnded() {
		return ended;
	}

	public Player currentPlayer() {
		return players[currentPlayerIndex];
	}

	public int currentPlayerPosition() {
		return board.getPeicePosition(currentPlayer().getPiece());
	}

	public int currentPlayerRollDice() {
		return currentPlayer().roll(die);
	}

	public void currentPlayerMovePiece(int steps) {
		replay.add(new Replay(currentPlayer(), steps));
		currentPlayer().movePiece(board, steps);
	}

	public boolean currentPlayersWins() {
		return board.pieceIsAtGoal(currentPlayer().getPiece());
	}

	public String currentPlayerName() {
		return currentPlayer().getName();
	}

	public void end() {
		ended = true;
	};
	
	public void switchPlayer() {
		int tmp = (currentPlayerIndex + 1) % players.length;
		if(players[tmp].getFreeze() == true) {
			players[tmp].setFreeze(false);
		} else {
			currentPlayerIndex = tmp;
		}
	};

	// check if the current player is on snake square
	public boolean hasLadder() {
		return board.hasLadderSquare(currentPlayerPosition());
	}
	
	// check if the current player is on snake square
	public boolean hasSnake() {
		return board.hasSnakeSquare(currentPlayerPosition());
	}

	// print the type of the square
	public void printTypes() {
		for (Square square : board.getSquare()) {
			System.out.println(square.getNumber() + ": " + square.getType() + " to " + (square.getNumber() + square.getSteps()));
		}
	}

	// when current player found ladder or snake
	public void currentPlayerFound() {
		currentPlayer().movePiece(board, board.getSquare()[currentPlayerPosition()].getSteps());
	}
	
	public List<Replay> getReplay() {
		return this.replay;
	}
}
