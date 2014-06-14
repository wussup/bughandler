package pl.edu.agh.toik.bughandler.util;

import org.aspectj.lang.SoftException;

public class BadParametersException extends SoftException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BadParametersException(Throwable arg0) {
		super(arg0);
	}

}
