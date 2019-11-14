package general.exceptions;

public class InvalidUsernameException extends Exception {

	private static final long serialVersionUID = 6265298321338569948L;

	public InvalidUsernameException() { super(); }
	
	public InvalidUsernameException(String message) { super(message); }
	
	public InvalidUsernameException(String message, Throwable cause) { super(message, cause); }
	
	public InvalidUsernameException(Throwable cause) { super(cause); }
}