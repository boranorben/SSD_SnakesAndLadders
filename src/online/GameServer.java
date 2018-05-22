package online;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.lloseng.ocsf.server.AbstractServer;
import com.lloseng.ocsf.server.ConnectionToClient;

public class GameServer extends AbstractServer {
	private List<GameRoom> rooms = new ArrayList<GameRoom>();

	public GameServer(int port) {
		super(port);
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		String[] message = ((String) msg).split(" ");
		String type = message[0];
		String text = message[1];
		GameRoom clientRoom = findClientRoom(client);
		if (type.equals("connect")) {
			if (rooms.size() > 0) {
				for (GameRoom room : rooms) {
					if (!room.isFull()) {
						room.add(client);
						if (room.isFull()) {
							try {
								room.getC1().sendToClient("start 1");
								room.getC2().sendToClient("start 2");
								room.getC3().sendToClient("start 3");
								room.getC4().sendToClient("start 4");
								break;
							} catch (IOException e) {
								e.printStackTrace();
							}
						} else {
							try {
								client.sendToClient("wait 1");
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
			clientRoom = findClientRoom(client);
			if (clientRoom == null) {
				GameRoom newRoom = new GameRoom();
				newRoom.add(client);
				rooms.add(newRoom);
				try {
					client.sendToClient("wait 1");
				} catch (IOException e) {
					e.printStackTrace();

				}
			}
		} else if (type.equals("move")) {
			if (clientRoom != null) {
				try {
					if (clientRoom.getC1() != client) {
						clientRoom.getC1().sendToClient(type + " " + text);
					}
					if (clientRoom.getC2() != client) {
						clientRoom.getC2().sendToClient(type + " " + text);
					}
					if (clientRoom.getC3() != client) {
						clientRoom.getC3().sendToClient(type + " " + text);
					}
					if (clientRoom.getC4() != client) {
						clientRoom.getC4().sendToClient(type + " " + text);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else if (type.equals("end")) {
			if (clientRoom != null) {
				if (rooms.contains(clientRoom)) {
					rooms.remove(clientRoom);
				}
			}
		} else if (type.equals("cancel") && clientRoom != null) {
			if (clientRoom.countPlayers() == 1) {
				rooms.remove(clientRoom);
			} else {
				if (clientRoom.getC1() == client) {
					clientRoom.setC1(null);
				} else if (clientRoom.getC2() == client) {
					clientRoom.setC2(null);
				} else if (clientRoom.getC3() == client) {
					clientRoom.setC3(null);
				} else if (clientRoom.getC4() == client) {
					clientRoom.setC4(null);
				}
			}
		} else if (type.equals("disconnect")) {
			if (clientRoom.isFull()) {
				try {
					if (clientRoom.getC1() != client) {
						clientRoom.getC1().sendToClient(type + " " + text);
						clientRoom.getC1().close();
					}
					if (clientRoom.getC2() != client) {
						clientRoom.getC2().sendToClient(type + " " + text);
						clientRoom.getC2().close();
					}
					if (clientRoom.getC3() != client) {
						clientRoom.getC3().sendToClient(type + " " + text);
						clientRoom.getC3().close();
					}
					if (clientRoom.getC4() != client) {
						clientRoom.getC4().sendToClient(type + " " + text);
						clientRoom.getC4().close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				rooms.remove(clientRoom);
			}
		}
	}

	public GameRoom findClientRoom(ConnectionToClient client) {
		for (GameRoom r : rooms) {
			if (r.getC1() == client || r.getC2() == client || r.getC3() == client || r.getC4() == client)
				return r;
		}
		return null;
	}

	public static void main(String[] args) {
		GameServer server = new GameServer(5000);
		try {
			server.listen();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
