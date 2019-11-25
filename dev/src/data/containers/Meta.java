package data.containers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import general.exceptions.InvalidParameterException;

public class Meta implements Serializable {

	private static final long serialVersionUID = 2650389079311244251L;
	
	private User localUser;
	private List<User> addedUsers;
	private List<User> knownUsers;
	private List<Chat> chats;
	
	private String privateKey;
	
	public Meta(String username) throws InvalidParameterException {
		setLocalUser(new User(username));
		setChats(new ArrayList<Chat>());
		setAddedUsers(new ArrayList<User>());
		setKnownUsers(new ArrayList<User>());
	}
	
	public User getLocalUser() {
		return localUser;
	}
	public void setLocalUser(User localUser) {
		this.localUser = localUser;
	}
	
	public List<User> getAddedUsers() {
		return addedUsers;
	}
	public void setAddedUsers(List<User> users) {
		this.addedUsers = users;
	}

	public List<User> getKnownUsers() {
		return knownUsers;
	}
	public void setKnownUsers(List<User> knownUsers) {
		this.knownUsers = knownUsers;
	}

	public List<Chat> getChats() {
		return chats;
	}
	public void setChats(List<Chat> chats) {
		this.chats = chats;
	}

	public String getPrivateKey() {
		return privateKey;
	}
	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}
}
