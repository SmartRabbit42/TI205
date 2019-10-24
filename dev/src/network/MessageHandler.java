package network;

import java.io.ObjectInputStream;
import java.net.Socket;

import network.netMsg.NetMsg;

public class MessageHandler implements Runnable {

	private Socket sender;
	
	public MessageHandler(Socket sender) {
		this.sender = sender;
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
		}
	}
	
	// Handlers
	private void OnConnectionMsg() {
		
	}
}
