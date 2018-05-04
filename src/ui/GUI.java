package ui;

import java.awt.BorderLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import game.Game;


public class GUI {

	private Game game;
	private JFrame homeFrame;
	private JFrame gameFrame;
	private JLabel rollResult;

	public GUI(Game game) {
		this.game = game;
		this.homeFrame = new JFrame();
		this.gameFrame = new JFrame();
		homeFrame.setTitle("Snake Ladder Game");
		homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initComponent();
	}

	public void initComponent() {		
		JLabel boardPic = new JLabel(new ImageIcon(getClass().getResource("./../images/boardPic.jpg")));	
		JLabel coverPic = new JLabel(new ImageIcon(getClass().getResource("./../images/coverPic2.jpg")));
		JButton player2_Button = new JButton(), player3_Button = new JButton(), player4_Button = new JButton();
		JButton die = new JButton();
		JLabel titlePic = new JLabel(new ImageIcon(getClass().getResource("./../images/titlePic.png")));
		JButton restartButton = new JButton(), replayButton = new JButton();
		JLabel status = new JLabel("Status");
		JLabel nextPlayer = new JLabel("Next player");
		JLabel currentPlayer = new JLabel("Current player");
		rollResult = new JLabel("You Get: 0");

		player2_Button.setIcon(new ImageIcon(getClass().getResource("./../images/2Player.png")));	
		player3_Button.setIcon(new ImageIcon(getClass().getResource("./../images/3Player.png")));
		player4_Button.setIcon(new ImageIcon(getClass().getResource("./../images/4Player.png")));
		die.setIcon(new ImageIcon(getClass().getResource("./../images/die.png")));
		restartButton.setIcon(new ImageIcon(getClass().getResource("./../images/restart.png")));	
		replayButton.setIcon(new ImageIcon(getClass().getResource("./../images/replay.png")));

		transparentBackground(player2_Button);
		transparentBackground(player3_Button);
		transparentBackground(player4_Button);
		transparentBackground(die);
		transparentBackground(restartButton);
		transparentBackground(replayButton);

		JPanel panelHome = new JPanel();
		panelHome.add(coverPic);
		coverPic.setLayout(new BoxLayout(coverPic,BoxLayout.PAGE_AXIS));
		coverPic.add(player2_Button);
		coverPic.add(player3_Button);
		coverPic.add(player4_Button);

		JPanel panelBoard = new JPanel();
		panelBoard.add(boardPic);

		JPanel panelTitle = new JPanel();
		panelTitle.add(titlePic);

		JPanel panelController = new JPanel();
		panelController.add(currentPlayer);
		panelController.add(nextPlayer);
		panelController.add(status);
		panelController.add(rollResult);
		panelController.add(die);
		panelController.add(restartButton);
		panelController.add(replayButton);
		panelController.setLayout(new BoxLayout(panelController,BoxLayout.PAGE_AXIS));

		homeFrame.add(panelHome, BorderLayout.NORTH);
		gameFrame.add(panelBoard, BorderLayout.CENTER);
		gameFrame.add(panelTitle, BorderLayout.NORTH);
		gameFrame.add(panelController, BorderLayout.WEST);

		ActionListener player2Listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				homeFrame.setVisible(false);
				gameFrame.setVisible(true);	
				
			}
		};

		ActionListener player3Listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				homeFrame.setVisible(false);
				gameFrame.setVisible(true);				
			}
		};

		ActionListener player4Listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				homeFrame.setVisible(false);
				gameFrame.setVisible(true);				
			}
		};
		player2_Button.addActionListener(player2Listener);
		player3_Button.addActionListener(player3Listener);
		player4_Button.addActionListener(player4Listener);

		ActionListener rollListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				rollResult.setText("You Get: " + game.currentPlayerRollDice());
			}
		};

		die.addActionListener(rollListener);

		ActionListener restart = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

			}
		};
		restartButton.addActionListener(restart);

		ActionListener replay = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		};
		replayButton.addActionListener(replay);

		homeFrame.pack();
		gameFrame.pack();
		gameFrame.setVisible(false);
	}

	public void transparentBackground(JButton button){
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
	}


	public static void main(String[] args) {
		new GUI(new Game()).homeFrame.setVisible(true);;
	}



}
