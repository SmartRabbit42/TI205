package network.netMsg.messages;

import network.netMsg.NetMsg;

public class StatusUpdateMsg extends NetMsg {

	private static final long serialVersionUID = 7591170873843269638L;
	
	private byte status;

	public StatusUpdateMsg() {
		setMessageType(MessageType.statusUpdate);
	}

	public byte getStatus() {
		return status;
	}
	public void setStatus(byte status) {
		this.status = status;
	}
}