package general.exceptions;

public class NetworkUnableToShutException extends Exception {

	private static final long serialVersionUID = 4219158273315494682L;

	public NetworkUnableToShutException() { super(); }
	
	public NetworkUnableToShutException(String message) { super(message); }
	
	public NetworkUnableToShutException(String message, Throwable cause) { super(message, cause); }
	
	public NetworkUnableToShutException(Throwable cause) { super(cause); }
}