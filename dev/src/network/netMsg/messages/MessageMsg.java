package network.netMsg.messages;

import network.netMsg.NetMsg;

public class MessageMsg extends NetMsg {

	private static final long serialVersionUID = 7718918154036741488L;

	private String chatId;
	
	private String content;
	
	private long time;
	
	public MessageMsg() {
		setMessageType(MessageType.message);
	}

	public String getChatId() {
		return chatId;
	}
	public void setChatId(String chatId) {
		this.chatId = chatId;
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
}
