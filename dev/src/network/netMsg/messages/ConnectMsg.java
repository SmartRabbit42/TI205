package network.netMsg.messages;

import network.netMsg.NetMsg;

public class ConnectMsg extends NetMsg {

	private static final long serialVersionUID = 7591170873843269638L;
	
	private byte status;
	
	private String address;
	private int port;
	
	public ConnectMsg() {
		setMessageType(MessageType.connect);
	}

	public byte getStatus() {
		return status;
	}
	public void setStatus(byte status) {
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