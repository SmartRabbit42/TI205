package network.netMsg;

public class OnGreetingMsg extends NetMsg {

	private static final long serialVersionUID = 6895296448150060379L;

	private String address;
	private int port;
	
	public OnGreetingMsg() {
		setMessageType(NetMsg.MessageType.onGreeting);
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
