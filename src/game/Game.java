package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import replay.Replay;
import square.BackwardSquare;
import square.FreezeSquare;
import square.LadderSquare;
import square.SnakeSquare;
import square.Square;

public class Game extends Observable {
	private Player[] players;
	private Die die;
	private Board board;
	private int currentPlayerIndex;
	private boolean ended;
	private int steps;
	private int initialPosition = 0;
	private Player previousPlayer;
	private boolean hasMove = false;

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
		int snakes[][] = { { 17, -10 }, { 54, -20 }, { 62, -43 }, { 64, -4 }, { 87, -51 }, { 93, -20 }, { 95, -20 },
				{ 98, -19 } };
		int freezes[] = { 26, 49, 57, 78, 86 };
		int backwards[] = { 19, 29, 37, 61, 96 };

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
		setMove(false);
		System.out.println(currentPlayerName() + ": freeze? " + currentPlayer().getFreeze());
		System.out.println(nextPlayer().getName() + ": freeze? " + nextPlayer().getFreeze());
		if (!currentPlayer().getFreeze()) {
			currentPlayerMovePiece(steps);
			if (hasFreeze()) {
				System.out.println(currentPlayerName() + " Freeze!");
				setFreeze();
			}
		} else if (currentPlayer().getFreeze() && !nextPlayer().getFreeze()) {
			switchPlayer();
			currentPlayerMovePiece(steps);
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

	public String getSquareType() {
		Square square = board.getSquare()[currentPlayerPosition()];
		if (square instanceof SnakeSquare) {
			return "Snake";
		} else if (square instanceof LadderSquare) {
			return "Ladder";
		} else if (square instanceof FreezeSquare) {
			return "Freeze";
		} else if (square instanceof BackwardSquare) {
			return "Backward";
		} else
			return "Square";
	}

	public void setFreeze() {
		players[currentPlayerIndex].setFreeze(true);
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
		previousPlayer = currentPlayer();
		if (getSquareType().equals("Backward")) {
			this.steps = -steps;
		} else {
			this.steps = steps;
		}
		this.initialPosition = currentPlayerPosition();
		currentPlayer().movePiece(board, steps);
		replay.add(new Replay(currentPlayer(), steps));
		setChanged();
		notifyObservers();
	}

	public void moveSpecial() {
		Square previousSquare = board.getSquare()[board.getPeicePosition(previousPlayer.getPiece())];
		if (previousSquare instanceof SnakeSquare || previousSquare instanceof LadderSquare) {
			setMove(true);
			this.initialPosition = previousSquare.getNumber();
			this.previousPlayer.movePiece(board, previousSquare.getSteps());
			this.steps = previousSquare.getSteps();
			replay.add(new Replay(this.previousPlayer, previousSquare.getSteps()));
			setChanged();
			notifyObservers();
		}
	}
	
	public void setMove(boolean move) {
		this.hasMove = move;
	}
	
	public boolean hasMove() {
		return this.hasMove;
	}

	public Square[] getSquare() {
		return this.board.getSquare();
	}

	public boolean currentPlayersWins() {
		return board.pieceIsAtGoal(currentPlayer().getPiece());
	}

	public String currentPlayerName() {
		return currentPlayer().getName();
	}

	public Player nextPlayer() {
		return players[(currentPlayerIndex + 1) % players.length];
	}

	public void end() {
		ended = true;
	};

	public void switchPlayer() {
		int tmp = (currentPlayerIndex + 1) % players.length;
		if (players[tmp].getFreeze()) {
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

	public boolean hasBackward() {
		return board.hasSpecialSquare("Backward", currentPlayerPosition());
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

	public Player[] getArrayPlayer() {
		return players;
	}

	public int getInitialPosition() {
		return this.initialPosition;
	}

	public String getPlayerPieceFace() {
		return currentPlayer().getPiece().getFace();
	}

	public void switchPlayerPieceFace() {
		currentPlayer().getPiece().switchFace();
	}

	public Player getPreviousPlayer() {
		return this.previousPlayer;
	}
	
	public String getPreviousPlayerName() {
		return getPreviousPlayer().getName();
	}
	
	public int getPreviousPosition() {
		return board.getPeicePosition(getPreviousPlayer().getPiece());
	}
}