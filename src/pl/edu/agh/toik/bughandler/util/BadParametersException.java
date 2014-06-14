package pl.edu.agh.toik.bughandler.util;

import org.aspectj.lang.SoftException;

/**
 * @author Taras Melon & Jakub Kolodziej
 * 
 *         Exception for bad parameters in annotations
 */
public class BadParametersException extends SoftException {

	/**
	 * Generated serial number
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * 
	 * @param ex
	 *            normal exception in SoftException
	 */
	public BadParametersException(Throwable ex) {
		super(ex);
	}

}
