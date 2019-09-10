package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Network {
	
	public static boolean connected;
	public static boolean listening;
	
	private static boolean updating;
	
	public static String localIp;
	public static int localPort;
	
	private static ServerSocket serverSocket;
	
	public static void Connect() throws IOException {
		serverSocket = new ServerSocket();
		
		UpdateMessagePump();
		
		listening = true;
		connected = true;
	}
	
	public static void Disconnect() {
		
	}
	
	public static void SendMessage() {
		
	}
	
	public static void UpdateMessagePump() {
		Runnable monitor = new Runnable() {
			@Override
			public void run() {
				if (updating)
					return;
				
				updating = true;
				
				while(listening) {
					try {
						Socket sender = serverSocket.accept();
						Runnable handler = new MessageHandler(sender);
						new Thread(handler).start();
					} catch (Exception e) { }
				}
				
				updating = false;
			}
		};
		new Thread(monitor).start();
	}
}
