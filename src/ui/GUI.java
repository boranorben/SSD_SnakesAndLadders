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

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import game.Game;

public class GUI {

	private Game game;
	private JFrame homeFrame, gameFrame;
	private JLabel rollResult, boardPic, coverPic, titlePic, status, nextPlayer, currentPlayer;
	private JButton player2_Button, player3_Button, player4_Button, die, restartButton, replayButton;

	public GUI(Game game) {
		this.game = game;
		homeFrame = new JFrame("Snakes and Ladders Game");
		gameFrame = new JFrame("Snakes and Ladders Game");
		homeFrame.setResizable(false);
		gameFrame.setResizable(false);
		homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initComponent();
	}

	public void initComponent() {
		rollResult = new JLabel(new ImageIcon(getClass().getResource("./../images/dice0.png")));
		boardPic = new JLabel(new ImageIcon(getClass().getResource("./../images/boardPic.jpg")));
		coverPic = new JLabel(new ImageIcon(getClass().getResource("./../images/coverPic.jpg")));
		titlePic = new JLabel(new ImageIcon(getClass().getResource("./../images/titlePic.png")));
		status = new JLabel("Status");
		nextPlayer = new JLabel("Next player");
		currentPlayer = new JLabel("Current player");
		
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
		player4_Button.setIcon(new ImageIcon(getClass().getResource("./../images/4Player.png")));
		die.setIcon(new ImageIcon(getClass().getResource("./../images/die.png")));
		restartButton.setIcon(new ImageIcon(getClass().getResource("./../images/restart.png")));
		replayButton.setIcon(new ImageIcon(getClass().getResource("./../images/replay.png")));

		setTransparentBackground(player2_Button);
		setTransparentBackground(player3_Button);
		setTransparentBackground(player4_Button);
		setTransparentBackground(die);
		setTransparentBackground(restartButton);
		setTransparentBackground(replayButton);
		
		homeFrame.setContentPane(coverPic);
		homeFrame.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        homeFrame.add(player2_Button, gbc);
        homeFrame.add(player3_Button, gbc);
        homeFrame.add(player4_Button, gbc);
		
		JPanel panelBoard = new JPanel();
		panelBoard.add(boardPic);

		JPanel panelTitle = new JPanel();
		panelTitle.add(titlePic);

		JPanel panelController = new JPanel();
		panelController.add(restartButton);
		panelController.add(replayButton);
		panelController.add(currentPlayer);
		panelController.add(nextPlayer);
		panelController.add(status);
		panelController.setLayout(new BoxLayout(panelController, BoxLayout.PAGE_AXIS));
		
		JPanel panelRoll = new JPanel();
		panelRoll.add(die);
		panelRoll.add(rollResult);
		
		gameFrame.add(panelBoard, BorderLayout.EAST);
		gameFrame.add(panelTitle, BorderLayout.NORTH);
		gameFrame.add(panelController, BorderLayout.WEST);
		gameFrame.add(panelRoll, BorderLayout.SOUTH);

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
				game.currentPlayerMovePiece(diceFace);
				// check
				System.out.println(game.currentPlayer().getName() + ": " + diceFace);
				game.switchPlayer();
			}
		});

		restartButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		});

		replayButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				game.showReplay();
			}
		});

		homeFrame.pack();
		gameFrame.pack();
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