package data;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import data.containers.User;
import general.exceptions.DataNotLoadedException;
import general.exceptions.DataNotSavedException;
import general.exceptions.EmptyDataFileException;
import general.exceptions.InvalidParameterException;
import data.containers.Chat;
import data.containers.Meta;

public class Data {
	
	private Meta meta;
	
	public Data() { }
	
	public void init(String username) throws InvalidParameterException {
		meta = new Meta();
		meta.setLocalUser(new User(username));
		meta.setChats(new ArrayList<Chat>());
		meta.setUsers(new ArrayList<User>());
	}
	

	public void load(File dataFile) throws DataNotLoadedException {
		try (FileInputStream fis = new FileInputStream(dataFile);
				ObjectInputStream ois = new ObjectInputStream(fis)) {
			Object obj = ois.readObject();
			
			if (obj == null)
				throw new EmptyDataFileException();
				
			meta = (Meta) obj;
		    
		    ois.close();
		    fis.close();
		} catch (Exception e) {
			throw new DataNotLoadedException();
		}
	}
	
	public void dump(File dataFile) throws DataNotSavedException {	
		try (FileOutputStream fos = new FileOutputStream(dataFile);
				ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(meta);
			
			oos.close();
			fos.close();
		} catch (Exception e) {
			throw new DataNotSavedException();
		}
	}

	public User getLocalUser() {
		return meta.getLocalUser();
	}
	
	public List<User> getUsers() {
		return meta.getUsers();
	}
	public User getUser(String id) {
		for (User user : meta.getUsers())
			if (user.getId().equals(id))
				return user;
		
		return null;
	}

	public List<Chat> getChats() {
		return meta.getChats();
	}
	public Chat getChat(String id) {
		for (Chat chat : meta.getChats())
			if (chat.getId().equals(id))
				return chat;
		
		return null;
	}
}