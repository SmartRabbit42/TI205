package network.netMsg.standart;

import network.netMsg.NetMsg;

public class OnReachUserMsg extends NetMsg {

	private static final long serialVersionUID = 6895296448150060379L;

	
	private String address;
	private int port;
	
	private String username;
	
	private byte status;

	private byte msgStatus;
	
	public static final class Status {
		public static final byte unknownError = 0;
		public static final byte success = 1;
		public static final byte tryingToReachLocalUser = 2;
		public static final byte userAlreadyReached = 3;
	}
	
	public OnReachUserMsg() {
		setMessageType(MessageType.onReachUser);
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
	
	public byte getMsgStatus() {
		return msgStatus;
	}
	public void setMsgStatus(byte status) {
		this.msgStatus = status;
	}

	
}
