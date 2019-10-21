package data;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
	
	private static final long serialVersionUID = -8074919554796103339L;
	
	private String username;
	private String token;
	
	private String address;
	private int port;
	
	private byte status;
	private String profiePicUrl;

	private ArrayList<Chat> chats;

	public User () { 
		setStatus(UserStatus.online);
	}
	
	public User (String username) {
		setUsername(username);
		setStatus(UserStatus.online);
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
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
	
	public String getProfiePicUrl() {
		return profiePicUrl;
	}
	public void setProfiePicUrl(String profiePicUrl) {
		this.profiePicUrl = profiePicUrl;
	}

	public ArrayList<Chat> getChats() {
		return chats;
	}
	public void setChats(ArrayList<Chat> chats) {
		this.chats = chats;
	}
}
