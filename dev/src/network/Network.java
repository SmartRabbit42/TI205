package network;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import data.Data;
import data.containers.User;
import network.netMsg.NetMsg;
import visual.Client;

public class Network {
	
	private Client client;
	private Data data;
	
	public boolean connected;
	public boolean listening;
	
	public String address;
	public int port;
	
	private boolean updating;
	
	private ServerSocket serverSocket;
	
	public Network(Client client, Data data) {
		this.client = client;
		this.data = data;
	}
	
	public void start() throws IOException {
		if (connected)
			return;
		
		serverSocket = new ServerSocket(0);
		
		address = Inet4Address.getLocalHost().getHostAddress();
		port = serverSocket.getLocalPort();
		
		listening = true;
		connected = true;
		
		updateMessagePump();
	}
	
	public void shut() throws IOException {
		serverSocket.close();
		
		listening = false;
		connected = false;
	}
	
	public void sendMessage(User user, NetMsg msg) throws UnknownHostException, IOException {
		if (user.getStatus() == User.Status.offline) {
			// TODO
		}
		
		Socket receiver = new Socket(user.getAddress(), user.getPort());
		
		msg.setUsername(data.getLocalUser().getUsername());
		msg.setToken(user.getToken());
		
		ObjectOutputStream out = new ObjectOutputStream(receiver.getOutputStream());
	    out.writeObject(msg);
		out.close();
		
		receiver.close();
	}
	
	public void updateMessagePump() {
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