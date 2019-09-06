package data;

import java.util.ArrayList;

public class User {
	private String username;
	private String address;
	
	private String status;
	private String profiePicUrl;

	private ArrayList<Chat> chats;

	public User() { }
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
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
