package network.netMsg.messages;

import network.netMsg.NetMsg;

public class DisconnectMsg extends NetMsg {

	private static final long serialVersionUID = 7591170873843269638L;
	
	public DisconnectMsg() {
		setMessageType(MessageType.disconnect);
	}
}