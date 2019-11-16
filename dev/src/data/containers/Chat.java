package data.containers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import general.exceptions.InvalidParameterException;

public class Chat implements Serializable {
	
	private static final long serialVersionUID = -7541266357185083144L;
	
	private String name;
	private Date start;
	
	private ArrayList<User> members;
	private ArrayList<Message> messages;
	
	public Chat() {
		try {
			setName("chat" + (new Date()).getTime());
			setStart(new Date());
			setMembers(new ArrayList<User>());
			setMessages(new ArrayList<Message>());
		} catch (Exception e) { }
	}
	
	public Chat(String name) throws InvalidParameterException { 
		setName(name);
		setStart(new Date());
		setMembers(new ArrayList<User>());
		setMessages(new ArrayList<Message>());
	}

	public String getName() {
		return name;
	}
	public void setName(String name) throws InvalidParameterException {
		if (name == null || name.equals(""))
			throw new InvalidParameterException();
		
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
