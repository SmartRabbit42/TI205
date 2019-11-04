package network.netMsg;

public class GreetingMsg extends NetMsg {

	private static final long serialVersionUID = 440000773731260800L;
	
	private String address;
	private int port;
	
	public GreetingMsg() {
		setMessageType(NetMsg.MessageType.greeting);
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
}
