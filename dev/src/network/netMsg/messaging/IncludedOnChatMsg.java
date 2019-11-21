package network.netMsg.messaging;

import java.util.List;

import network.netMsg.NetMsg;

public class IncludedOnChatMsg extends NetMsg {

	private static final long serialVersionUID = -8098369012506824015L;
	
	private String name;
	private String chatId;
	private long date;
	
	private List<String> membersId;
	
	public IncludedOnChatMsg() {
		setMessageType(MessageType.includedOnChat);
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getChatId() {
		return chatId;
	}
	public void setChatId(String chatId) {
		this.chatId = chatId;
	}
	
	public long getDate() {
		return date;
	}
	public void setDate(long date) {
		this.date = date;
	}

	public List<String> getMembersId() {
		return membersId;
	}
	public void setMembersId(List<String> membersId) {
		this.membersId = membersId;
	}
}
