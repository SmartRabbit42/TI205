package network.netMsg;

import java.io.Serializable;

public class NetMsg implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private byte messageType;
	
	public static final class MessageType {
		public static final byte none = 0;
		
		public static final byte connect = 1;
		public static final byte onConnect = 2;
		public static final byte disconnect = 3;
		
		public static final byte statusUpdate = 4;
		
		public static final byte reachUser = 5;
		public static final byte onReachUser = 6;
		
		public static final byte includedOnChat = 7;
		
		public static final byte requestAddress = 8;
		public static final byte onRequestAddress = 9;
		
		public static final byte message = 10;
	}
	
	private String id;
	private String token;

	public NetMsg() { 
		setMessageType(MessageType.none);
	}

	public byte getMessageType() {
		return messageType;
	}
	public void setMessageType(byte messageType) {
		this.messageType = messageType;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
}