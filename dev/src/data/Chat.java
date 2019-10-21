package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Chat implements Serializable {
	
	private static final long serialVersionUID = -7541266357185083144L;
	
	private String name;
	private Date start;
	
	private ArrayList<User> members;
	private ArrayList<Message> messages;
	
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

	public ArrayList<Message> getMessages() {
		return messages;
	}
	public void setMessages(ArrayList<Message> messageFlow) {
		this.messages = messageFlow;
	}

	public ArrayList<User> getMembers() {
		return members;
	}
	public void setMembers(ArrayList<User> members) {
		this.members = members;
	}
}
