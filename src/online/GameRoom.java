package online;

import com.lloseng.ocsf.server.ConnectionToClient;

public class GameRoom {
	private ConnectionToClient c1 = null;
	private ConnectionToClient c2 = null;
	private ConnectionToClient c3 = null;
	private ConnectionToClient c4 = null;
	private boolean isStart = false;
	private int currentPlayer = 1;

	public void add(ConnectionToClient newClient) {
		if(!isFull()) {
			if(c1 == null) {
				c1 = newClient;
			} else if(c2 == null) {
				c2 = newClient;
			} else if(c3 == null) {
				c3 = newClient;
			} else {
				c4 = newClient;
			}
		}
	}

	public boolean isFull() {
		if(countPlayers() == 4) {
			return true;
		}
		return false;
	}

	public int countPlayers() {
		int count = 0;
		if (c1 != null) {
			count++;
		}
		if (c2 != null) {
			count++;
		}
		if (c3 != null) {
			count++;
		}
		if (c4 != null) {
			count++;
		}
		if (count == 4)
			isStart = true;
		return count;
	}
	
	public ConnectionToClient getC1() {
		return this.c1;
	}

	public ConnectionToClient getC2() {
		return c2;
	}

	public ConnectionToClient getC3() {
		return c3;
	}


	public ConnectionToClient getC4() {
		return c4;
	}

	public void setC1(ConnectionToClient c1) {
		this.c1 = c1;
	}

	public void setC2(ConnectionToClient c2) {
		this.c2 = c2;
	}

	public void setC3(ConnectionToClient c3) {
		this.c3 = c3;
	}

	public void setC4(ConnectionToClient c4) {
		this.c4 = c4;
	}
}
