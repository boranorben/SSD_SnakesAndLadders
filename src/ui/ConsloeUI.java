package ui;

import java.util.Scanner;

import game.Game;

public class ConsloeUI {
	private Scanner scanner = new Scanner(System.in);
	
	public void start(Game game) {
		game.initPlayers(2);
		while(!game.isEnded()) {
			System.out.println("-------------");
			System.out.println(game.currentPlayer().getName() + "'s turn");
			System.out.println("Position: " + game.currentPlayerPosition());
			System.out.println("Please hit enter to roll a die.");
			scanner.nextLine();
			int face = game.currentPlayerRollDice();
			System.out.println("Dice face = " + face);
			game.currentPlayerMovePiece(face);
			game.setFreeze(game.currentPlayerPosition());
			System.out.println("Position: " + game.currentPlayerPosition());
			// check if the current player are on special square
			System.out.println("Square type: " + game.getSquareType());
			// if the the current player found the ladder, move the piece to that the end of the ladder
			if (game.hasLadder() || game.hasSnake()) {
				System.out.print("From position " + game.currentPlayerPosition() + " to ");
				game.currentPlayerFound();
				System.out.println("Position: " + game.currentPlayerPosition());
			}
			// freeze is not completed
			if (game.hasBackward()) {
				System.out.println("Current player found Backward! need to roll agian!");
				scanner.nextLine();
				int backFace = game.currentPlayerRollDice();
				System.out.println("Dice face = " + -backFace);
				System.out.print("From position " + game.currentPlayerPosition() + " to ");
				game.currentPlayerFound(backFace);
				System.out.println("Position: " + game.currentPlayerPosition());
			}
			if (game.currentPlayersWins()) {
				System.out.println(game.currentPlayerName() + "Win!");
				game.end();
			} else {
				game.switchPlayer();
			}
		}
//		game.printTypes();
	}
	
	public static void main(String[] args) {
		new ConsloeUI().start(new Game());
	}

}
