package pl.edu.agh.toik.bughandler.interfaces;

import pl.edu.agh.toik.bughandler.util.ErrorType;

/**
 * @author Taras Melon & Jakub Kolodziej
 * 
 *         Interface for tasks in annotations
 */
public interface ITask {

	/**
	 * Make something with exception object
	 * 
	 * @param ex
	 *            exception object
	 * @param errorType
	 *            type of error
	 * @return is onlyHandler
	 */
	Boolean proceed(Exception ex, ErrorType errorType);

}
