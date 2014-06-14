package pl.edu.agh.toik.bughandler.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import pl.edu.agh.toik.bughandler.util.ErrorType;

/**
 * @author Taras Melon & Jakub Kolodziej
 * 
 *         Annotation ErrorRepeat
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ErrorRepeat {
	/**
	 * Number of times to repeat
	 * 
	 * @return number of times to repeat
	 */
	int count() default 1;

	/**
	 * Gap between repeats
	 * 
	 * @return gap between repeats
	 */
	int time() default 0;

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
