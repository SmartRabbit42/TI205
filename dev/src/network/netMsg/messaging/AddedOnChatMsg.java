package network.netMsg.messaging;

import java.util.List;

import network.netMsg.NetMsg;

public class AddedOnChatMsg extends NetMsg {

	private static final long serialVersionUID = -8098369012506824015L;
	
	private String name;
	private long date;
	
	private List<String> membersAddress;
	private List<Integer> membersPort;
	
	private List<Byte> membersStatus;
	
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

	public List<String> getMembersAddress() {
		return membersAddress;
	}
	public void setMembersAddress(List<String> membersAddress) {
		this.membersAddress = membersAddress;
	}

	public List<Integer> getMembersPort() {
		return membersPort;
	}
	public void setMembersPort(List<Integer> membersPort) {
		this.membersPort = membersPort;
	}

	public List<Byte> getMembersStatus() {
		return membersStatus;
	}
	public void setMembersStatus(List<Byte> membersStatus) {
		this.membersStatus = membersStatus;
	}

}
