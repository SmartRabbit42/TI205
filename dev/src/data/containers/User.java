package data.containers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import general.Helper;
import general.exceptions.InvalidParameterException;
import network.netMsg.NetMsg;

public class User implements Serializable {

	private static final long serialVersionUID = -8074919554796103339L;
	
	private String publicKey;
	
	private String username;
	private String id;
	private String token;

	private String address;
	private int port;
	
	private STATUS status;
	
	private boolean added;
	
	private List<Chat> chats;
	
	private List<NetMsg> unsentMessages;
	
	public static enum STATUS {
		UNKNOWN, LOADING, OFFLINE, ONLINE, BUSY, BLACK
	}
	
	public User () {
		try {
			setUsername("anon" + System.currentTimeMillis());
			setStatus(STATUS.ONLINE);
			
			setChats(new ArrayList<Chat>());
			setUnsentMessages(new ArrayList<NetMsg>());
		} catch (InvalidParameterException e) {
			throw new RuntimeException();
		}
	}
	
	public User (String username) throws InvalidParameterException {
		setUsername(username);
		setStatus(STATUS.ONLINE);
		
		setChats(new ArrayList<Chat>());
		setUnsentMessages(new ArrayList<NetMsg>());
	}
	
	public boolean equals (Object obj)
    {
        if (this==obj)
            return true;

        if (obj==null)
            return false;

        if (this.getClass() != obj.getClass())
            return false;

        User usr = (User) obj;
        

        if (!usr.getId().equals(getId()))
        	return false;

        return true;
    }
	
	public String getPublicKey() {
		return publicKey;
	}
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) throws InvalidParameterException {
		if (!username.matches(Helper.nameRegex))
			throw new InvalidParameterException("invalid username");
		
		this.username = username;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}

	public STATUS getStatus() {
		return status;
	}
	public void setStatus(STATUS online) {
		this.status = online;
	}
	
	public boolean isAdded() {
		return added;
	}
	public void setAdded(boolean added) {
		this.added = added;
	}
	
	public List<Chat> getChats() {
		return chats;
	}
	public void setChats(List<Chat> chats) {
		this.chats = chats;
	}
	
	public List<NetMsg> getUnsentMessages() {
		return unsentMessages;
	}
	public void setUnsentMessages(List<NetMsg> unsentMessages) {
		this.unsentMessages = unsentMessages;
	}
	
	public String getFullAddress() {
		return String.format("%s:%d", address, port);
	}
}