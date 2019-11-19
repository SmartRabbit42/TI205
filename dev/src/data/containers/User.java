package data.containers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import general.exceptions.InvalidParameterException;

public class User implements Serializable {

	private static final long serialVersionUID = -8074919554796103339L;
	
	private String username;
	private String id;
	private String token;

	private String address;
	private int port;
	
	private byte status;
	
	private List<Chat> chats;
	
	public static class Status {
		public static final byte unknown = 0;
		public static final byte offline = 1;
		public static final byte online = 2;
		public static final byte busy = 3;
		public static final byte occupied = 4;
	}
	
	public User () {
		try {
			setUsername("anon" + (new Date()).getTime());
			setStatus(Status.online);
			
			setChats(new ArrayList<Chat>());
		} catch (InvalidParameterException e) { }
	}
	
	public User (String username) throws InvalidParameterException {
		setUsername(username);
		setStatus(Status.online);
		
		setChats(new ArrayList<Chat>());
	}
	
	public boolean equals (Object obj)
    {
        if (this==obj)
            return true;

        if (obj==null)
            return false;

        if (this.getClass()!=obj.getClass())
            return false;

        User usr = (User) obj;
        

        if (!(usr.getFullAddress().equals(getFullAddress()) && usr.getId().equals(getId())))
        	return false;

        return true;
    }
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) throws InvalidParameterException {
		if (username.equals(null) || username.equals(""))
			throw new InvalidParameterException();
		
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

	public List<Chat> getChats() {
		return chats;
	}
	public void setChats(List<Chat> chats) {
		this.chats = chats;
	}

	public String getFullAddress() {
		return String.format("%s:%d", address, port);
	}
}