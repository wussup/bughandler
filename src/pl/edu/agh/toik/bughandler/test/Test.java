package pl.edu.agh.toik.bughandler.test;

import pl.edu.agh.toik.bughandler.annotations.ErrorCatch;
import pl.edu.agh.toik.bughandler.annotations.ErrorIgnore;
import pl.edu.agh.toik.bughandler.annotations.ErrorLogToFile;
import pl.edu.agh.toik.bughandler.annotations.ErrorRepeat;
import pl.edu.agh.toik.bughandler.util.ErrorType;

public class Test {

	@ErrorRepeat(count = 3, time = 2000, handlerName = "DefaultCatchTask", onlyHandler = true, catchExceptions = { "Exception" }, errorType = ErrorType.MEDIUM)
	public static void testErrorRepeat() {
		throw new Exception("Fatal error repeat! Turn off your computer");
	}

	@ErrorCatch(handlerName = "DefaultCatchTask", onlyHandler = true, catchExceptions = {
			"ArrayIndexOutOfBoundsException", "Exception" }, errorType = ErrorType.MEDIUM)
	public static void testErrorCatch(boolean shouldRunWithoutAnnotations) {
		if (shouldRunWithoutAnnotations)
			testWithoutAnnotations();
		else
			throw new ArrayIndexOutOfBoundsException(
					"Fatal error catch! Turn off your computer");
	}

	@ErrorIgnore(catchExceptions = { "Exception" }, errorType = ErrorType.MEDIUM)
	public static void testErrorIgnore(boolean shouldRunErrorCatch) {
		if (shouldRunErrorCatch)
			testErrorCatch(false);
		else
			throw new Exception("Fatal error ignore! Turn off your computer");
	}

	public static void testWithoutAnnotations() {
		throw new Exception(
				"Fatal error without annotations! Turn off your computer");
	}

	@ErrorLogToFile(fileName = "logs.txt", handlerName = "DefaultCatchTask", onlyHandler = false, catchExceptions = { "Exception" }, errorType = ErrorType.MEDIUM)
	public static void testErrorLogToFile() {
		throw new Exception("Fatal error log to file! Turn off your computer");
	}

	public static void main(String[] args) {
		testErrorRepeat();
		testErrorCatch(true);
		testErrorCatch(false);
		testErrorIgnore(true);
		testErrorIgnore(false);
		testErrorLogToFile();
		testWithoutAnnotations();
	}

}
