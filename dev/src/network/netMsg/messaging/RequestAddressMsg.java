package network.netMsg.messaging;

import network.netMsg.NetMsg;

public class RequestAddressMsg extends NetMsg {

	private static final long serialVersionUID = -3899697232431961887L;

	private String userId;
	
	public RequestAddressMsg() {
		setMessageType(NetMsg.MessageType.requestAddress);
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
