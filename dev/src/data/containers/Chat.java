package data.containers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import general.exceptions.InvalidParameterException;

public class Chat implements Serializable {
	
	private static final long serialVersionUID = -7541266357185083144L;
	
	private String name;
	private String id;
	private Date start;
	
	private boolean silenced;
	
	private List<User> members;
	private List<Message> messages;
	
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

	public boolean equals (Object obj)
    {
        if (this==obj)
            return true;

        if (obj==null)
            return false;

        if (this.getClass()!=obj.getClass())
            return false;

        Chat cht = (Chat) obj;
        

        if (!cht.getId().equals(getId()))
        	return false;

        return true;
    }
	
	public String getName() {
		return name;
	}
	public void setName(String name) throws InvalidParameterException {
		if (name == null || name.equals(""))
			throw new InvalidParameterException("invalid chat name");
		
		this.name = name;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}

	public boolean isSilenced() {
		return silenced;
	}
	public void setSilenced(boolean silenced) {
		this.silenced = silenced;
	}

	public List<Message> getMessages() {
		return messages;
	}
	public void setMessages(List<Message> messageFlow) {
		this.messages = messageFlow;
	}

	public List<User> getMembers() {
		return members;
	}
	public void setMembers(List<User> members) {
		this.members = members;
	}
}
