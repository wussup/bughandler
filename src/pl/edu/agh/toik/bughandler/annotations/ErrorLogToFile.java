package pl.edu.agh.toik.bughandler.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import pl.edu.agh.toik.bughandler.util.ErrorType;

/**
 * @author Taras Melon & Jakub Kolodziej
 * 
 *         Annotation ErrorLogToFile
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ErrorLogToFile {
	/**
	 * File name
	 * 
	 * @return file name
	 */
	String fileName() default "log.txt";

	/**
	 * Catch task name
	 * 
	 * @return catch task name
	 */
	String handlerName() default "";

	/**
	 * Only handler should be executed
	 * 
	 * @return only handler or not value
	 */
	boolean onlyHandler() default false;

	/**
	 * List of caught exceptions
	 * 
	 * @return caught exceptions
	 */
	String[] catchExceptions() default {};

	/**
	 * List of uncaught exceptions
	 * 
	 * @return uncaught exceptions
	 */
	String[] uncatchExceptions() default {};

	/**
	 * Error type
	 * 
	 * @return error type
	 */
	ErrorType errorType() default ErrorType.MEDIUM;
}
