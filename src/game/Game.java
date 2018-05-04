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
		this.die = new Die();
		this.board = new Board();
		this.ended = false;
		this.createSpecial();
	}
	
	public void createSpecial() {
		int ladders[][] = {{4, 10}, {9, 22}, {20, 18}, {28, 56}, {40, 19}, {51, 16}, {63, 18}, {71, 20}};
		int snakes[][] = {{17, -10}, {54, -20}, {62, -43}, {64, -4}, {87, -60}, {93, -20}, {95, -20}, {99, -21}};
		int freezes[] = {26, 50, 59, 78, 85};
		
		for(int[] ladder: ladders) {
			board.createSpecialSquares("Ladder", ladder[0], ladder[1]);
		}
		
		for(int[] snake: snakes) {
			board.createSpecialSquares("Snake", snake[0], snake[1]);
		}
		
		for (int freeze: freezes) {
			board.createSpecialSquares("Freeze", freeze);
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
	
	public boolean hasSnake() {
		return board.hasSpecialSquare("Snake", currentPlayerPosition());
	}

	public boolean hasLadder() {
		return board.hasSpecialSquare("Ladder", currentPlayerPosition());
	}
	
	public boolean hasFreeze() {
		return board.hasSpecialSquare("Freeze", currentPlayerPosition());
	}
	
	// print the type of the square
	public void printTypes() {
		for (Square square : board.getSquare()) {
			System.out.println(square.getNumber() + ": " + square.getType() + " to " + (square.getNumber() + square.getSteps()));
		}
	}

	// when current player found special squares
	public void currentPlayerFound() {
		currentPlayer().movePiece(board, board.getSquare()[currentPlayerPosition()].getSteps());
	}
	
	public List<Replay> getReplay() {
		return this.replay;
	}
	
	public void initPlayers(int num){
		this.players = new Player[num];
		for( int i = 0 ; i < num ; i++ ) {
			this.players[i] = new Player("P" + (i+1));
			board.addPiece(players[i].getPiece(), 0);
		}
	}
}
