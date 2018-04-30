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
			// check if the current player are on ladder square
			System.out.println("This is a Ladder?: " + game.hasLadder());
			// if the the current player found the ladder, move the piece to that the end of the ladder
			if (game.hasLadder()) {
				System.out.println("From position " + game.currentPlayerPosition() + " to ");
				game.currentPlayerFoundLadder();
				System.out.print("Position: " + game.currentPlayerPosition() + "\n");
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
