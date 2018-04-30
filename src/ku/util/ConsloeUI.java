package ku.util;

import java.util.Scanner;

public class ConsloeUI {
	private Scanner scanner = new Scanner(System.in);
	
	public void start(Game game) {
		while(!game.isEnded()) {
			System.out.println("-------------");
			System.out.println(game.currentPlayer().getName() + "'s turn");
			System.out.println("Position: " + game.currentPlayerPosition());
			System.out.println("Please hit enter to roll a die.");
			scanner.nextLine();
			int face = game.currentPlayerRollDice();
			System.out.println("Dice face = " + face);
			game.currentPlayerMovePiece(face);
			System.out.println("Position: " + game.currentPlayerPosition());
			if (game.currentPlayersWins()) {
				System.out.println(game.currentPlayerName() + "Win!");
				game.end();
			} else {
				game.switchPlayer();
			}
		}
	}
	
	public static void main(String[] args) {
		new ConsloeUI().start(new Game());
	}

}
