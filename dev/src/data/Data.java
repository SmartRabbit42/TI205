package data;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import data.containers.User;
import general.exceptions.EmptyDataFileException;
import general.exceptions.InvalidParameterException;
import data.containers.Chat;

public class Data implements Serializable {
	
	private static final long serialVersionUID = -3185108578513027310L;
	
	private User localUser;
	private List<User> users;
	private List<Chat> chats;
	
	public Data() { }
	
	public void init(String username) throws InvalidParameterException {
		setLocalUser(new User(username));
		setUsers(new ArrayList<User>());
		setChats(new ArrayList<Chat>());
	}
	
	public void load(File dataFile) throws IOException, ClassNotFoundException, EmptyDataFileException {
		try (FileInputStream fis = new FileInputStream(dataFile); 
				ObjectInputStream ois = new ObjectInputStream(fis)) {
			Object obj = ois.readObject();
			
			if (obj == null)
				throw new EmptyDataFileException();
				
			Data aux = (Data) obj;
				
			setLocalUser(aux.getLocalUser());
			setUsers(aux.getUsers());
			setChats(aux.getChats());
		    
		    ois.close();
		    fis.close();
		}
	}
	
	public void dump(File dataFile) throws IOException {	
		FileOutputStream fos = new FileOutputStream(dataFile);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		
		oos.writeObject(this);
		
		oos.close();
		fos.close();
	}

	public User getLocalUser() {
		return localUser;
	}
	public void setLocalUser(User localUser) {
		if (this.localUser != null)
			return;
		
		this.localUser = localUser;
	}
	
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		if (this.users != null)
			return;
		
		this.users = users;
	}
	public User getUser(String id) {
		for (User user : users)
			if (user.getId().equals(id))
				return user;
		
		return null;
	}

	
	public List<Chat> getChats() {
		return chats;
	}
	public void setChats(List<Chat> chats) {
		if (this.chats != null)
			return;
		
		this.chats = chats;
	}
}