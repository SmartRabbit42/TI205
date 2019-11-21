package general.exceptions;

public class DataNotLoadedException extends Exception {

	private static final long serialVersionUID = -7396707696749062453L;

	public DataNotLoadedException() { super(); }
	
	public DataNotLoadedException(String message) { super(message); }
	
	public DataNotLoadedException(String message, Throwable cause) { super(message, cause); }
	
	public DataNotLoadedException(Throwable cause) { super(cause); }
}
