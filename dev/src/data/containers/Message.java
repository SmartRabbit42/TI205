package data.containers;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
	
	private static final long serialVersionUID = 3495882302934872561L;
	
	private User sender;
	private Chat chat;
	private String content;
	private Date time;
	
	public Message() { }

	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		
		return false;
	}
	
	public User getSender() {
		return sender;
	}
	public void setSender(User sender) {
		this.sender = sender;
	}

	public Chat getChat() {
		return chat;
	}

	public void setChat(Chat chat) {
		this.chat = chat;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
}