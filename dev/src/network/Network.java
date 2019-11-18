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
		
		localUser.setId(Helper.generateNewId(localUser.getFullAddress()));
		
		for (User user : data.getUsers()) {
			user.setStatus(User.Status.unknown);
			user.setToken(Helper.generateNewToken());
		}
		
		ConnectMsg cmsg = new ConnectMsg();
		cmsg.setStatus(localUser.getStatus());
		cmsg.setAddress(address);
		cmsg.setPort(port);
		
		spreadMessage(cmsg);
		
		running = true;
		
		System.out.println(String.format("network running on port %d", port));
		
		updateMessagePump();
	}
	
	public void shut() throws IOException {
		running = false;
		
		serverSocket.close();
		
		System.out.println("network no longer running");
	}
	
	public void sendMessage(User user, NetMsg msg) throws IOException  {
		if (user.getStatus() == User.Status.offline)
			return;
		
		Socket socket;
		try {
			socket = new Socket(user.getAddress(), user.getPort());
		} catch (UnknownHostException e) {
			user.setStatus(User.Status.unknown);
			return;
		}
		
		msg.setId(data.getLocalUser().getId());
		msg.setToken(user.getToken());
		
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
	    out.writeObject(msg);
		out.close();
		
		socket.close();
	}
	
	public void sendMessage(String address, int port, String token, NetMsg msg) throws UnknownHostException, IOException {
		Socket socket = new Socket(address, port);
		
		msg.setId(data.getLocalUser().getId());
		msg.setToken(token);
		
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
	    out.writeObject(msg);
		out.close();
		
		socket.close();
	}
	
	public void spreadMessage(NetMsg msg) {
		for (User user : data.getUsers()) {
			new Thread(new Runnable() {
			    @Override
			    public void run() {
			    	try {
						sendMessage(user, msg);
					} catch (IOException e) { }
			    }
			}).start();
		}
	}
	
	private void updateMessagePump() {
		new Thread (new Runnable() {
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
		}).start();
	}
}