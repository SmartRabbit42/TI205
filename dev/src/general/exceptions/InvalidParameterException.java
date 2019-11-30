package general.exceptions;

public class InvalidParameterException extends Exception {

	private static final long serialVersionUID = 6265298321338569948L;

	public InvalidParameterException() { super(); }
	
	public InvalidParameterException(String message) { super(message); }
	
	public InvalidParameterException(String message, Throwable cause) { super(message, cause); }
	
	public InvalidParameterException(Throwable cause) { super(cause); }
}