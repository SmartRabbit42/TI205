package network.netMsg.messages;

import data.containers.User;
import network.netMsg.NetMsg;

public class ConnectMsg extends NetMsg {

	private static final long serialVersionUID = 7591170873843269638L;
	
	private User.STATUS status;
	
	private String address;
	private int port;
	
	public ConnectMsg() {
		setMessageType(MessageType.connect);
	}

	public User.STATUS getStatus() {
		return status;
	}
	public void setStatus(User.STATUS status) {
		this.status = status;
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
}