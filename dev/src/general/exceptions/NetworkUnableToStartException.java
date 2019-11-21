package general.exceptions;

public class NetworkUnableToStartException extends Exception {

	private static final long serialVersionUID = 1270759769751650844L;

	public NetworkUnableToStartException() { super(); }
	
	public NetworkUnableToStartException(String message) { super(message); }
	
	public NetworkUnableToStartException(String message, Throwable cause) { super(message, cause); }
	
	public NetworkUnableToStartException(Throwable cause) { super(cause); }
}
