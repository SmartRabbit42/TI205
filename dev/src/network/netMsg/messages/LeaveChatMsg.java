package network.netMsg.messages;

import network.netMsg.NetMsg;

public class LeaveChatMsg extends NetMsg {

	private static final long serialVersionUID = 977569572237079806L;

	private String chatId;
	
	public LeaveChatMsg() {
		setMessageType(MessageType.leaveChat);
	}

	public String getChatId() {
		return chatId;
	}
	public void setChatId(String chatId) {
		this.chatId = chatId;
	}
}
