package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Date;
import java.util.List;

import data.Data;
import data.containers.Chat;
import data.containers.User;
import general.exceptions.InvalidParameterException;
import network.netMsg.NetMsg;
import network.netMsg.messaging.*;
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
			
			handleMessage((NetMsg) msg);
		}
	    catch(Exception e) { }
	}

	private void handleMessage(NetMsg msg) {
		try {
			switch (msg.getMessageType()) {
				default:
				case NetMsg.MessageType.none:
					System.out.println("none message received");
					break;
				case NetMsg.MessageType.connect:
					System.out.println("connect message received");
					connectMsg((ConnectMsg) msg);
					break;
				case NetMsg.MessageType.onConnect:
					System.out.println("onconnect message received");
					onConnectMsg((OnConnectMsg) msg);
					break;
				case NetMsg.MessageType.disconnect:
					System.out.println("disconnect message received");
					disconnectMsg((DisconnectMsg) msg);
					break;
				case NetMsg.MessageType.statusUpdate:
					System.out.println("statusupdate message received");
					statusUpdateMsg((StatusUpdateMsg) msg);
					break;
				case NetMsg.MessageType.addUser:
					System.out.println("adduser message received");
					addUserMsg((AddUserMsg) msg);
					break;
				case NetMsg.MessageType.onAddUser:
					System.out.println("onadduser message received");
					onAddUserMsg((OnAddUserMsg) msg);
					break;
				case NetMsg.MessageType.addedOnChat:
					System.out.println("addedonchat message received");
					addedOnChatMsg((AddedOnChatMsg) msg);
					break;
			} 
		} catch (Exception e) { 
			System.out.println("unknown error");
		}
	}
	
	// Handlers
	private void connectMsg(ConnectMsg msg) {
		User user = data.getUser(msg.getId());
		
		if (user == null)
			return;
		
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
	
	private void onConnectMsg(OnConnectMsg msg) {
		User user = data.getUser(msg.getId());
		
		if (user == null || !user.getToken().equals(msg.getToken()))
			return;

		user.setStatus(msg.getStatus());
		
		client.updateUser(user);
	}
	
	private void disconnectMsg(DisconnectMsg msg) {
		User user = data.getUser(msg.getId());
		
		if (user == null || user.getToken().equals(msg.getToken()))
			return;
		
		user.setStatus(User.Status.offline);
		
		client.updateUser(user);
	}
	
	private void statusUpdateMsg(StatusUpdateMsg msg) {		
		User user = data.getUser(msg.getId());
		
		if (user == null || !user.getToken().equals(msg.getToken()))
			return;
		
		user.setStatus(msg.getStatus());
		client.updateUser(user);
	}
	
	private void addUserMsg(AddUserMsg msg) {
		try {
			User newUser = new User();
			newUser.setId(msg.getId());
			newUser.setAddress(msg.getAddress());
			newUser.setPort(msg.getPort());
			
			OnAddUserMsg oaumsg = new OnAddUserMsg();
			
			if (newUser.equals(data.getLocalUser()))
				oaumsg.setMsgStatus(OnAddUserMsg.Status.trying_to_befriend_self);
			else if (data.getUsers().contains(newUser))
				oaumsg.setMsgStatus(OnAddUserMsg.Status.user_already_added);
			else {
				newUser.setToken(msg.getToken());
				newUser.setStatus(msg.getStatus());
				
				data.getUsers().add(newUser);
				client.addUser(newUser);
				
				oaumsg.setMsgStatus(OnAddUserMsg.Status.success);
				oaumsg.setStatus(data.getLocalUser().getStatus());
				oaumsg.setAddress(data.getLocalUser().getAddress());
				oaumsg.setPort(data.getLocalUser().getPort());
			}
			
			network.sendMessage(msg.getAddress(), msg.getPort(), msg.getToken(), oaumsg);
		} catch (Exception e) { }
	}
	
	private void onAddUserMsg(OnAddUserMsg msg) {
		User user = data.getUser(msg.getToken());
		
		if (user == null || !user.getToken().equals(msg.getToken()))
			return;
		
		MessageDialog msgd;
		
		switch(msg.getMsgStatus()) {
			default:
			case OnAddUserMsg.Status.unknown_error:
				data.getUsers().remove(user);
				
				msgd = new MessageDialog(client, "couldn't add new user: unknown error");
				msgd.setVisible(true);
				break;
			case OnAddUserMsg.Status.success:
				try {
					user.setId(msg.getId());
					user.setStatus(msg.getStatus());
					user.setAddress(msg.getAddress());
					user.setPort(msg.getPort());
					
					client.addUser(user);
					
					msgd = new MessageDialog(client, "added new user");
					msgd.setVisible(true);
				} catch (Exception e) { 
					msgd = new MessageDialog(client, "couldn't add new user: unknown error");
					msgd.setVisible(true);
				}		
				break;
			case OnAddUserMsg.Status.trying_to_befriend_self:
				data.getUsers().remove(user);
				
				msgd = new MessageDialog(client, "couldn't add new user: user is you");
				msgd.setVisible(true);
				break;
			case OnAddUserMsg.Status.user_already_added:
				data.getUsers().remove(user);
				
				msgd = new MessageDialog(client, "couldn't add new user: user already added");
				msgd.setVisible(true);
				break;
		}
	}

	private void addedOnChatMsg(AddedOnChatMsg msg) {
		User user = data.getUser(msg.getId());
		
		if (user == null || !user.getToken().equals(msg.getToken()))
			return;

		try {
			Chat newChat = new Chat(msg.getName());
			newChat.setStart(new Date(msg.getDate()));
			
			List<String> membersAddress = msg.getMembersAddress();
			List<Integer> membersPort = msg.getMembersPort();
			List<Byte> membersStatus = msg.getMembersStatus();
			
			List<User> members = newChat.getMembers();
			
			for (int i = 0; i < membersAddress.size(); i++) {
				
			}
		} catch (InvalidParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
