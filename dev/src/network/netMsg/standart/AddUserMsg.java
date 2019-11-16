package network.netMsg.standart;

import network.netMsg.NetMsg;

public class AddUserMsg extends NetMsg {

	private static final long serialVersionUID = 440000773731260800L;
	
	private String address;
	private int port;
	
	private byte status;
	
	public AddUserMsg() {
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

	public byte getStatus() {
		return status;
	}
	public void setStatus(byte status) {
		this.status = status;
	}
}
