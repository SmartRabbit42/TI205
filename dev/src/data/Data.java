package data;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import data.containers.User;
import data.containers.Chat;

public class Data implements Serializable {
	
	private static final long serialVersionUID = -3185108578513027310L;
	
	private User localUser;
	private ArrayList<User> onlineUsers;
	private ArrayList<User> offlineUsers;
	private ArrayList<Chat> chats;
	
	public Data() { }
	
	public void init(String username) throws Exception {
		setLocalUser(new User(username));
		setOnlineUsers(new ArrayList<User>());
		setOfflineUsers(new ArrayList<User>());
		setChats(new ArrayList<Chat>());
	}
	
	public void load(File dataFile) throws IOException, ClassNotFoundException {
		FileInputStream fis = new FileInputStream(dataFile);
		ObjectInputStream ois = new ObjectInputStream(fis);
		
		Object obj = ois.readObject();
		
		if (obj != null) {
			Data aux = (Data) obj;
			
		    setLocalUser(aux.getLocalUser());
		    setOnlineUsers(aux.getOnlineUsers());
		    setOfflineUsers(aux.getOfflineUsers());
		    setChats(aux.getChats());
		}
	    
	    ois.close();
	    fis.close();
	}
	
	public void dump(File dataFile) throws IOException {	
		FileOutputStream fos = new FileOutputStream(dataFile);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		
		oos.writeObject(this);
		
		oos.close();
		fos.close();
	}

	public User getLocalUser() {
		return this.localUser;
	}
	public void setLocalUser(User localUser) {
		if (this.localUser != null)
			return;
		
		this.localUser = localUser;
	}
	
	public ArrayList<User> getOnlineUsers() {
		return onlineUsers;
	}
	public void setOnlineUsers(ArrayList<User> onlineUsers) {
		if (this.onlineUsers != null)
			return;
		
		this.onlineUsers = onlineUsers;
	}

	public ArrayList<User> getOfflineUsers() {
		return offlineUsers;
	}
	public void setOfflineUsers(ArrayList<User> offlineUsers) {
		if (this.offlineUsers != null)
			return;
		
		this.offlineUsers = offlineUsers;
	}
	
	public ArrayList<Chat> getChats() {
		return this.chats;
	}
	public void setChats(ArrayList<Chat> chats) {
		if (this.chats != null)
			return;
		
		this.chats = chats;
	}
}