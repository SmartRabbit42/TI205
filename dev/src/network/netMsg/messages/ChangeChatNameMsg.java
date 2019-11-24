package network.netMsg.messages;

import network.netMsg.NetMsg;

public class ChangeChatNameMsg extends NetMsg {

	private static final long serialVersionUID = -8275261203065458758L;

	private String chatId;
	
	private String chatName;
	
	public ChangeChatNameMsg() {
		setMessageType(MessageType.changeChatName);
	}

	public String getChatId() {
		return chatId;
	}
	public void setChatId(String chatId) {
		this.chatId = chatId;
	}

	public String getChatName() {
		return chatName;
	}
	public void setChatName(String chatName) {
		this.chatName = chatName;
	}
}
