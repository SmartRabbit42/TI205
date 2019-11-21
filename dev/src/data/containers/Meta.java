package data.containers;

import java.io.Serializable;
import java.util.List;

public class Meta implements Serializable {

	private static final long serialVersionUID = 2650389079311244251L;
	
	private User localUser;
	private List<User> users;
	private List<Chat> chats;
	
	public Meta() { }
	
	public User getLocalUser() {
		return localUser;
	}
	public void setLocalUser(User localUser) {
		this.localUser = localUser;
	}
	
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	public List<Chat> getChats() {
		return chats;
	}
	public void setChats(List<Chat> chats) {
		this.chats = chats;
	}
}
