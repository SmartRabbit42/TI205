package network.netMsg.messages;

import data.containers.User;
import network.netMsg.NetMsg;

public class OnConnectMsg extends NetMsg {

	private static final long serialVersionUID = 7591170863843269638L;
	
	private User.STATUS status;
	
	public OnConnectMsg() {
		setMessageType(MessageType.onConnect);
	}

	public User.STATUS getStatus() {
		return status;
	}
	public void setStatus(User.STATUS status) {
		this.status = status;
	}
}