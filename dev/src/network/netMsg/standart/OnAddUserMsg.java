package network.netMsg.standart;

import network.netMsg.NetMsg;

public class OnAddUserMsg extends NetMsg {

	private static final long serialVersionUID = 6895296448150060379L;

	private byte status;
			
	private String address;
	private int port;
	
	private byte msgStatus;
	
	public static final class Status {
		public static final byte unknown_error = 0;
		public static final byte success = 1;
		public static final byte trying_to_befriend_self = 2;
		public static final byte user_already_added = 3;
	}
	
	public OnAddUserMsg() {
		setMessageType(NetMsg.MessageType.onAddUser);
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

	public byte getMsgStatus() {
		return msgStatus;
	}
	public void setMsgStatus(byte status) {
		this.msgStatus = status;
	}
}
