package network;

import java.io.IOException;
import java.net.ServerSocket;

public class Network {
	public static boolean connected;
	
	public static String localIp;
	public static int localPort;
	
	private static ServerSocket serverSocket;
	
	public static void Connect() throws IOException {
		serverSocket = new ServerSocket();
		
		UpdateMessagePump();
		
		connected = true;
	}
	
	public static void Disconnect() {
		
	}
	
	public static void UpdateMessagePump() {
		
	}
	
	public static void SendMessage() {
		
	}
}
