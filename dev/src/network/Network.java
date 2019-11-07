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
	private Network network;
	
	public boolean connected;
	public boolean listening;
	
	public String address;
	public int port;
	
	private boolean updating;
	
	private ServerSocket serverSocket;
	
	public Network(Client client, Data data) {
		this.client = client;
		this.data = data;
		this.network = this;
	}
	
	public void start() throws IOException {
		if (this.connected)
			return;
		
		this.serverSocket = new ServerSocket(0);
		
		this.address = Inet4Address.getLocalHost().getHostAddress();
		this.port = serverSocket.getLocalPort();
		
		this.data.getLocalUser().setAddress(this.address);
		this.data.getLocalUser().setPort(this.port);
		
		for (User user : this.data.getUsers())
			user.setToken(Helper.createToken());
		ConnectMsg cmsg = new ConnectMsg();
		spreadMessage(cmsg);
		
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
		Socket socket = new Socket(user.getAddress(), user.getPort());
		
		msg.setUsername(data.getLocalUser().getUsername());
		msg.setToken(user.getToken());
		
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
	    out.writeObject(msg);
		out.close();
		
		socket.close();
	}
	
	public void sendMessage(String address, int port, String token, NetMsg msg) throws UnknownHostException, IOException {
		Socket socket = new Socket(address, port);
		
		msg.setUsername(data.getLocalUser().getUsername());
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
				// TODO Auto-generated catch block
				e.printStackTrace();
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
				
				while(listening) {
					try {
						Socket socket = serverSocket.accept();
						Runnable handler = new MessageHandler(socket, client, network, data);
						new Thread(handler).start();
					} catch (Exception e) { }
				}
				
				updating = false;
			}
		};
		new Thread(monitor).start();
	}
}