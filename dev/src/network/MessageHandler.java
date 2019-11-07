package network;

import java.io.ObjectInputStream;
import java.net.Socket;

import data.Data;
import data.containers.User;
import network.netMsg.NetMsg;
import network.netMsg.standart.*;
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
			case NetMsg.MessageType.connect:
				connectMsg((ConnectMsg) msg);
				break;
			case NetMsg.MessageType.onConnect:
				onConnectMsg((OnConnectMsg) msg);
				break;
			case NetMsg.MessageType.disconnect:
				disconnectMsg((DisconnectMsg) msg);
				break;
			case NetMsg.MessageType.addUser:
				addUserMsg((AddUser) msg);
				break;
			case NetMsg.MessageType.onAddUser:
				onAddUserMsg((OnAddUser) msg);
				break;
			case NetMsg.MessageType.statusUpdate:
				statusUpdateMsg((StatusUpdateMsg) msg);
				break;
		}
	}
		
	private User getSender(String username) throws Exception {
		return this.data.getUsers().get(this.data.getUsers().indexOf(new User(username)));
	}
	
	// Handlers
	private void connectMsg(ConnectMsg msg) {
		try {
			User user = getSender(msg.getUsername());
			
			if (user != null) {
				user.setToken(msg.getToken());
				user.setStatus(User.Status.online);
				
				this.client.updateUser(user);
				
				OnConnectMsg ocmsg = new OnConnectMsg();
				this.network.sendMessage(user, ocmsg);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void onConnectMsg(OnConnectMsg msg) {
		try {
			User user = getSender(msg.getUsername());
			
			if (user != null) {
				user.setToken(msg.getToken());
				user.setStatus(User.Status.online);
				
				this.client.updateUser(user);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void disconnectMsg(DisconnectMsg msg) {
		try {
			User user = getSender(msg.getUsername());
			
			if (user != null) {
				user.setStatus(User.Status.offline);
				
				this.client.updateUser(user);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void addUserMsg(AddUser msg) {
		try {
			User newUser = new User(msg.getUsername());
			newUser.setToken(msg.getToken());
			newUser.setAddress(msg.getAddress());
			newUser.setPort(msg.getPort());
			
			OnAddUser ogmsg = new OnAddUser();
			ogmsg.setAddress(this.data.getLocalUser().getAddress());
			ogmsg.setPort(this.data.getLocalUser().getPort());
			
			if (this.data.getUsers().contains(newUser))
				ogmsg.setStatus(OnAddUser.Status.user_already_added);
			else {
				ogmsg.setStatus(OnAddUser.Status.success);
				this.data.getUsers().add(newUser);
				this.client.addUser(newUser);
			}
			
			this.network.sendMessage(newUser, ogmsg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void onAddUserMsg(OnAddUser msg) {
		switch(msg.getStatus()) {
			default:
			case OnAddUser.Status.unknown_error:
				// TODO
				System.out.print("ongreetingmsg: unknown error");
				break;
			case OnAddUser.Status.success:
				User newUser;
				try {
					newUser = new User(msg.getUsername());
					newUser.setToken(msg.getToken());
					newUser.setAddress(msg.getAddress());
					newUser.setPort(msg.getPort());

					this.data.getUsers().remove(newUser);
					this.data.getUsers().add(newUser);
					
					this.client.addUser(newUser);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
				break;
			case OnAddUser.Status.user_already_added:
				// TODO
				System.out.print("ongreetingmsg: user-already-added error");
				break;
		}
	}
	
	private void statusUpdateMsg(StatusUpdateMsg msg) {
		try {			
			User user = getSender(msg.getUsername());
			
			if (user.getToken().equals(msg.getToken())) {
				user.setStatus(msg.getStatus());
				this.client.updateUser(user);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
