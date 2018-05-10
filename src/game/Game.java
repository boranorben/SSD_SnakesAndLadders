package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import replay.Replay;
import square.Square;

public class Game extends Observable{
	private Player[] players;
	private Die die;
	private Board board;
	private int currentPlayerIndex;
	private boolean ended;
	private int steps;
	private int initialPosition = 0;

	private List<Replay> replay = new ArrayList<Replay>();
	private List<Replay> tmp;

	public Game() {
		this.die = new Die();
		this.board = new Board();
		this.ended = false;
		this.createSpecial();
	}

	public void initPlayers(int num) {
		this.players = new Player[num];
		for (int i = 0; i < num; i++) {
			this.players[i] = new Player("Player" + (i + 1));
			board.addPiece(players[i].getPiece(), 0);
		}
	}

	public void createSpecial() {
		int ladders[][] = { { 1, 37 }, { 4, 10 }, { 9, 22 }, { 21, 21 }, { 28, 56 }, { 51, 16 }, { 72, 19 },
				{ 80, 19 } };
		int snakes[][] = { { 17, -10 }, { 54, -20 }, { 62, -44 }, { 64, -4 }, { 87, -61 }, { 92, -19 }, { 95, -20 },
				{ 98, -19 } };
		int freezes[] = { 26, 50, 59, 78, 85 };
		int backwards[] = { 19, 29, 35, 61, 96 };

		for (int[] ladder : ladders) {
			board.createSpecialSquares("Ladder", ladder[0], ladder[1]);
		}

		for (int[] snake : snakes) {
			board.createSpecialSquares("Snake", snake[0], snake[1]);
		}

		for (int freeze : freezes) {
			board.createSpecialSquares("Freeze", freeze);
		}

		for (int backward : backwards) {
			board.createSpecialSquares("Backward", backward);
		}
	}

	public void gameLogic(int steps) {
		System.out.println("--------------------------------");
		if (!currentPlayer().getFreeze()) {
			System.out.println(currentPlayerName() + "'s positon: " + currentPlayerPosition());
			System.out.println("Dice faces: " + steps);
			currentPlayerMovePiece(steps);
			System.out.println(currentPlayerName() + "'s positon: " + currentPlayerPosition());
			if (getSquareType() != "Square") {
				if (hasSnake() || hasLadder()) {
					System.out.println(currentPlayerName() + " found " + getSquareType());
					System.out.print(currentPlayerName() + " go to ");
					currentPlayerFound();
					System.out.println(currentPlayerPosition());
				} else if (hasFreeze()) {
					System.out.println(currentPlayerName() + " Freeze!");
					setFreeze();
				}
			}
		} else {
			System.out.println(currentPlayerName() + "'s still Frozen");
		}
		if (currentPlayersWins()) {
			System.out.println(currentPlayerName() + "'s win!");
			end();
		} else {
			if (!hasBackward()) {
				switchPlayer();
			}
		}
	}

	public void setFreeze() {
		if (hasFreeze()) {
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
		this.initialPosition = currentPlayerPosition();
		currentPlayer().movePiece(board, steps);
		Square currentSquareType = board.getSquare()[currentPlayerPosition()];
		replay.add(new Replay(currentPlayer(), steps + currentSquareType.getSteps()));
		this.steps = steps + currentSquareType.getSteps();
		currentPlayer().movePiece(board, currentSquareType.getSteps());
		setChanged();
		notifyObservers();
	}

	public boolean currentPlayersWins() {
		return board.pieceIsAtGoal(currentPlayer().getPiece());
	}

	public String currentPlayerName() {
		return currentPlayer().getName();
	}
	
	public String nextPlayerName() {
		return players[(currentPlayerIndex + 1) % players.length].getName();
	}
	
	public void end() {
		ended = true;
	};

	public void switchPlayer() {
		int tmp = (currentPlayerIndex + 1) % players.length;
		if (players[tmp].getFreeze() == true) {
			players[tmp].setFreeze(false);
		} else {
			currentPlayerIndex = tmp;
		}
	};

	public String getSquareType() {
		return board.getSquare()[currentPlayerPosition()].getType() == "Square" ? "Square"
				: board.getSquare()[currentPlayerPosition()].getType();
	}

	public boolean hasSnake() {
		return board.hasSpecialSquare("Snake", currentPlayerPosition());
	}

	public boolean hasLadder() {
		return board.hasSpecialSquare("Ladder", currentPlayerPosition());
	}

	public boolean hasFreeze() {
		return board.hasSpecialSquare("Freeze", currentPlayerPosition());
	}

	public boolean hasBackward() {
		return board.hasSpecialSquare("Backward", currentPlayerPosition());
	}

	// print the type of the square
	public void printTypes() {
		for (Square square : board.getSquare()) {
			System.out.println(
					square.getNumber() + ": " + square.getType() + " to " + (square.getNumber() + square.getSteps()));
		}
	}

	public void currentPlayerFound() {
		currentPlayer().movePiece(board, board.getSquare()[currentPlayerPosition()].getSteps());
	}

	public void currentPlayerFound(int backSteps) {
		currentPlayer().movePiece(board, -backSteps);
	}

	public void showReplay() {
		for (Player player : players) {
			board.restartPiece(player.getPiece());
		}
		setTmp(this.replay);
		doReplay(this.tmp);
	}

	public void setTmp(List<Replay> tmp) {
		this.tmp = tmp;
	}

	public void doReplay(List<Replay> tmp) {
		if (tmp.size() == 0) {
			return;
		}
		tmp.get(0).getPlayer().movePiece(board, tmp.get(0).getSteps());
		if (tmp.size() == 1) {
			tmp = new ArrayList<Replay>();
			return;
		} else {
			tmp = tmp.subList(1, tmp.size());
			doReplay(tmp);
		}
	}
	
	public int getSteps() {
		return this.steps;
	}

	public void restartGame() {
		for (Player player : players) {
			board.restartPiece(player.getPiece());
		}
		replay.clear();
	}
	
	public Player[] getArrayPlayer(){
		return players;
	}

	public String getPlayerPieceFace() {
		return players[currentPlayerIndex].getPiece().getFace();
	}
	
	public void setPlayerPieceFace(String face) {
		players[currentPlayerIndex].getPiece().setFace(face);
	}
	
	public int getInitialPosition() {
		return this.initialPosition;
	}
}