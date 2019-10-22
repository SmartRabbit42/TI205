package data;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import data.containers.User;
import data.containers.Chat;

import visual.Client;

public class Data {
	
	public static User localUser;
	
	public static ArrayList<User> onlineUsers;
	public static ArrayList<User> offlineUsers;
	
	public static ArrayList<Chat> chats;
	
	public static void init(String username) {
		localUser = new User(username);
		
		onlineUsers = new ArrayList<User>();
		offlineUsers = new ArrayList<User>();
		
		chats = new ArrayList<Chat>();
	}
	
	public static void load(File dataFile) throws IOException, ClassNotFoundException {
		init("anon");
		
		FileInputStream fis = new FileInputStream(dataFile);
		ObjectInputStream ois = new ObjectInputStream(fis);
		
		Object obj = ois.readObject();
		
		if (obj != null) {
			AuxData aux; 
			aux = (AuxData) obj;
		    aux.dump();
		}
		
		Client.updateUser(localUser);
	    
	    for (Chat chat : chats)
	    	Client.loadChat(chat);
	    
	    ois.close();
	    fis.close();
	}
	
	public static void dump(File dataFile) throws IOException {	
		FileOutputStream fos = new FileOutputStream(dataFile);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		
		AuxData aux = new AuxData();
		aux.load();
		oos.writeObject(aux);
		
		oos.close();
		fos.close();
	}
}