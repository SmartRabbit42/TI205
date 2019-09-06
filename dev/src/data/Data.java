package data;

import java.io.File;
import java.util.ArrayList;

public class Data {
	public static User localUser;
	
	public static ArrayList<User> onlineUsers;
	public static ArrayList<User> offlineUsers;
	
	public static void LoadHistory(File historyFile, String username) {
		if (historyFile == null)
			return;
		
		
	}
}