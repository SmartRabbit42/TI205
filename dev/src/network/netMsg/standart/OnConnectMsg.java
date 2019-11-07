package network.netMsg.standart;

import network.netMsg.NetMsg;

public class OnConnectMsg extends NetMsg {

	private static final long serialVersionUID = 7591170863843269638L;
	
	public OnConnectMsg() {
		setMessageType(NetMsg.MessageType.onConnect);
	}
}