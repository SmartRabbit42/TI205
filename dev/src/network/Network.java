package network;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;

import data.Data;
import data.User; 
import data.UserStatus;
import network.netMsg.NetMsg;

public class Network {
	
	public static boolean connected;
	public static boolean listening;
	
	private static boolean updating;
	
	private static ServerSocket serverSocket;
	
	public static void start(String username) throws IOException {
		if (connected)
			return;
		
		serverSocket = new ServerSocket(0);
		
		Data.localUser = new User(username);
		Data.localUser.setAddress(Inet4Address.getLocalHost().getHostAddress());
		Data.localUser.setPort(serverSocket.getLocalPort());
		
		listening = true;
		connected = true;
		
		updateMessagePump();
	}
	
	public static void shut() {
		try {
			serverSocket.close();
		} catch (IOException e) { }
		
		listening = false;
		connected = false;
	}
	
	public static boolean sendMessage(User user, NetMsg msg) {
		if (user.getStatus()  == UserStatus.offline)
			return false;
		
		try {
			Socket receiver = new Socket(user.getAddress(), user.getPort());
			
			msg.setUsername(Data.localUser.getUsername());
			msg.setToken(user.getToken());
			
			ObjectOutputStream out = new ObjectOutputStream(receiver.getOutputStream());
		    out.writeObject(msg);
			out.close();
			
			receiver.close();
		}
		catch (Exception e) { 
			return false;
		}
		
		return true;
	}
	
	public static void updateMessagePump() {
		Runnable monitor = new Runnable() {
			@Override
			public void run() {
				if (updating)
					return;
				
				updating = true;
				
				while(listening) {
					try {
						Socket sender = serverSocket.accept();
						Runnable handler = new MessageHandler(sender);
						new Thread(handler).start();
					} catch (Exception e) { }
				}
				
				updating = false;
			}
		};
		new Thread(monitor).start();
	}
}