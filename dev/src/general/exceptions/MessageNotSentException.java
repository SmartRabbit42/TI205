package general.exceptions;

public class MessageNotSentException extends Exception {

	private static final long serialVersionUID = -1741169718094023664L;
	
	public MessageNotSentException() { super(); }
	
	public MessageNotSentException(String message) { super(message); }
	
	public MessageNotSentException(String message, Throwable cause) { super(message, cause); }
	
	public MessageNotSentException(Throwable cause) { super(cause); }
}
