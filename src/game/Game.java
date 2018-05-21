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
	private int currentPlayerIndex = 0;
	private boolean ended;
	private int steps;
	private Thread moveThread;
	private Thread replayThread;
	private boolean backwards = false;
	private int initialPosition = 0;
	private boolean hasMove = false;
	private boolean isMoveEnd = false;
	private boolean replayCheck = false;

	private List<Replay> replay = new ArrayList<Replay>();
	private boolean replayMode;
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

	public void reset() {
		currentPlayerIndex = 0;
		die = new Die();
		board = new Board();
		ended = false;
		replayMode = false;

		for (int i = 0; i < players.length; i++) {
			players[i] = new Player("P" + (i + 1));
			board.addPiece(players[i].getPiece(), 0);
		}
		createSpecial();
	}

	public synchronized void gameLogic(int newSteps) {
		setMove(false);
		isMoveEnd = false;
		replayCheck = false;
		System.out.println("--------------------------------");
		if (isAllFreeze(players))
			currentPlayer().setFreeze(false);

		if (!currentPlayer().getFreeze() && !ended) {
			if (getBackward()) {
				this.steps = -newSteps;
			} else {
				this.steps = newSteps;
			}
			initialPosition = currentPlayerPosition();
			moveThread = new Thread(new Runnable() {

				@Override
				public void run() {
					if (!replayMode) {
						replay.add(new Replay(currentPlayer(), newSteps));
					}
					if (getBackward()) {
						for (int i = steps; i < 0; i++) {
							initialPosition--;
							setChanged();
							notifyObservers();
							waitFor(500);
						}
						backwards = false;
					} else {
						for (int i = 0; i < newSteps; i++) {
							initialPosition++;
							setChanged();
							notifyObservers();
							waitFor(500);
						}
					}

					currentPlayer().movePiece(board, steps);
					waitFor(800);
					System.out.println(currentPlayerName() + "'s positon: " + currentPlayerPosition());
					System.out.println("Dice faces: " + steps);
					System.out.println(currentPlayerName() + "'s positon: " + currentPlayerPosition());

					if (hasLadder()) {
						for (int i = 0; i < getSpecialSteps(); i++) {
							initialPosition++;
							setChanged();
							notifyObservers();
							waitFor(500);
						}
					} else if (hasSnake()) {
						backwards = true;
						for (int i = getSpecialSteps(); i < 0; i++) {
							initialPosition--;
							setChanged();
							notifyObservers();
							waitFor(500);
						}
						backwards = false;
					}

					currentPlayer().movePiece(board, getSpecialSteps());

					if (hasFreeze()) {
						System.out.println(currentPlayerName() + " Freeze!");
						setFreeze();
					}

					setMove(true);
					isMoveEnd = true;
					setChanged();
					notifyObservers();

					if (currentPlayersWins()) {
						System.out.println(currentPlayerName() + "'s win!");
						end();
					}

					if (!hasBackward()) {
						switchPlayer();
					} else {
						backwards = true;
					}
					replayCheck = true;

				}
			});
			moveThread.start();

		}

	}

	public boolean isMoveEnd() {
		return this.isMoveEnd;
	}

	public void doReplay() {
		replayMode = true;
		initPlayers(players.length);
		int size = tmp.size();
		replayThread = new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < size; i++) {
					gameLogic(tmp.get(0).getSteps());
					tmp = tmp.subList(1, tmp.size());
					while (!replayCheck) {
						System.out.print("");
					}
				}
				replayMode = false;
			}
		});
		replayThread.start();
	}

	public void notifyT() {
		replayThread.start();
	}

	public void sleepT() {
		try {
			replayThread.wait();
		} catch (InterruptedException e) {
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

	public void setMove(boolean move) {
		this.hasMove = move;
	}

	public boolean hasMove() {
		return this.hasMove;
	}

	private void waitFor(long delayed) {
		try {
			Thread.sleep(delayed);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public int getSpecialSteps() {
		return getSquare()[currentPlayerPosition()].getSteps();
	}

	public boolean getBackward() {
		return this.backwards;
	}

	public boolean getReplayMode() {
		return this.replayMode;
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
	}

	public void switchPlayer() {
		int tmp = (currentPlayerIndex + 1) % players.length;
		if (players[tmp].getFreeze()) {
			players[tmp].setFreeze(false);
		} else {
			currentPlayerIndex = tmp;
		}
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

	public void currentPlayerFound() {
		currentPlayer().movePiece(board, board.getSquare()[currentPlayerPosition()].getSteps());
	}

	public void currentPlayerFound(int backSteps) {
		currentPlayer().movePiece(board, -backSteps);
	}

	public void showReplay() {
		reset();
		setTmp(this.replay);
		doReplay();
	}

	public void setTmp(List<Replay> tmp) {
		this.tmp = tmp;
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

	public boolean isAllFreeze(Player[] players) {
		for (Player p : players) {
			if (!p.getFreeze())
				return false;
		}
		return true;
	}
}