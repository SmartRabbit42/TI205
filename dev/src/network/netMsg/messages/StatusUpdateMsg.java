package network.netMsg.messages;

import data.containers.User;
import network.netMsg.NetMsg;

public class StatusUpdateMsg extends NetMsg {

	private static final long serialVersionUID = 7591170873843269638L;
	
	private User.STATUS status;

	public StatusUpdateMsg() {
		setMessageType(MessageType.statusUpdate);
	}

	public User.STATUS getStatus() {
		return status;
	}
	public void setStatus(User.STATUS status) {
		this.status = status;
	}
}