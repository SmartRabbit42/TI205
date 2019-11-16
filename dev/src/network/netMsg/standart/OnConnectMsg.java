package network.netMsg.standart;

import network.netMsg.NetMsg;

public class OnConnectMsg extends NetMsg {

	private static final long serialVersionUID = 7591170863843269638L;
	
	private byte status;
	
	public OnConnectMsg() {
		setMessageType(NetMsg.MessageType.onConnect);
	}

	public byte getStatus() {
		return status;
	}
	public void setStatus(byte status) {
		this.status = status;
	}
}