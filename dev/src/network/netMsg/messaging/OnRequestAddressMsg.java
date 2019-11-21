package network.netMsg.messaging;

import network.netMsg.NetMsg;

public class OnRequestAddressMsg extends NetMsg {

	private static final long serialVersionUID = 1549163461545822963L;

	private String address;
	private int port;
	private String userId;
	
	public OnRequestAddressMsg() {
		setMessageType(MessageType.onRequestAddress);
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

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
