package general.exceptions;

public class DataNotSavedException extends Exception {

	private static final long serialVersionUID = -2689476347865981978L;

	public DataNotSavedException() { super(); }
	
	public DataNotSavedException(String message) { super(message); }
	
	public DataNotSavedException(String message, Throwable cause) { super(message, cause); }
	
	public DataNotSavedException(Throwable cause) { super(cause); }
}
