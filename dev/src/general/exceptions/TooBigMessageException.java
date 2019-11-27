package general.exceptions;

public class TooBigMessageException extends Exception {

	private static final long serialVersionUID = 1035626395856364307L;

	public TooBigMessageException() { super(); }
	
	public TooBigMessageException(String message) { super(message); }
	
	public TooBigMessageException(String message, Throwable cause) { super(message, cause); }
	
	public TooBigMessageException(Throwable cause) { super(cause); }
}
