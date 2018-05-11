package ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import game.Game;
import game.Player;

public class GUI implements Observer {

	private Game game;
	private JFrame homeFrame, gameFrame;
	private JLabel title, rollResult, boardPic, coverPic, status, currentPlayer, player1_Pin, player2_Pin, player3_Pin,
			player4_Pin;
	private JButton player2_Button, player3_Button, player4_Button, die, restartButton, replayButton;
	private ImageIcon player1_icon, player2_icon, player3_icon, player4_icon;
	private int diceFace = 0;

	public GUI(Game game) {
		this.game = game;
		homeFrame = new JFrame("Snakes and Ladders Game");
		gameFrame = new JFrame("Snakes and Ladders Game");
		homeFrame.setResizable(false);
		gameFrame.setResizable(false);
		homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initComponent();
		game.addObserver(this);
	}

	public void initComponent() {
		title = new JLabel(new ImageIcon(getClass().getResource("./../images/titlePic.png")));
		rollResult = new JLabel(new ImageIcon(getClass().getResource("./../images/dice0.png")));
		boardPic = new JLabel(new ImageIcon(getClass().getResource("./../images/boardPic.jpg")));
		coverPic = new JLabel(new ImageIcon(getClass().getResource("./../images/coverPic.jpg")));
		status = new JLabel();
		currentPlayer = new JLabel();

		player1_icon = new ImageIcon(getClass().getResource("./../images/player1.png"));
		player2_icon = new ImageIcon(getClass().getResource("./../images/player2.png"));
		player3_icon = new ImageIcon(getClass().getResource("./../images/player3.png"));
		player4_icon = new ImageIcon(getClass().getResource("./../images/player4.png"));

		player1_Pin = new JLabel(player1_icon);
		player2_Pin = new JLabel(player2_icon);
		player3_Pin = new JLabel(player3_icon);
		player4_Pin = new JLabel(player4_icon);

		setFont(status);
		setFont(currentPlayer);

		player2_Button = new JButton();
		player3_Button = new JButton();
		player4_Button = new JButton();
		die = new JButton();
		restartButton = new JButton();
		replayButton = new JButton();

		player2_Button.setIcon(new ImageIcon(getClass().getResource("./../images/2Player.png")));
		player3_Button.setIcon(new ImageIcon(getClass().getResource("./../images/3Player.png")));
		// player4_Button.setIcon(new
		// ImageIcon(getClass().getResource("./../images/4Player.png")));
		die.setIcon(new ImageIcon(getClass().getResource("./../images/die.png")));
		restartButton.setIcon(new ImageIcon(getClass().getResource("./../images/restart.png")));
		replayButton.setIcon(new ImageIcon(getClass().getResource("./../images/replay.png")));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;

		setTransparentBackground(player2_Button);
		setTransparentBackground(player3_Button);
		setTransparentBackground(player4_Button);
		setTransparentBackground(die);
		setTransparentBackground(restartButton);
		setTransparentBackground(replayButton);

		homeFrame.setContentPane(coverPic);
		homeFrame.setLayout(new GridBagLayout());
		homeFrame.add(player2_Button, gbc);
		homeFrame.add(player3_Button, gbc);
		homeFrame.add(player4_Button, gbc);

		JPanel boardPanel = new JPanel();
		boardPanel.add(boardPic);
		boardPic.setLayout(null);
		boardPic.add(player1_Pin);

		int x_initialPos = -50, y_initialPos = 450;
		movePlayer(player1_Pin, x_initialPos, y_initialPos);
		boardPic.add(player1_Pin);
		movePlayer(player2_Pin, x_initialPos, y_initialPos);
		boardPic.add(player2_Pin);
		movePlayer(player3_Pin, x_initialPos, y_initialPos);
		boardPic.add(player3_Pin);
		movePlayer(player1_Pin, x_initialPos, y_initialPos);
		boardPic.add(player4_Pin);

		JPanel statusPanel = new JPanel();
		statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.Y_AXIS));
		statusPanel.add(currentPlayer);
		statusPanel.add(status);

		JPanel ManagerPanel = new JPanel();
		ManagerPanel.setLayout(new BoxLayout(ManagerPanel, BoxLayout.Y_AXIS));
		ManagerPanel.add(restartButton);
		ManagerPanel.add(replayButton);

		JPanel controllerPanel = new JPanel();
		controllerPanel.add(ManagerPanel);
		controllerPanel.add(die);
		controllerPanel.add(rollResult);
		controllerPanel.add(statusPanel);

		gameFrame.add(title, BorderLayout.NORTH);
		gameFrame.add(boardPanel, BorderLayout.CENTER);
		gameFrame.add(controllerPanel, BorderLayout.SOUTH);

		gameFrame.pack();
		homeFrame.pack();

		updateCurrentPlayer();

		player2_Button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				changeFrame(2);
			}
		});

		player3_Button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				changeFrame(3);
			}
		});

		player4_Button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				changeFrame(4);
			}
		});

		die.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (game.isEnded()) {
					JOptionPane.showMessageDialog(null, "Game's alrealdy ended!");
					die.setEnabled(false);
				} else {
					diceFace = game.currentPlayerRollDice();
					switch (diceFace) {
					case 1:
						rollResult.setIcon(new ImageIcon(getClass().getResource("./../images/dice1.png")));
						break;
					case 2:
						rollResult.setIcon(new ImageIcon(getClass().getResource("./../images/dice2.png")));
						break;
					case 3:
						rollResult.setIcon(new ImageIcon(getClass().getResource("./../images/dice3.png")));
						break;
					case 4:
						rollResult.setIcon(new ImageIcon(getClass().getResource("./../images/dice4.png")));
						break;
					case 5:
						rollResult.setIcon(new ImageIcon(getClass().getResource("./../images/dice5.png")));
						break;
					case 6:
						rollResult.setIcon(new ImageIcon(getClass().getResource("./../images/dice6.png")));
						break;
					default:
						break;
					}
					game.gameLogic(diceFace);
				}

			}
		});

		restartButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				game.restartGame();
			}
		});

		replayButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				game.showReplay();
			}
		});

		gameFrame.setVisible(false);
	}

	public void setVisible() {
		homeFrame.setVisible(true);
	}

	public void setTransparentBackground(JButton button) {
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
	}

	public void changeFrame(int numPlayers) {
		homeFrame.setVisible(false);
		gameFrame.setVisible(true);
		game.initPlayers(numPlayers);
	}

	public void setFont(JLabel label) {
		InputStream is = GUI.class.getResourceAsStream("./../fonts/HoboStd.otf");
		try {
			Font font = Font.createFont(Font.TRUETYPE_FONT, is);
			Font sizedFont = font.deriveFont(12f);
			label.setFont(font);
			label.setFont(sizedFont);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
	}

	public Player changePlayerType(JLabel player) {
		if (player == player1_Pin)
			return game.getArrayPlayer()[0];
		else if (player == player2_Pin)
			return game.getArrayPlayer()[1];
		else if (player == player3_Pin)
			return game.getArrayPlayer()[2];
		else if (player == player4_Pin)
			return game.getArrayPlayer()[3];
		return null;
	}

	public JLabel findPlayerName() {
		switch (game.currentPlayerName()) {
		case "Player1":
			return player1_Pin;
		case "Player2":
			return player2_Pin;
		case "Player3":
			return player3_Pin;
		case "Player4":
			return player4_Pin;
		default:
			break;
		}
		return null;
	}

	public void movePlayer(JLabel player, int x, int y) {
		player.setBounds(x, y, 50, 50);
	}

	public void updateCurrentPlayer() {
		if (game.getArrayPlayer() == null) {
			currentPlayer.setIcon(player1_icon);
			currentPlayer.setText("Current Player: Player 1");
		} else if (game.currentPlayersWins() && game.getSquareType().equals("Backward")) {
			currentPlayer.setIcon(null);
			currentPlayer.setText("");
		} else if (!game.nextPlayer().getFreeze()) {
			switch (game.getArrayPlayer().length) {
			case 2:
				if (game.currentPlayerName().equals("Player1")) {
					currentPlayer.setIcon(player2_icon);
				} else {
					currentPlayer.setIcon(player1_icon);
				}
				break;
			case 3:
				if (game.currentPlayerName().equals("Player1")) {
					currentPlayer.setIcon(player2_icon);
				} else if (game.currentPlayerName().equals("Player2")) {
					currentPlayer.setIcon(player3_icon);
				} else {
					currentPlayer.setIcon(player1_icon);
				}
				break;
			case 4:
				if (game.currentPlayerName().equals("Player1")) {
					currentPlayer.setIcon(player2_icon);
				} else if (game.currentPlayerName().equals("Player2")) {
					currentPlayer.setIcon(player3_icon);
				} else if (game.currentPlayerName().equals("Player3")) {
					currentPlayer.setIcon(player4_icon);
				} else {
					currentPlayer.setIcon(player1_icon);
				}
				break;
			default:
				break;
			}
			currentPlayer.setText("Current Player: " + game.nextPlayer().getName());
		}
		currentPlayer.setHorizontalTextPosition(JLabel.CENTER);
		currentPlayer.setVerticalTextPosition(JLabel.BOTTOM);
	}

	public void updateStatus() {
		String squareType = game.getSquareType();
		String currentPlayerName = game.currentPlayerName();
		if (game.currentPlayersWins()) {
			status.setText(currentPlayerName + "WINS!");
			switch (currentPlayerName) {
			case "Player1":
				status.setIcon(player1_icon);
				break;
			case "Player2":
				status.setIcon(player2_icon);
				break;
			case "Player3":
				status.setIcon(player3_icon);
				break;
			case "Player4":
				status.setIcon(player4_icon);
				break;
			default:
				break;
			}
		}
		if (game.nextPlayer().getFreeze()) {
			status.setText(game.nextPlayer().getName() + "'s still FREEZE!");
		}
		if (!squareType.equals("Square")) {
			switch (squareType) {
			case "Backward":
				status.setText(currentPlayerName + " founds Backward, Roll again and move backward!");
				break;
			case "Freeze":
				status.setText(currentPlayerName + " found Freeze, Skip 1 turn!");
			default:
				status.setText(currentPlayerName + " founds " + squareType);
				break;
			}
		}
		status.setHorizontalTextPosition(JLabel.CENTER);
		status.setVerticalTextPosition(JLabel.BOTTOM);
	}

	@Override
	public void update(Observable o, Object arg) {
		if(game.getSteps() < 0) {
			moveBackwards(game.getInitialPosition(), game.getSteps());
		} else {
			moveForward(game.getInitialPosition(), game.getSteps());	
		}
		updateCurrentPlayer();
		updateStatus();
	}

	public void moveForward(int position, int steps) {
		JLabel currentLabel = findPlayerName();
		for (int i = 0; i < steps; i++) {
			if (position % 10 == 0 && position != 0) {
				movePlayer(currentLabel, currentLabel.getX(), currentLabel.getY() - 50);
				game.switchPlayerPieceFace();
			} else {
				movePlayer(currentLabel, currentLabel.getX(), currentLabel.getY());
				if (game.getPlayerPieceFace() == "right") {
					currentLabel.setLocation(currentLabel.getX()+50, currentLabel.getY());
				} else if (game.getPlayerPieceFace() == "left") {
					currentLabel.setLocation(currentLabel.getX()-50, currentLabel.getY());
				}
			}
			position++;
		}
	}
	
	public void moveBackwards(int position, int steps) {
		JLabel currentLabel = findPlayerName();
		for (int i = 0; i > steps; i--) {
			if ((position-1) % 10 == 0 && position != 0) {
				movePlayer(currentLabel, currentLabel.getX(), currentLabel.getY() + 50);
				game.switchPlayerPieceFace();
			} else {
				movePlayer(currentLabel, currentLabel.getX(), currentLabel.getY());
				if (game.getPlayerPieceFace() == "right") {
					currentLabel.setLocation(currentLabel.getX()-50, currentLabel.getY());
				} else if (game.getPlayerPieceFace() == "left") {
					currentLabel.setLocation(currentLabel.getX()+50, currentLabel.getY());
				}
			}
			position--;
		}
	}

	public static void main(String[] args) {
		GUI gui = new GUI(new Game());
		gui.setVisible();
	}

}