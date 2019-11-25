package data;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInput;
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
		meta = new Meta(username);
	}

	public void load(File dataFile) throws DataNotLoadedException {
		try (InputStream fis = new FileInputStream(dataFile);
				ObjectInput ois = new ObjectInputStream(fis)) {
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
		try (OutputStream fos = new FileOutputStream(dataFile);
				ObjectOutput oos = new ObjectOutputStream(fos)) {
			oos.writeObject(meta);
			
			oos.close();
			fos.close();
		} catch (Exception e) {
			throw new DataNotSavedException();
		}
	}

	public String getPrivateKey() {
		return meta.getPrivateKey();
	}
	public String getPublicKey() {
		return meta.getLocalUser().getPublicKey();
	}
	
	public User getLocalUser() {
		return meta.getLocalUser();
	}
	
	public List<User> getAddedUsers() {
		return meta.getAddedUsers();
	}
	public List<User> getKnownUsers(){
		return meta.getKnownUsers();
	}
	public User getUser(String id) {
		for (User user : meta.getAddedUsers())
			if (user.getId().equals(id))
				return user;
		
		for (User user : meta.getKnownUsers())
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