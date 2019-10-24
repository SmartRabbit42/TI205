package network.netMsg.entry;

import network.netMsg.NetMsg;

public class OnConnectionMsg extends NetMsg {

	private static final long serialVersionUID = 1L;
	
	public OnConnectionMsg() {
		setMessageType(MessageType.onConnect);
	}
}