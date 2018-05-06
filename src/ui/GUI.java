package ui;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import game.Game;

public class GUI {

	private Game game;
	private JFrame homeFrame, gameFrame;
	private JLabel rollResult, boardPic, coverPic, status, nextPlayer, currentPlayer, player1, player2, player3, player4;
	private JButton player2_Button, player3_Button, player4_Button, die, restartButton, replayButton;
	private GridBagConstraints gbc;

	public GUI(Game game) {
		this.game = game;
		homeFrame = new JFrame("Snakes and Ladders Game");
		gameFrame = new JFrame("Snakes and Ladders Game");
		gameFrame.setSize(730, 730);
		homeFrame.setResizable(false);
		gameFrame.setResizable(false);
		homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initComponent();
	}

	public void initComponent() {
		rollResult = new JLabel(new ImageIcon(getClass().getResource("./../images/dice0.png")));
		boardPic = new JLabel(new ImageIcon(getClass().getResource("./../images/boardPic.jpg")));
		coverPic = new JLabel(new ImageIcon(getClass().getResource("./../images/coverPic.jpg")));
		status = new JLabel("Status");
		nextPlayer = new JLabel("Next player");
		currentPlayer = new JLabel("Current player");
		player1 = new JLabel(new ImageIcon(getClass().getResource("./../images/player1.png")));
		player2 = new JLabel(new ImageIcon(getClass().getResource("./../images/player2.png")));
		player3 = new JLabel(new ImageIcon(getClass().getResource("./../images/player3.png")));
		player4 = new JLabel(new ImageIcon(getClass().getResource("./../images/player4.png")));
		
		setFont(status);
		setFont(nextPlayer);
		setFont(currentPlayer);
		
		player2_Button = new JButton();
		player3_Button = new JButton();
		player4_Button = new JButton();
		die = new JButton();
		restartButton = new JButton();
		replayButton = new JButton();
		
		player2_Button.setIcon(new ImageIcon(getClass().getResource("./../images/2Player.png")));
		player3_Button.setIcon(new ImageIcon(getClass().getResource("./../images/3Player.png")));
//		player4_Button.setIcon(new ImageIcon(getClass().getResource("./../images/4Player.png")));
		die.setIcon(new ImageIcon(getClass().getResource("./../images/die.png")));
		restartButton.setIcon(new ImageIcon(getClass().getResource("./../images/restart.png")));
		replayButton.setIcon(new ImageIcon(getClass().getResource("./../images/replay.png")));
		
		gbc = new GridBagConstraints();
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
		
		JPanel panelBoard = new JPanel();
		panelBoard.add(boardPic);

		JPanel panelController = new JPanel();
		panelController.setLayout(new GridBagLayout());
		panelController.add(restartButton);
		panelController.add(replayButton);
		panelController.add(die);
		panelController.add(rollResult);
		
		JPanel panelStatus = new JPanel();
		panelStatus.add(currentPlayer);
		panelStatus.add(nextPlayer);
		panelStatus.add(status);
		
		homeFrame.pack();
		
		gameFrame.setLayout(new FlowLayout());
		gameFrame.add(panelController);
		gameFrame.add(panelStatus);
		gameFrame.add(panelBoard);

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
					int diceFace = game.currentPlayerRollDice();
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
			Font sizedFont = font.deriveFont(20f);
			label.setFont(font);
			label.setFont(sizedFont);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		GUI gui = new GUI(new Game());
		gui.setVisible();
	}

}