package network.netMsg.standart;

import network.netMsg.NetMsg;
import network.netMsg.NetMsg.MessageType;

public class AddUser extends NetMsg {

	private static final long serialVersionUID = 440000773731260800L;
	
	private String address;
	private int port;
	
	public AddUser() {
		setMessageType(NetMsg.MessageType.addUser);
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
