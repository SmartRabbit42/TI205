package general.exceptions;

public class DisconnectedReceiverException extends Exception {

	private static final long serialVersionUID = -1741169718094023664L;
	
	public DisconnectedReceiverException() { super(); }
	
	public DisconnectedReceiverException(String message) { super(message); }
	
	public DisconnectedReceiverException(String message, Throwable cause) { super(message, cause); }
	
	public DisconnectedReceiverException(Throwable cause) { super(cause); }
}
