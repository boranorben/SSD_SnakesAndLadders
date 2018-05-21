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
import java.util.*;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import game.Game;

public class GUI implements Observer {

	private Game game;
	private JFrame homeFrame, gameFrame, searchFrame, waitingFrame;
	private JLabel title, rollResult, boardPic, coverPic, status, currentPlayer, player1_Pin, player2_Pin, player3_Pin,
			player4_Pin, searchLabel, searchPic, waitingLabel, waitingPic;
	private List<JLabel> players = new ArrayList<JLabel>();
	private JButton player2_Button, player3_Button, player4_Button ,online_Button, die, restartButton, replayButton,
			serachButton, cancelButton;
	private ImageIcon player1_icon, player2_icon, player3_icon, player4_icon;
	private JTextField portField;
	private int diceFace = 0;

	public GUI(Game game) {
		this.game = game;
		homeFrame = new JFrame("Snakes and Ladders - HOME");
		gameFrame = new JFrame("Snakes and Ladders - GAME");
		searchFrame = new JFrame("Snakes and Ladders - SEARCH FOR ONLINE");
		waitingFrame = new JFrame("Snakes and Ladders - WAITING FOR PLAYERS");
		initFrame(homeFrame);
		initFrame(gameFrame);
		initFrame(searchFrame);
		initFrame(waitingFrame);
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
		
		searchLabel = new JLabel("Enter port number");
		waitingLabel = new JLabel("Waiting for players");
		searchPic  = new JLabel(new ImageIcon(getClass().getResource("./../images/searchPic.png")));
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
		setFont(searchLabel, 28f);
		setFont(waitingLabel, 28f);

		player2_Button = new JButton();
		player3_Button = new JButton();
		player4_Button = new JButton();
		online_Button = new JButton();
		die = new JButton();
		restartButton = new JButton();
		replayButton = new JButton();
		serachButton = new JButton();
		cancelButton = new JButton();

		player2_Button.setIcon(new ImageIcon(getClass().getResource("./../images/2Player.png")));
		player3_Button.setIcon(new ImageIcon(getClass().getResource("./../images/3Player.png")));
		player4_Button.setIcon(new ImageIcon(getClass().getResource("./../images/4Player.png")));
		online_Button.setIcon(new ImageIcon(getClass().getResource("./../images/online.png")));
		die.setIcon(new ImageIcon(getClass().getResource("./../images/die.png")));
		restartButton.setIcon(new ImageIcon(getClass().getResource("./../images/restart.png")));
		replayButton.setIcon(new ImageIcon(getClass().getResource("./../images/replay.png")));
		serachButton.setIcon(new ImageIcon(getClass().getResource("./../images/search.png")));
		cancelButton.setIcon(new ImageIcon(getClass().getResource("./../images/cancel.png")));
		replayButton.setEnabled(false);
		
		portField = new JTextField("                                       ");
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;

		setTransparentBackground(player2_Button);
		setTransparentBackground(player3_Button);
		setTransparentBackground(player4_Button);
		setTransparentBackground(online_Button);
		setTransparentBackground(die);
		setTransparentBackground(restartButton);
		setTransparentBackground(replayButton);
		setTransparentBackground(serachButton);
		setTransparentBackground(cancelButton);

		homeFrame.setContentPane(coverPic);
		homeFrame.setLayout(new GridBagLayout());
		homeFrame.add(player2_Button, gbc);
		homeFrame.add(player3_Button, gbc);
		homeFrame.add(player4_Button, gbc);
		homeFrame.add(online_Button, gbc);
		
		JPanel managerPanel = new JPanel();
		managerPanel.setLayout(new BoxLayout(managerPanel, BoxLayout.X_AXIS));
		managerPanel.add(restartButton);
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
		
		gbc.gridheight = GridBagConstraints.CENTER;
		searchFrame.setContentPane(searchPic);
		searchFrame.setLayout(new GridBagLayout());
		searchFrame.add(searchLabel, gbc);
		searchFrame.add(portField, gbc);
		searchFrame.add(serachButton, gbc);
		
		waitingFrame.setContentPane(waitingPic);
		waitingFrame.setLayout(new GridBagLayout());
		waitingFrame.add(waitingLabel, gbc);
		waitingFrame.add(cancelButton, gbc);
		
		endFrame(homeFrame);
		endFrame(gameFrame);
		endFrame(searchFrame);
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
				searchFrame.setVisible(true);
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
					game.gameLogic(diceFace);
				}
			}
		});

		restartButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
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
//		gameFrame.setVisible(false);
		
		serachButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				waitingFrame.setVisible(true);
				searchFrame.setVisible(false);
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				searchFrame.setVisible(true);
				waitingFrame.setVisible(false);
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
			status.setText(currentPlayerName + "WINS!");
			replayButton.setEnabled(true);
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
			die.setEnabled(true);
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