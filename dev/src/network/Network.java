package network;

import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import data.Data;
import data.containers.User;
import general.Helper;
import general.exceptions.MessageNotSentException;
import general.exceptions.NetworkUnableToShutException;
import general.exceptions.NetworkUnableToStartException;
import network.netMsg.NetMsg;
import network.netMsg.messages.ConnectMsg;
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
	
	public void start() throws NetworkUnableToStartException {
		if (running)
			return;
		
		try {
			serverSocket = new ServerSocket(0);
			
			String address = Inet4Address.getLocalHost().getHostAddress();
			int port = serverSocket.getLocalPort();
			
			User localUser = data.getLocalUser();
			
			localUser.setAddress(address);
			localUser.setPort(port);
			
			if (localUser.getId() == null)
				localUser.setId(Helper.generateUserId(localUser.getFullAddress()));
			
			for (User user : data.getUsers())
				user.setStatus(User.Status.loading);
			
			running = true;
		
			updateMessagePump();
			
			System.out.println(String.format("network running on port %d", port));
			
			ConnectMsg cmsg = new ConnectMsg();
			cmsg.setStatus(localUser.getStatus());
			cmsg.setAddress(address);
			cmsg.setPort(port);
			
			spreadMessage(cmsg, false);
		} catch (Exception e) {
			throw new NetworkUnableToStartException();
		}
	}
	
	public void shut() throws NetworkUnableToShutException {
		try {
			running = false;
			
			serverSocket.close();
			
			System.out.println("network no longer running");
		} catch (Exception e) {
			throw new NetworkUnableToShutException();
		}
	}
	
	public void sendMessage(User user, NetMsg msg) throws MessageNotSentException  {
		if (user.equals(data.getLocalUser()))
			return;
		if (user.getStatus() == User.Status.offline)
			throw new MessageNotSentException("offline receiver");
		
    	try {
	    	Socket socket;
			
	    	socket = new Socket(user.getAddress(), user.getPort());

			msg.setId(data.getLocalUser().getId());
			msg.setToken(user.getToken());
			
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		    out.writeObject(msg);
			out.close();
			
			socket.close();
    	} catch (Exception e) {
    		user.setStatus(User.Status.unknown);
    		throw new MessageNotSentException();
    	}
	}
	
	public void spreadMessage(NetMsg msg, boolean store) {
		new Thread(new Runnable() {
		    @Override
		    public void run() {
		    	for (User user : data.getUsers())
			    	try {
						sendMessage(user, msg);
					} catch (MessageNotSentException e) { 
						if (store)
							user.getUnsentMessages().add(msg);
					}
		    }
		}).start();
	}
	
	public void spreadMessage(List<User> users, NetMsg msg, boolean store) {
		new Thread(new Runnable() {
		    @Override
		    public void run() {
		    	for (User user : users)
			    	try {
						sendMessage(user, msg);
					} catch (MessageNotSentException e) { 
						if (store)
							user.getUnsentMessages().add(msg);
					}
		    }
		}).start();
	}
	
	public void sendUnsentMessages(User user) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				List<NetMsg> unsent = new ArrayList<NetMsg>();
				
				for (NetMsg msg : user.getUnsentMessages()) {
					try {
						sendMessage(user, msg);
					} catch (MessageNotSentException e) {
						unsent.add(msg);
					}
				}
				
				user.setUnsentMessages(unsent);			
			}
		}).start();
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