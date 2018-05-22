package online;

import com.lloseng.ocsf.client.AbstractClient;

import ui.GUI;

public class GameClient extends AbstractClient {
	private GUI gui;

	public GameClient(String host, int port, GUI gui) {
		super(host, port);
		System.out.println("hello");
		this.gui = gui;
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		System.out.println("recieved");
		String[] message = ((String) msg).split(" ");
		String type = message[0];
		int text = Integer.parseInt(message[1]);
		if (type.equals("move")) {
			gui.opponentMove(text);
		} else if (type.equals("start")) {
			gui.startGame(text);
		} else if (type.equals("wait")) {
			gui.waitForPlayers();
		} else if (type.equals("disconnect")) {
			gui.opponentDisconnected();
		}
	}
}
