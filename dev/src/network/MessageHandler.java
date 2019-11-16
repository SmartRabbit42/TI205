package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import data.Data;
import data.containers.User;
import network.netMsg.NetMsg;
import network.netMsg.standart.*;
import visual.Client;
import visual.dialogs.MessageDialog;

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
				addUserMsg((AddUserMsg) msg);
				break;
			case NetMsg.MessageType.onAddUser:
				onAddUserMsg((OnAddUserMsg) msg);
				break;
			case NetMsg.MessageType.statusUpdate:
				statusUpdateMsg((StatusUpdateMsg) msg);
				break;
		}
	}
		
	private User getSender(String id) {
		for (User user : data.getUsers())
			if (user.getId().equals(id))
				return user;
		
		return null;
	}
	
	// Handlers
	private void connectMsg(ConnectMsg msg) {
		User user = getSender(msg.getId());
		
		if (user != null) {
			user.setToken(msg.getToken());
			user.setStatus(msg.getStatus());
			user.setAddress(msg.getAddress());
			user.setPort(msg.getPort());
			
			client.updateUser(user);
			
			OnConnectMsg ocmsg = new OnConnectMsg();
			ocmsg.setStatus(data.getLocalUser().getStatus());

			try {
				network.sendMessage(user, ocmsg);
			} catch (IOException e) { }
		}
	}
	
	private void onConnectMsg(OnConnectMsg msg) {
		User user = getSender(msg.getId());
		
		if (user != null && user.getToken().equals(msg.getToken())) {
			user.setStatus(msg.getStatus());
			
			client.updateUser(user);
		}
	}
	
	private void disconnectMsg(DisconnectMsg msg) {
		User user = getSender(msg.getId());
		
		if (user != null && user.getToken().equals(msg.getToken())) {
			user.setStatus(User.Status.offline);
			
			client.updateUser(user);
		}
	}
	
	private void addUserMsg(AddUserMsg msg) {
		try {
			User newUser = new User();
			newUser.setAddress(msg.getAddress());
			newUser.setPort(msg.getPort());
			
			OnAddUserMsg oaumsg = new OnAddUserMsg();
			
			if (newUser.equals(data.getLocalUser()))
				oaumsg.setMsgStatus(OnAddUserMsg.Status.trying_to_befriend_self);
			else if (data.getUsers().contains(newUser))
				oaumsg.setMsgStatus(OnAddUserMsg.Status.user_already_added);
			else {
				String id = msg.getId() + data.getNum();
				
				newUser.setId(id);
				newUser.setToken(msg.getToken());
				newUser.setStatus(msg.getStatus());
				
				data.getUsers().add(newUser);
				data.increaseNum();
				client.addUser(newUser);
				
				oaumsg.setId(id);
				oaumsg.setMsgStatus(OnAddUserMsg.Status.success);
				oaumsg.setStatus(data.getLocalUser().getStatus());
				oaumsg.setAddress(data.getLocalUser().getAddress());
				oaumsg.setPort(data.getLocalUser().getPort());
			}
			
			network.sendMessage(msg.getAddress(), msg.getPort(), msg.getToken(), oaumsg);
		} catch (Exception e) { }
	}
	
	private void onAddUserMsg(OnAddUserMsg msg) {
		if (data.getLocalUser().getToken().equals(msg.getToken())) {
			MessageDialog msgd;
			
			switch(msg.getMsgStatus()) {
				default:
				case OnAddUserMsg.Status.unknown_error:
					msgd = new MessageDialog(client, "couldn't add new user: unknown error");
					msgd.setVisible(true);
					break;
				case OnAddUserMsg.Status.success:
					User newUser;
					try {
						newUser = new User();
						newUser.setId(msg.getId());
						newUser.setToken(msg.getToken());
						newUser.setStatus(msg.getStatus());
						newUser.setAddress(msg.getAddress());
						newUser.setPort(msg.getPort());
	
						data.getUsers().remove(newUser);
						data.getUsers().add(newUser);
						
						client.addUser(newUser);
						
						msgd = new MessageDialog(client, "added new user");
						msgd.setVisible(true);
					} catch (Exception e) { 
						msgd = new MessageDialog(client, "couldn't add new user: unknown error");
						msgd.setVisible(true);
					}		
					break;
				case OnAddUserMsg.Status.trying_to_befriend_self:
					msgd = new MessageDialog(client, "couldn't add new user: user is you");
					msgd.setVisible(true);
					break;
				case OnAddUserMsg.Status.user_already_added:
					msgd = new MessageDialog(client, "couldn't add new user: user already added");
					msgd.setVisible(true);
					break;
			}
			
			client.setEnabled(true);
		}
	}
	
	private void statusUpdateMsg(StatusUpdateMsg msg) {		
		User user = getSender(msg.getId());
		
		if (user != null && user.getToken().equals(msg.getToken())) {
			user.setStatus(msg.getStatus());
			client.updateUser(user);
		}
	}
}
