package network.netMsg.entry;

import network.netMsg.NetMsg;

public class ConnectionMsg extends NetMsg {

	private static final long serialVersionUID = 1L;
	
	public ConnectionMsg() {
		setMessageType(MessageType.connect);
	}
}