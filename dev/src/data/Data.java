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
import general.exceptions.EmptyDataFileException;
import general.exceptions.InvalidParameterException;
import data.containers.Chat;

public class Data implements Serializable {
	
	private static final long serialVersionUID = -3185108578513027310L;
	
	private User localUser;
	private ArrayList<User> users;
	private ArrayList<Chat> chats;
	private int num;
	
	public Data() { }
	
	public void init(String username) throws InvalidParameterException {
		setLocalUser(new User(username));
		setUsers(new ArrayList<User>());
		setChats(new ArrayList<Chat>());
		setNum(0);
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
			setNum(aux.getNum());
		    
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
	
	public ArrayList<User> getUsers() {
		return users;
	}
	public void setUsers(ArrayList<User> users) {
		if (this.users != null)
			return;
		
		this.users = users;
	}

	public ArrayList<Chat> getChats() {
		return chats;
	}
	public void setChats(ArrayList<Chat> chats) {
		if (this.chats != null)
			return;
		
		this.chats = chats;
	}

	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public void increaseNum() {
		num++;
	}
}