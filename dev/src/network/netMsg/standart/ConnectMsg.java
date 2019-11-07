package network.netMsg.standart;

import network.netMsg.NetMsg;

public class ConnectMsg extends NetMsg {

	private static final long serialVersionUID = 7591170873843269638L;
	
	public ConnectMsg() {
		setMessageType(NetMsg.MessageType.connect);
	}
}