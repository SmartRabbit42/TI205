package network.netMsg.messages;

import data.containers.User;
import network.netMsg.NetMsg;

public class OnAddMsg extends NetMsg {

	private static final long serialVersionUID = 6895296448150060379L;
	
	private String username;
	
	private User.STATUS status;

	private STATUS msgStatus;
	
	public static enum STATUS {
		UNKNOWN_ERROR, SUCCESS, TRYING_TO_ADD_LOCAL_USER, USER_ALREADY_ADDED
	}
	
	public OnAddMsg() {
		setMessageType(MessageType.onAdd);
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public User.STATUS getStatus() {
		return status;
	}
	public void setStatus(User.STATUS status) {
		this.status = status;
	}
	
	public STATUS getMsgStatus() {
		return msgStatus;
	}
	public void setMsgStatus(STATUS status) {
		this.msgStatus = status;
	}

	
}
