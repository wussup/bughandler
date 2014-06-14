package pl.edu.agh.toik.bughandler.interfaces;

import pl.edu.agh.toik.bughandler.util.ErrorType;

public interface ITask {

	/**
	 * Make something with exception object
	 * 
	 * @param ex
	 *            exception object
	 * @return is onlyHandler
	 */
	Boolean proceed(Exception ex, ErrorType errorType);

}
