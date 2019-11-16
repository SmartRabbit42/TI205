package network;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import data.Data;
import data.containers.User;
import general.Helper;
import network.netMsg.NetMsg;
import network.netMsg.standart.ConnectMsg;
import visual.Client;

public class Network {
	
	private Client client;
	private Data data;
	private Network instance;
	
	public boolean running;
	
	private boolean updating;
	
	private ServerSocket serverSocket;
	
	public Network(Client client, Data data) {
		this.client = client;
		this.data = data;
		
		instance = this;
	}
	
	public void start() throws IOException {
		if (running)
			return;
		
		serverSocket = new ServerSocket(0);
		
		String address = Inet4Address.getLocalHost().getHostAddress();
		int port = serverSocket.getLocalPort();
		
		User localUser = data.getLocalUser();
		
		localUser.setAddress(address);
		localUser.setPort(port);
		
		for (User user : data.getUsers()) {
			System.out.println(user.getUsername());
			
			user.setStatus(User.Status.unknown);
			user.setToken(Helper.createToken());
		}
		
		ConnectMsg cmsg = new ConnectMsg();
		cmsg.setStatus(localUser.getStatus());
		cmsg.setAddress(address);
		cmsg.setPort(port);
		
		spreadMessage(cmsg);
		
		running = true;
		
		updateMessagePump();
	}
	
	public void shut() throws IOException {
		running = false;
		
		serverSocket.close();
	}
	
	public void sendMessage(User user, NetMsg msg) throws UnknownHostException, IOException {
		if (user.getStatus() == User.Status.offline)
			return;
		
		Socket socket = new Socket(user.getAddress(), user.getPort());
		
		msg.setId(user.getId());
		msg.setToken(user.getToken());
		
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
	    out.writeObject(msg);
		out.close();
		
		socket.close();
	}
	
	public void sendMessage(String address, int port, String token, NetMsg msg) throws UnknownHostException, IOException {
		Socket socket = new Socket(address, port);
		
		msg.setToken(token);
		
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
	    out.writeObject(msg);
		out.close();
		
		socket.close();
	}
	
	public void spreadMessage(NetMsg msg) {
		for (User user : data.getUsers()) {
			try {
				sendMessage(user, msg);
			} catch (IOException e) {
				user.setStatus(User.Status.unknown);
			}
		}
	}
	
	public void updateMessagePump() {
		Runnable monitor = new Runnable() {
			@Override
			public void run() {
				if (updating)
					return;
				
				updating = true;
				
				while(running) {
					try {
						Socket socket = serverSocket.accept();
						Runnable handler = new MessageHandler(socket, client, instance, data);
						new Thread(handler).start();
					} catch (Exception e) { }
				}
				
				updating = false;
			}
		};
		new Thread(monitor).start();
	}
}