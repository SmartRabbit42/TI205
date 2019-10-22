package data;

import java.io.Serializable;
import java.util.ArrayList;

import data.containers.User;
import data.containers.Chat;

public class AuxData implements Serializable {
	
	private static final long serialVersionUID = -3728345190233194710L;
	
	private User localUser;
	private ArrayList<User> onlineUsers;
	private ArrayList<User> offlineUsers;
	private ArrayList<Chat> chats;
	
	public void load() {
		this.localUser = Data.localUser;
		this.onlineUsers = Data.onlineUsers;
		this.offlineUsers = Data.offlineUsers;
		this.chats = Data.chats;
	}
	
	public void dump() {
		if (this.localUser != null)
			Data.localUser = this.localUser;
		if (this.onlineUsers != null)
			Data.onlineUsers = this.onlineUsers;
		if (this.offlineUsers != null)
			Data.offlineUsers = this.offlineUsers;
		if (this.chats != null)
			Data.chats = this.chats;
	}
}