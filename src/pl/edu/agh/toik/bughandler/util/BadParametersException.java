package pl.edu.agh.toik.bughandler.util;

public class BadParametersException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BadParametersException() {
		super();
	}

	public BadParametersException(String arg0, Throwable arg1, boolean arg2,
			boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public BadParametersException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public BadParametersException(String arg0) {
		super(arg0);
	}

	public BadParametersException(Throwable arg0) {
		super(arg0);
	}

}
