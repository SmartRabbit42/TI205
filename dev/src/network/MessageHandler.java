package network;

import java.io.ObjectInputStream;
import java.net.Socket;

import data.Data;
import data.containers.User;
import network.netMsg.GreetingMsg;
import network.netMsg.NetMsg;
import network.netMsg.OnGreetingMsg;
import visual.Client;

public class MessageHandler implements Runnable {

	private Socket sender;
	
	private Client client;
	private Network network;
	private Data data;
	
	public MessageHandler(Socket sender, Client client, Network network, Data data) {
		this.sender = sender;
		this.client = client;
		this.network = network;
		this.data = data;
	}
	
	@Override
	public void run() {
		try {
			ObjectInputStream in = new ObjectInputStream(sender.getInputStream());
		    Object msg = in.readObject();
			sender.close();
			
			handleMessage((NetMsg)msg);
		}
	    catch(Exception e) { }
	}

	private void handleMessage(NetMsg msg) {
		switch (msg.getMessageType()) {
			default:
			case NetMsg.MessageType.none:
				break;
			case NetMsg.MessageType.greeting:
				greetingMsg((GreetingMsg) msg);
				break;
			case NetMsg.MessageType.onGreeting:
				onGreetingMsg((OnGreetingMsg) msg);
				break;
		}
	}
	
	// Handlers
	private void greetingMsg(GreetingMsg msg) {
		try {
			User newUser = new User(msg.getUsername());
			newUser.setToken(msg.getToken());
			newUser.setAddress(msg.getAddress());
			newUser.setPort(msg.getPort());
			
			OnGreetingMsg ogmsg = new OnGreetingMsg();
			ogmsg.setAddress(data.getLocalUser().getAddress());
			ogmsg.setPort(data.getLocalUser().getPort());
			network.sendMessage(newUser, ogmsg);
			
			data.getOnlineUsers().add(newUser);
			client.addUser(newUser);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void onGreetingMsg(OnGreetingMsg msg) {
		User newUser;
		try {
			newUser = new User(msg.getUsername());
			newUser.setToken(msg.getToken());
			newUser.setAddress(msg.getAddress());
			newUser.setPort(msg.getPort());
			
			data.getOnlineUsers().add(newUser);
			client.addUser(newUser);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
