package data;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.io.Serializable;

import visual.Client;

public class Data {
	
	public static User localUser;
	
	public static ArrayList<User> onlineUsers;
	public static ArrayList<User> offlineUsers;
	
	public static ArrayList<Chat> chats;
	
	private class auxData implements Serializable {
		
		private static final long serialVersionUID = -3728345190233194710L;
		
		private User localUser;
		private ArrayList<User> onlineUsers;
		private ArrayList<User> offlineUsers;
		private ArrayList<Chat> chats;
		
		public void load() {
			localUser = Data.localUser;
			onlineUsers = Data.onlineUsers;
			offlineUsers = Data.offlineUsers;
			chats = Data.chats;
		}
		
		public void dump() {
			Data.localUser = localUser;
			Data.onlineUsers = onlineUsers;
			Data.offlineUsers = offlineUsers;
			Data.chats = chats;
		}
	}
	
	public static void load(File dataFile) throws IOException, ClassNotFoundException {
		FileInputStream fis = new FileInputStream(dataFile);
		ObjectInputStream ois = new ObjectInputStream(fis);
		
	    auxData aux = (auxData) ois.readObject();
	    aux.dump();
	    
	    Client.updateUser(localUser);
	    
	    for (Chat chat : chats)
	    	Client.loadChat(chat);
	    
	    ois.close();
	    fis.close();
	}
	
	public static void dump(File dataFile) throws IOException {
		FileOutputStream fos = new FileOutputStream(dataFile);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		
		auxData aux = (new Data()).new auxData();
		aux.load();
		oos.writeObject(aux);
		
		oos.close();
		fos.close();
	}
}