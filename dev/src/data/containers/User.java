package data.containers;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
	
	public static class Status {
		public static final byte offline = 0;
		public static final byte online = 1;
		public static final byte busy = 2;
		public static final byte occupied = 3;
	}
	
	private static final long serialVersionUID = -8074919554796103339L;
	
	private String username;
	private String token;
	
	private String address;
	private int port;
	
	private byte status;
	
	private ArrayList<Chat> chats;
	
	public User (String username) throws Exception {
		setUsername(username);
		setStatus(Status.online);
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) throws Exception {
		if (username.equals(null) || username.equals(""))
			throw new Exception();
		
		this.username = username;
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

	public ArrayList<Chat> getChats() {
		return chats;
	}
	public void setChats(ArrayList<Chat> chats) {
		this.chats = chats;
	}
}
