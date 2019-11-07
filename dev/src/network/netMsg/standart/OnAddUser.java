package network.netMsg.standart;

import network.netMsg.NetMsg;
import network.netMsg.NetMsg.MessageType;

public class OnAddUser extends NetMsg {

	private static final long serialVersionUID = 6895296448150060379L;

	private String address;
	private int port;
	
	private byte status;
	
	public static final class Status {
		public static final byte unknown_error = 0;
		public static final byte success = 1;
		public static final byte user_already_added = 2;
	}
	
	public OnAddUser() {
		setMessageType(NetMsg.MessageType.onAddUser);
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
