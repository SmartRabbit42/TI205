package data.containers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import general.Helper;
import general.exceptions.InvalidParameterException;
import network.netMsg.NetMsg;

public class User implements Serializable {

	private static final long serialVersionUID = -8074919554796103339L;
	
	private String username;
	private String id;
	private String token;

	private String address;
	private int port;
	
	private byte status;
	
	private boolean added;
	
	private List<Chat> chats;
	
	private List<NetMsg> unsentMessages;
	
	public static class Status {
		public static final byte unknown = 0;
		public static final byte loading = 1;
		public static final byte offline = 3;
		public static final byte online = 4;
		public static final byte busy = 5;
	}
	
	public User () {
		try {
			setUsername("anon" + System.currentTimeMillis());
			setStatus(Status.online);
			
			setChats(new ArrayList<Chat>());
			setUnsentMessages(new ArrayList<NetMsg>());
		} catch (InvalidParameterException e) { }
	}
	
	public User (String username) throws InvalidParameterException {
		setUsername(username);
		setStatus(Status.online);
		
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

	public byte getStatus() {
		return status;
	}
	public void setStatus(byte status) {
		this.status = status;
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