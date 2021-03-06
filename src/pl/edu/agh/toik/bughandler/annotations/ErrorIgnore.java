package pl.edu.agh.toik.bughandler.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import pl.edu.agh.toik.bughandler.util.ErrorType;

/**
 * @author Taras Melon & Jakub Kolodziej
 * 
 *         Annotation ErrorIgnore
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ErrorIgnore {
	/**
	 * Catch task name
	 * 
	 * @return catch task name
	 */
	String handlerName() default "";

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
