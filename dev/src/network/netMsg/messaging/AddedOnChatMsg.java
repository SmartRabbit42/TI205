package network.netMsg.messaging;

import java.util.List;

import network.netMsg.NetMsg;

public class AddedOnChatMsg extends NetMsg {

	private static final long serialVersionUID = -8098369012506824015L;
	
	private String name;
	private long date;
	
	private List<String> membersId;
	
	public AddedOnChatMsg() {
		setMessageType(NetMsg.MessageType.addedOnChat);
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
