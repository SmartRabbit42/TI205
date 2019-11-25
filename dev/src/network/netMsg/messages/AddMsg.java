package network.netMsg.messages;

import network.netMsg.NetMsg;

public class AddMsg extends NetMsg {

	private static final long serialVersionUID = 440000773731260800L;
	
	private String publicKey;
	
	private String address;
	private int port;
	
	private String username;
	
	private byte status;
	
	public AddMsg() {
		setMessageType(MessageType.add);
	}

	public String getPublicKey() {
		return publicKey;
	}
	public void setPublicKey(String privateKey) {
		this.publicKey = privateKey;
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

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public byte getStatus() {
		return status;
	}
	public void setStatus(byte status) {
		this.status = status;
	}
}
