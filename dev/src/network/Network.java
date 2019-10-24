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
	
	private Data data;
	
	public boolean connected;
	public boolean listening;
	
	public String address;
	public int port;
	
	private boolean updating;
	
	private ServerSocket serverSocket;
	
	public Network(Data data) {
		this.data = data;
	}
	
	public void start() throws IOException {
		if (this.connected)
			return;
		
		this.serverSocket = new ServerSocket(0);
		
		this.address = Inet4Address.getLocalHost().getHostAddress();
		this.port = serverSocket.getLocalPort();
		
		data.getLocalUser().setAddress(this.address);
		data.getLocalUser().setPort(this.port);
		
		this.listening = true;
		this.connected = true;
		
		updateMessagePump();
	}
	
	public void shut() throws IOException {
		this.serverSocket.close();
		
		this.listening = false;
		this.connected = false;
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