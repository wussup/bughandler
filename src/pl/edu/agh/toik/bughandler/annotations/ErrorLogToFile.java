package pl.edu.agh.toik.bughandler.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ErrorLogToFile {
	String fileName() default "log.txt";

	String handlerName() default "";

	boolean onlyHandler() default false;

	String[] catchExceptions() default {};

	String[] uncatchExceptions() default {};
}
