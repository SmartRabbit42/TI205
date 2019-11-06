package network.netMsg.standart;

import network.netMsg.NetMsg;
import network.netMsg.NetMsg.MessageType;

public class StatusUpdateMsg extends NetMsg {

	private static final long serialVersionUID = 7591170873843269638L;
	
	private byte status;

	public StatusUpdateMsg() {
		setMessageType(NetMsg.MessageType.statusUpdate);
	}

	public byte getStatus() {
		return status;
	}
	public void setStatus(byte status) {
		this.status = status;
	}
}
