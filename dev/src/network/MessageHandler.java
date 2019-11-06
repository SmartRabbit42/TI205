package network;

import java.io.ObjectInputStream;
import java.net.Socket;

import data.Data;
import data.containers.User;
import network.netMsg.NetMsg;
import network.netMsg.standart.GreetingMsg;
import network.netMsg.standart.OnGreetingMsg;
import network.netMsg.standart.StatusUpdateMsg;
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
			case NetMsg.MessageType.statusUpdate:
				statusUpdate((StatusUpdateMsg) msg);
				break;
		}
	}
		
	private User getSender(String username) throws Exception {
		return data.getOnlineUsers().get(data.getOnlineUsers().indexOf(new User(username)));
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
			
			if (data.getOnlineUsers().contains(newUser) || 
					data.getOfflineUsers().contains(newUser))
				ogmsg.setStatus(OnGreetingMsg.Status.user_already_added);
			else {
				ogmsg.setStatus(OnGreetingMsg.Status.success);
				data.getOnlineUsers().add(newUser);
				client.addUser(newUser);
			}
			
			network.sendMessage(newUser, ogmsg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void onGreetingMsg(OnGreetingMsg msg) {
		switch(msg.getStatus()) {
			default:
			case OnGreetingMsg.Status.unknown_error:
				// TODO
				System.out.print("ongreetingmsg: unknown error");
				break;
			case OnGreetingMsg.Status.success:
				User newUser;
				try {
					newUser = new User(msg.getUsername());
					newUser.setToken(msg.getToken());
					newUser.setAddress(msg.getAddress());
					newUser.setPort(msg.getPort());

					data.getOnlineUsers().remove(newUser);
					data.getOfflineUsers().remove(newUser);
					data.getOnlineUsers().add(newUser);
					
					client.addUser(newUser);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
				break;
			case OnGreetingMsg.Status.user_already_added:
				// TODO
				System.out.print("ongreetingmsg: user-already-added error");
				break;
		}
	}
	
	private void statusUpdate(StatusUpdateMsg msg) {
		try {			
			User user = getSender(msg.getUsername());
			
			if (user.getToken().equals(msg.getToken())) {
				user.setStatus(msg.getStatus());
				client.updateUser(user);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
