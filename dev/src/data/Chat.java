package data;

import java.util.ArrayList;
import java.util.Date;

public class Chat {
	
	private String name;
	private Date start;
	
	private ArrayList<User> members;
	private ArrayList<Message> messageFlow;
	
	public Chat() { }

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}

	public ArrayList<Message> getMessageFlow() {
		return messageFlow;
	}
	public void setMessageFlow(ArrayList<Message> messageFlow) {
		this.messageFlow = messageFlow;
	}

	public ArrayList<User> getMembers() {
		return members;
	}
	public void setMembers(ArrayList<User> members) {
		this.members = members;
	}
}
