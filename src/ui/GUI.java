package ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import game.Game;
import online.GameClient;

public class GUI implements Observer {

	private Game game;

	private JFrame homeFrame, gameFrame, disconnectedFrame, waitingFrame, closingFrame;
	private JLabel title, rollResult, boardPic, coverPic, status, currentPlayer, player1_Pin, player2_Pin, player3_Pin,
			player4_Pin, disconnectedLabel, searchPic, waitingLabel, waitingPic;
	private List<JLabel> players = new ArrayList<JLabel>();
	private JButton player2_Button, player3_Button, player4_Button, online_Button, die, home_Button, replayButton,
			homeButton, cancelButton;
	private ImageIcon player1_icon, player2_icon, player3_icon, player4_icon;
	private int diceFace = 0;
	private int currentPlayerNumber = 1, playerNumber;
	private boolean online = false;
	private GameClient client;
	private GUI gui;

	public GUI(Game game) {
		this.game = game;
		homeFrame = new JFrame("Snakes and Ladders - HOME");
		gameFrame = new JFrame("Snakes and Ladders - GAME");
		disconnectedFrame = new JFrame("Snakes and Ladders - OPPONENT DISCONNECTED");
		waitingFrame = new JFrame("Snakes and Ladders - WAITING FOR PLAYERS");
		closingFrame = new JFrame("Snakes and Ladders - CLOSING ONLINE");
		initFrame(homeFrame);
		initFrame(gameFrame);
		initFrame(disconnectedFrame);
		initFrame(waitingFrame);
		initFrame(closingFrame);
		waitingFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.gui = this;
		initComponent();
		game.addObserver(this);
	}

	public void initComponent() {
		title = new JLabel(new ImageIcon(getClass().getResource("./../images/titlePic.png")));
		rollResult = new JLabel(new ImageIcon(getClass().getResource("./../images/dice0.png")));
		boardPic = new JLabel(new ImageIcon(getClass().getResource("./../images/boardPic.jpg")));
		coverPic = new JLabel(new ImageIcon(getClass().getResource("./../images/coverPic.jpg")));
		status = new JLabel("Waiting for roling the dice...");
		currentPlayer = new JLabel();

		disconnectedLabel = new JLabel("Opponent disconnected");
		waitingLabel = new JLabel("Waiting for players");
		searchPic = new JLabel(new ImageIcon(getClass().getResource("./../images/searchPic.png")));
		waitingPic = new JLabel(new ImageIcon(getClass().getResource("./../images/searchPic.png")));

		player1_icon = new ImageIcon(getClass().getResource("./../images/player1.png"));
		player2_icon = new ImageIcon(getClass().getResource("./../images/player2.png"));
		player3_icon = new ImageIcon(getClass().getResource("./../images/player3.png"));
		player4_icon = new ImageIcon(getClass().getResource("./../images/player4.png"));

		player1_Pin = new JLabel(player1_icon);
		player2_Pin = new JLabel(player2_icon);
		player3_Pin = new JLabel(player3_icon);
		player4_Pin = new JLabel(player4_icon);

		setFont(status, 12f);
		setFont(currentPlayer, 12f);
		setFont(disconnectedLabel, 28f);
		setFont(waitingLabel, 28f);

		player2_Button = new JButton();
		player3_Button = new JButton();
		player4_Button = new JButton();
		online_Button = new JButton();
		die = new JButton();
		home_Button = new JButton();
		replayButton = new JButton();
		homeButton = new JButton();
		cancelButton = new JButton();

		player2_Button.setIcon(new ImageIcon(getClass().getResource("./../images/2Player.png")));
		player3_Button.setIcon(new ImageIcon(getClass().getResource("./../images/3Player.png")));
		player4_Button.setIcon(new ImageIcon(getClass().getResource("./../images/4Player.png")));
		online_Button.setIcon(new ImageIcon(getClass().getResource("./../images/online.png")));
		die.setIcon(new ImageIcon(getClass().getResource("./../images/die.png")));
		home_Button.setIcon(new ImageIcon(getClass().getResource("./../images/home_2.png")));
		replayButton.setIcon(new ImageIcon(getClass().getResource("./../images/replay.png")));
		homeButton.setIcon(new ImageIcon(getClass().getResource("./../images/home.png")));
		cancelButton.setIcon(new ImageIcon(getClass().getResource("./../images/cancel.png")));
		replayButton.setEnabled(false);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;

		setTransparentBackground(player2_Button);
		setTransparentBackground(player3_Button);
		setTransparentBackground(player4_Button);
		setTransparentBackground(online_Button);
		setTransparentBackground(die);
		setTransparentBackground(home_Button);
		setTransparentBackground(replayButton);
		setTransparentBackground(homeButton);
		setTransparentBackground(cancelButton);

		homeFrame.setContentPane(coverPic);
		homeFrame.setLayout(new GridBagLayout());
		homeFrame.add(player2_Button, gbc);
		homeFrame.add(player3_Button, gbc);
		homeFrame.add(player4_Button, gbc);
		homeFrame.add(online_Button, gbc);

		JPanel managerPanel = new JPanel();
		managerPanel.setLayout(new BoxLayout(managerPanel, BoxLayout.X_AXIS));
		managerPanel.add(home_Button);
		managerPanel.add(replayButton);

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridBagLayout());
		topPanel.add(title, gbc);
		topPanel.add(managerPanel, gbc);

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
		movePlayer(player4_Pin, x_initialPos, y_initialPos);
		boardPic.add(player4_Pin);

		JPanel statusPanel = new JPanel();
		statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
		statusPanel.add(currentPlayer);
		statusPanel.add(status);
		statusPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JPanel controllerPanel = new JPanel();
		controllerPanel.add(die);
		controllerPanel.add(rollResult);
		controllerPanel.add(statusPanel);

		gameFrame.add(topPanel, BorderLayout.NORTH);
		gameFrame.add(boardPanel, BorderLayout.CENTER);
		gameFrame.add(controllerPanel, BorderLayout.SOUTH);

		gameFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		gameFrame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if (JOptionPane.showConfirmDialog(gameFrame, "Are you sure to exit the game?", "Really Closing?",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					if (online == true) {
						try {
							client.sendToServer("disconnect 0");
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					System.exit(0);
				}
			}

		});

		gbc.gridheight = GridBagConstraints.CENTER;
		disconnectedFrame.setContentPane(searchPic);
		disconnectedFrame.setLayout(new GridBagLayout());
		disconnectedFrame.add(disconnectedLabel, gbc);
		disconnectedFrame.add(homeButton, gbc);

		waitingFrame.setContentPane(waitingPic);
		waitingFrame.setLayout(new GridBagLayout());
		waitingFrame.add(waitingLabel, gbc);
		waitingFrame.add(cancelButton, gbc);

		endFrame(homeFrame);
		endFrame(gameFrame);
		endFrame(disconnectedFrame);
		endFrame(waitingFrame);

		player2_Button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				changeFrame(2);
				players.add(player1_Pin);
				players.add(player2_Pin);
			}
		});

		player3_Button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				changeFrame(3);
				players.add(player1_Pin);
				players.add(player2_Pin);
				players.add(player3_Pin);
			}
		});

		player4_Button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				changeFrame(4);
				players.add(player1_Pin);
				players.add(player2_Pin);
				players.add(player3_Pin);
				players.add(player4_Pin);
			}
		});

		online_Button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				client = new GameClient("127.0.0.1", 5000, gui);
				online = true;
				connectToServer();
				homeFrame.setVisible(false);
				// disconnectedFrame.setVisible(true);
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
					die.setEnabled(false);
					game.gameLogic(101);
					if (online) {
						try {
							client.sendToServer("move " + diceFace);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});

		home_Button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (online) {
					try {
						client.sendToServer("disconnect 0");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				new GUI(new Game()).setVisible();
				gameFrame.setVisible(false);
				game.restartGame();
			}
		});

		replayButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				for (JLabel player : players) {
					player.setLocation(-50, 450);
				}
				game.showReplay();
			}
		});
		// gameFrame.setVisible(false);

		homeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				disconnectedFrame.setVisible(false);
				homeFrame.setVisible(true);
				try {
					client.sendToServer("disconnect 0");
				} catch (IOException e1) {
				}
			}
		});

		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				waitingFrame.setVisible(false);
				homeFrame.setVisible(true);
				try {
					client.sendToServer("cancel 0");
				} catch (IOException e) {
				}
			}
		});
	}

	public void initFrame(JFrame frame) {
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void endFrame(JFrame frame) {
		frame.pack();
		frame.setLocationRelativeTo(null);
	}

	public void setVisible() {
		homeFrame.setVisible(true);
	}

	public void startGame(int player) {
		homeFrame.setVisible(false);
		gameFrame.setVisible(true);
		waitingFrame.setVisible(false);
		game.initPlayers(4);
		players.add(player1_Pin);
		players.add(player2_Pin);
		players.add(player3_Pin);
		players.add(player4_Pin);
		playerNumber = player;
		if (player != 1) {
			die.setEnabled(false);
		}
	}

	public void opponentDisconnected() {
		disconnectedFrame.setVisible(true);
		gameFrame.setVisible(false);
	}

	public void waitForPlayers() {
		waitingFrame.setVisible(true);
	}

	public void switchPlayer() {
		if (currentPlayerNumber != 4) {
			currentPlayerNumber++;
		} else {
			currentPlayerNumber = 1;
		}
	}

	public void connectToServer() {
		try {
			client.openConnection();
			client.sendToServer("connect 0");
		} catch (IOException e) {
		}
	}

	public void opponentMove(int diceFace) {
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
		game.gameLogic(100);
		switchPlayer();
		if (currentPlayerNumber == playerNumber) {
			die.setEnabled(true);
		} else {
			die.setEnabled(false);
		}
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

	public void setFont(JLabel label, float f) {
		InputStream is = GUI.class.getResourceAsStream("./../fonts/HoboStd.otf");
		try {
			Font font = Font.createFont(Font.TRUETYPE_FONT, is);
			Font sizedFont = font.deriveFont(f);
			label.setFont(font);
			label.setFont(sizedFont);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
	}

	public JLabel getPlayerPin() {
		switch (game.currentPlayerName()) {
		case "Player1":
			return players.get(0);
		case "Player2":
			return players.get(1);
		case "Player3":
			return players.get(2);
		case "Player4":
			return players.get(3);
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
		} else {
			String currentPlayerName = game.currentPlayerName();
			if (game.currentPlayersWins() || game.hasBackward()) {
				currentPlayer.setIcon(getPlayerPin().getIcon());
				currentPlayer.setText("");
			} else if (!game.currentPlayer().getFreeze()) {
				switch (currentPlayerName) {
				case "Player1":
					currentPlayer.setIcon(player1_icon);
					break;
				case "Player2":
					currentPlayer.setIcon(player2_icon);
					break;
				case "Player3":
					currentPlayer.setIcon(player3_icon);
					break;
				case "Player4":
					currentPlayer.setIcon(player4_icon);
					break;
				default:
					break;
				}
				currentPlayer.setText("Current Player: " + currentPlayerName);
			}
		}
		currentPlayer.setHorizontalTextPosition(JLabel.CENTER);
		currentPlayer.setVerticalTextPosition(JLabel.BOTTOM);
	}

	public void updateStatus() {
		String squareType = game.getSquareType();
		String currentPlayerName = game.currentPlayerName();
		int currentPosition = game.currentPlayerPosition();
		if (game.currentPlayersWins()) {
			status.setText(currentPlayerName + " WINS!");
			replayButton.setEnabled(true);
			die.setEnabled(false);
			if (online) {
				try {
					client.sendToServer("end 0");
				} catch (IOException e) {
				}
			}
		} else {
			if (game.currentPlayer().getFreeze()) {
				status.setText(currentPlayerName + "'s still FREEZE!");
			}
			if (!squareType.equals("Square")) {
				switch (squareType) {
				case "Backward":
					status.setText("<html>" + currentPlayerName + " founds Backward<br>Roll and move backward!</html>");
					break;
				case "Freeze":
					status.setText(currentPlayerName + " found Freeze, Skip 1 turn!");
				default:
					status.setText(currentPlayerName + " founds " + squareType + " at " + currentPosition);
					break;
				}
			} else {
				status.setText(currentPlayerName + " goes to " + diceFace);
				currentPosition = game.currentPlayerPosition();
				status.setText(currentPlayerName + " is now at " + currentPosition);
				if (!game.isMoveEnd()) {
					int pos = currentPosition + diceFace;
					status.setText(currentPlayerName + " goes to " + pos);
				}
			}
		}
		status.setHorizontalTextPosition(JLabel.CENTER);
		status.setVerticalTextPosition(JLabel.BOTTOM);
	}

	@Override
	public void update(Observable o, Object arg) {
		move(game.getInitialPosition());
		updateCurrentPlayer();
		updateStatus();

	}

	public void move(int initialPos) {
		JLabel current = getPlayerPin();
		if (game.isMoveEnd()) {
			if (online == false) {
				die.setEnabled(true);
			}
		} else {
			if (game.getBackward()) {
				moveBackwards(initialPos, current);
			} else {
				moveForward(initialPos - 1, current);
			}
		}
	}

	public void moveForward(int position, JLabel currentLabel) {
		if (position % 10 == 0 && position != 0) {
			currentLabel.setLocation(currentLabel.getX(), currentLabel.getY() - 50);
		} else {
			if ((position / 10) % 2 == 0) {
				currentLabel.setLocation(currentLabel.getX() + 50, currentLabel.getY());
			} else {
				currentLabel.setLocation(currentLabel.getX() - 50, currentLabel.getY());
			}
		}
	}

	public void moveBackwards(int position, JLabel currentLabel) {
		if (position % 10 == 0 && position != 0) {
			currentLabel.setLocation(currentLabel.getX(), currentLabel.getY() + 50);
		} else {
			if ((position / 10) % 2 == 0) {
				currentLabel.setLocation(currentLabel.getX() - 50, currentLabel.getY());
			} else {
				currentLabel.setLocation(currentLabel.getX() + 50, currentLabel.getY());
			}
		}
	}

	public void sleep(int mSecs) {
		try {
			Thread.sleep(mSecs);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		GUI gui = new GUI(new Game());
		gui.setVisible();
	}

}