package network;

import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Date;
import java.util.List;

import data.Data;
import data.containers.Chat;
import data.containers.User;
import general.exceptions.InvalidParameterException;
import general.exceptions.MessageNotSentException;
import network.netMsg.NetMsg;
import network.netMsg.messaging.*;
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
			
			handleMessage((NetMsg) msg);
		} catch(Exception e) {
	    	System.out.println("unknown error");
	    }
	}

	private void handleMessage(NetMsg msg) {
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
			case NetMsg.MessageType.reachUser:
				System.out.println("adduser message received");
				reachUserMsg((ReachUserMsg) msg);
				break;
			case NetMsg.MessageType.onReachUser:
				System.out.println("onadduser message received");
				onReachUserMsg((OnReachUserMsg) msg);
				break;
			case NetMsg.MessageType.includedOnChat:
				System.out.println("addedonchat message received");
				includedOnChatMsg((IncludedOnChatMsg) msg);
				break;
			case NetMsg.MessageType.requestAddress:
				System.out.println("requestAddress message received");
				requestAddressMsg((RequestAddressMsg) msg);
				break;
			case NetMsg.MessageType.onRequestAddress:
				System.out.println("onRequestAddress message received");
				onRequestAddressMsg((OnRequestAddressMsg) msg);
				break;
		} 
	}
	
	// Handlers
	private void connectMsg(ConnectMsg msg) {
		User user = data.getUser(msg.getId());

		if (user == null)
			return;
		
		try {
			user.setToken(msg.getToken());
			user.setStatus(msg.getStatus());
			user.setAddress(msg.getAddress());
			user.setPort(msg.getPort());
			
			OnConnectMsg ocmsg = new OnConnectMsg();
			ocmsg.setStatus(data.getLocalUser().getStatus());

			network.sendMessage(user, ocmsg);
			
			client.updateUser(user);
		} catch (MessageNotSentException e) { }
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
		
		if (user == null || !user.getToken().equals(msg.getToken()))
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
	
	private void reachUserMsg(ReachUserMsg msg) {
		try {
			User newUser = new User();
			newUser.setId(msg.getId());
			newUser.setAddress(msg.getAddress());
			newUser.setPort(msg.getPort());
			newUser.setToken(msg.getToken());
			
			OnReachUserMsg oaumsg = new OnReachUserMsg();
			
			if (newUser.equals(data.getLocalUser()))
				oaumsg.setMsgStatus(OnReachUserMsg.Status.tryingToReachLocalUser);
			else if (data.getUsers().contains(newUser))
				oaumsg.setMsgStatus(OnReachUserMsg.Status.userAlreadyReached);
			else {
				newUser.setStatus(msg.getStatus());
				newUser.setUsername(msg.getUsername());
				
				data.getUsers().add(newUser);
				client.addUser(newUser);
				
				oaumsg.setAddress(data.getLocalUser().getAddress());
				oaumsg.setPort(data.getLocalUser().getPort());
				oaumsg.setUsername(data.getLocalUser().getUsername());
				oaumsg.setStatus(data.getLocalUser().getStatus());
				oaumsg.setMsgStatus(OnReachUserMsg.Status.success);
			}
			
			network.sendMessage(newUser, oaumsg);
		} catch (Exception e) { }
	}
	
	private void onReachUserMsg(OnReachUserMsg msg) {
		User user = data.getUser(msg.getToken());
		
		if (user == null || !user.getToken().equals(msg.getToken()))
			return;

		switch(msg.getMsgStatus()) {
			default:
			case OnReachUserMsg.Status.unknownError:
				data.getUsers().remove(user);
				break;
			case OnReachUserMsg.Status.success:
				try {
					user.setId(msg.getId());
					user.setAddress(msg.getAddress());
					user.setPort(msg.getPort());
					user.setUsername(msg.getUsername());
					user.setStatus(msg.getStatus());
					
					client.addUser(user);
				} catch (Exception e) {  }		
				break;
			case OnReachUserMsg.Status.tryingToReachLocalUser:
				data.getUsers().remove(user);
				break;
			case OnReachUserMsg.Status.userAlreadyReached:
				data.getUsers().remove(user);
				break;
		}
	}

	private void includedOnChatMsg(IncludedOnChatMsg msg) {
		User user = data.getUser(msg.getId());
		
		if (user == null || !user.getToken().equals(msg.getToken()))
			return;

		try {		
			Chat newChat = new Chat(msg.getName());
			newChat.setId(msg.getChatId());
			newChat.setStart(new Date(msg.getDate()));

			List<User> members = newChat.getMembers();
			
			for (String id : msg.getMembersId()) {
				if (id.equals(data.getLocalUser().getId())) {
					members.add(data.getLocalUser());
					continue;
				}
					
				User member = data.getUser(id);
				
				if (member == null) {
					member = new User();
					member.setId(id);
					member.setStatus(User.Status.loading);
					member.getChats().add(newChat);
					
					members.add(member);
					
					RequestAddressMsg ram = new RequestAddressMsg();
					ram.setUserId(id);
					
					try {
						network.sendMessage(user, ram);
					} catch (MessageNotSentException e) {  }
				} else {
					member.getChats().add(newChat);
					members.add(member);
				}
			}

			data.getChats().add(newChat);
			client.addChat(newChat);
		} catch (InvalidParameterException e) { }
	}
	
	private void requestAddressMsg(RequestAddressMsg msg) {
		User user = data.getUser(msg.getId());
		
		if (user == null || !user.getToken().equals(msg.getToken()))
			return;
		
		User requestedUser = data.getUser(msg.getUserId());
		
		OnRequestAddressMsg oram = new OnRequestAddressMsg();
		oram.setUserId(requestedUser.getId());
		oram.setAddress(requestedUser.getAddress());
		oram.setPort(requestedUser.getPort());
		
		try {
			network.sendMessage(user, oram);
		} catch (MessageNotSentException e) { }
	}
	
	private void onRequestAddressMsg(OnRequestAddressMsg msg) {
		User user = data.getUser(msg.getId());
		
		if (user == null || !user.getToken().equals(msg.getToken()))
			return;
		
		User requestedUser = data.getUser(msg.getUserId());
		
		requestedUser.setAddress(msg.getAddress());
		requestedUser.setPort(msg.getPort());
	}
}
