package network;

import java.net.Socket;
import java.util.Date;
import java.util.List;

import data.Data;
import data.containers.Chat;
import data.containers.Message;
import data.containers.User;
import general.Helper;
import general.exceptions.InvalidParameterException;
import general.exceptions.MessageNotSentException;
import general.exceptions.TooBigMessageException;
import network.netMsg.NetMsg;
import network.netMsg.messages.*;
import visual.Client;

public class MessageHandler implements Runnable {

	private Socket sender;
	
	private Client client;
	private Network network;
	private Data data;
	
	private String log;
	
	public MessageHandler(Socket sender, Client client, Network network, Data data) {
		this.sender = sender;
		
		this.client = client;
		this.network = network;
		this.data = data;
	}
	
	@Override
	public void run() {
		try {
			byte[] buffer = new byte[Network.BUFFER_SIZE];
			
			int size = sender.getInputStream().read(buffer);
			
			log = String.format("> %d bytes received from %s:%d\n", size,
					sender.getInetAddress().getHostAddress(), sender.getPort());
			
			sender.close();
					
			if (size > Network.BUFFER_SIZE)
				throw new TooBigMessageException();

			Object msg = Helper.decodeMessage(buffer, data.getPrivateKey());
			
			handleMessage((NetMsg) msg);
		} catch(Exception e) {
	    	throw new RuntimeException(e);
	    }
	}

	private void handleMessage(NetMsg msg) throws Exception {
		log += "decoded bytes into ";
		
		switch (msg.getMessageType()) {
			default:
			case NetMsg.MessageType.none:
				log += "nothing\n";
				break;
			case NetMsg.MessageType.connect:
				log += "connectMsg\n";
				connectMsg((ConnectMsg) msg);
				break;
			case NetMsg.MessageType.onConnect:
				log += "onConnectMsg\n";
				onConnectMsg((OnConnectMsg) msg);
				break;
			case NetMsg.MessageType.disconnect:
				log += "disconnectMsg\n";
				disconnectMsg((DisconnectMsg) msg);
				break;
			case NetMsg.MessageType.statusUpdate:
				log += "statusUpdateMsg\n";
				statusUpdateMsg((StatusUpdateMsg) msg);
				break;
			case NetMsg.MessageType.add:
				log += "addMsg\n";
				addMsg((AddMsg) msg);
				break;
			case NetMsg.MessageType.onAdd:
				log += "onAddMsg\n";
				onAddMsg((OnAddMsg) msg);
				break;
			case NetMsg.MessageType.includedOnChat:
				log += "includedOnChatMsg\n";
				includedOnChatMsg((IncludedOnChatMsg) msg);
				break;
			case NetMsg.MessageType.message:
				log += "messageMsg\n";
				messageMsg((MessageMsg) msg);
				break;
			case NetMsg.MessageType.changeChatName:
				log += "changeChatNameMsg\n";
				changeChatNameMsg((ChangeChatNameMsg) msg);
				break;
			case NetMsg.MessageType.leaveChat:
				log += "leaveChatMsg\n";
				leaveChatMsg((LeaveChatMsg) msg);
				break;
		}
		
		log += "\n\n";
		
		System.out.print(log);
		System.out.flush();
	}
	
	private boolean isMessageLegit(User sender, String msgToken) {
		if (sender == null) {
			log += "error: inexistent sender";
			return false;
		}
			
		
		if (sender.equals(data.getLocalUser())) {
			log += "error: sender is local user";
			return false;
		}
			
		
		if (!sender.getToken().equals(msgToken)) {
			log += "error: incorrect token";
			return false;
		}
			
		
		log += "message verified";
		return true;
	}
	
	// Handlers
	private void connectMsg(ConnectMsg msg) {
		User user = data.getUser(msg.getId());

		if (!isMessageLegit(user, msg.getToken()))
			return;
		
		user.setStatus(msg.getStatus());
		user.setAddress(msg.getAddress());
		user.setPort(msg.getPort());
		
		OnConnectMsg ocmsg = new OnConnectMsg();
		ocmsg.setStatus(data.getLocalUser().getStatus());

		try {
			network.sendMessage(user, ocmsg);
			
			network.sendUnsentMessages(user);
		} catch (MessageNotSentException e) {
			user.getUnsentMessages().add(ocmsg);
		}

		client.updateUser(user);
	}
	
	private void onConnectMsg(OnConnectMsg msg) {
		User user = data.getUser(msg.getId());
		
		if (!isMessageLegit(user, msg.getToken()))
			return;
		
		user.setStatus(msg.getStatus());
		
		network.sendUnsentMessages(user);
		
		client.updateUser(user);
	}
	
	private void disconnectMsg(DisconnectMsg msg) {
		User user = data.getUser(msg.getId());
		
		if (!isMessageLegit(user, msg.getToken()))
			return;
		
		user.setStatus(User.STATUS.OFFLINE);
		
		client.updateUser(user);
	}
	
	private void statusUpdateMsg(StatusUpdateMsg msg) {		
		User user = data.getUser(msg.getId());
		
		if (!isMessageLegit(user, msg.getToken()))
			return;
		
		user.setStatus(msg.getStatus());
		
		client.updateUser(user);
	}
	
	private void addMsg(AddMsg msg) {
		try {
			User newUser = new User();
			newUser.setId(msg.getId());
			newUser.setAddress(msg.getAddress());
			newUser.setPort(msg.getPort());
			newUser.setToken(msg.getToken());
			
			OnAddMsg oamsg = new OnAddMsg();
			
			if (newUser.equals(data.getLocalUser()))
				oamsg.setMsgStatus(OnAddMsg.STATUS.TRYING_TO_ADD_LOCAL_USER);
			else if (data.getAddedUsers().contains(newUser))
				oamsg.setMsgStatus(OnAddMsg.STATUS.USER_ALREADY_ADDED);
			else {
				newUser.setStatus(msg.getStatus());
				newUser.setUsername(msg.getUsername());
				newUser.setAdded(true);
				
				data.getKnownUsers().remove(newUser);
				data.getAddedUsers().add(newUser);

				oamsg.setUsername(data.getLocalUser().getUsername());
				oamsg.setStatus(data.getLocalUser().getStatus());
				oamsg.setMsgStatus(OnAddMsg.STATUS.SUCCESS);
				
				client.addUser(newUser);
			}
			
			try {
				network.sendMessage(newUser, oamsg);
			} catch (MessageNotSentException e) {
				newUser.getUnsentMessages().add(oamsg);
			}
		} catch (InvalidParameterException e) { }
	}
	
	private void onAddMsg(OnAddMsg msg) {
		User user = data.getUser(msg.getToken());
		
		if (!isMessageLegit(user, msg.getToken()))
			return;

		switch(msg.getMsgStatus()) {
			default:
			case UNKNOWN_ERROR:
				if (user.getId() != null)
					break;
				
				data.getAddedUsers().remove(user);
				client.removeUser(user);
				break;
			case SUCCESS:
				try {
					user.setId(msg.getId());
					user.setUsername(msg.getUsername());
					user.setStatus(msg.getStatus());
					user.setAdded(true);
					
					client.updateUser(user);
				} catch (Exception e) {  }		
				break;
			case TRYING_TO_ADD_LOCAL_USER:
				data.getAddedUsers().remove(user);
				client.removeUser(user);
				break;
			case USER_ALREADY_ADDED:
				break;
		}
	}

	private void includedOnChatMsg(IncludedOnChatMsg msg) {
		User user = data.getUser(msg.getId());

		if (!isMessageLegit(user, msg.getToken()))
			return;

		try {
			Chat newChat = new Chat(msg.getName());
			newChat.setId(msg.getChatId());
			newChat.setStart(new Date(msg.getDate()));

			List<User> members = newChat.getMembers();
			
			for (int i = 0; i < msg.getMembersId().size(); i++) {
				String id = msg.getMembersId().get(i);
				
				if (id.equals(data.getLocalUser().getId())) {
					members.add(data.getLocalUser());
					continue;
				}
				
				User member = data.getUser(id);
				
				if (member == null) {
					String fullAddress = msg.getMembersAddress().get(i);
					
					String[] aux = fullAddress.split(":");
					String address = aux[0];
					int port = Integer.parseInt(aux[1]);
					
					member = new User();
					member.setId(id);
					member.setToken(newChat.getId());
					member.setAddress(address);
					member.setPort(port);
					member.setStatus(User.STATUS.UNKNOWN);
					member.getChats().add(newChat);
					
					members.add(member);
					
					data.getKnownUsers().add(member);
				} else {
					member.getChats().add(newChat);
					members.add(member);
				}
			}

			data.getChats().add(newChat);
			client.addChat(newChat);
		} catch (InvalidParameterException e) { }
	}
	
	private void messageMsg(MessageMsg msg) {
		User user = data.getUser(msg.getId());
		
		if (!isMessageLegit(user, msg.getToken()))
			return;
		
		Chat chat = data.getChat(msg.getChatId());
		
		if (chat == null)
			return;

		Message message = new Message();
		message.setChat(chat);
		message.setSender(user);
		message.setTime(new Date(msg.getTime()));
		message.setContent(msg.getContent());
		
		chat.getMessages().add(message);
		
		client.addMessage(message);
	}
	
	private void changeChatNameMsg(ChangeChatNameMsg msg) {
		User user = data.getUser(msg.getId());
		
		if (!isMessageLegit(user, msg.getToken()))
			return;
		
		Chat chat = data.getChat(msg.getChatId());
		
		if (chat == null)
			return;
		
		try {
			chat.setName(msg.getChatName());
			
			client.updateChat(chat);
		} catch (InvalidParameterException e) { }
	}
	
	private void leaveChatMsg(LeaveChatMsg msg) {
		User user = data.getUser(msg.getId());
		
		if (!isMessageLegit(user, msg.getToken()))
			return;
		
		Chat chat = data.getChat(msg.getChatId());
		
		if (chat == null)
			return;
		
		chat.getMembers().remove(user);
		user.getChats().remove(chat);
		
		if (!user.isAdded())
			if (user.getChats().size() == 0)
				data.getKnownUsers().remove(user);
	}
}
