package network.netMsg;

import java.io.Serializable;

public class NetMsg implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private byte messageType = MessageType.none;
	
	private String username;
	private String token;

	public NetMsg() { }

	public byte getMessageType() {
		return messageType;
	}
	public void setMessageType(byte messageType) {
		this.messageType = messageType;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
}