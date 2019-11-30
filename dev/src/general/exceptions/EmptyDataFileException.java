package general.exceptions;

public class EmptyDataFileException extends Exception {

	private static final long serialVersionUID = -1741169718094023664L;
	
	public EmptyDataFileException() { super(); }
	
	public EmptyDataFileException(String message) { super(message); }
	
	public EmptyDataFileException(String message, Throwable cause) { super(message, cause); }
	
	public EmptyDataFileException(Throwable cause) { super(cause); }
}