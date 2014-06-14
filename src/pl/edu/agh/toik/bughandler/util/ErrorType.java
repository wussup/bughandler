package pl.edu.agh.toik.bughandler.util;

/**
 * @author Taras Melon & Jakub Kolodziej
 * 
 *         Enum with error types
 */
public enum ErrorType {

	/**
	 * Throw up caught exception
	 */
	EASY,
	/**
	 * Handle exception with caught annotation
	 */
	MEDIUM,
	/**
	 * Exit application
	 */
	CRITICAL;

}
