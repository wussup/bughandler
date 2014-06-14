package pl.edu.agh.toik.bughandler.test;

import pl.edu.agh.toik.bughandler.annotations.ErrorCatch;
import pl.edu.agh.toik.bughandler.annotations.ErrorIgnore;
import pl.edu.agh.toik.bughandler.annotations.ErrorLogToFile;
import pl.edu.agh.toik.bughandler.annotations.ErrorRepeat;
import pl.edu.agh.toik.bughandler.util.ErrorType;

public class Test {

	@ErrorRepeat(count = 3, time = 2000, handlerName = "DefaultCatchTask", onlyHandler = true)
	public static void test() {
		throw new Exception("Fatal error! Turn off your computer");
	}

	@ErrorCatch(handlerName = "DefaultCatchTask", onlyHandler = true, uncatchExceptions = { "ArrayIndexOutOfBoundsException" })
	public static void test2() {
		throw new ArrayIndexOutOfBoundsException(
				"Fatal error2! Turn off your computer");
	}

	@ErrorIgnore()
	public static void test3() {
		test4();
		//throw new Exception("Fatal error3! Turn off your computer");
	}

	public static void test4() {
		throw new Exception("Fatal error4! Turn off your computer");
	}

	@ErrorLogToFile(fileName = "lol.txt", handlerName = "DefaultCatchTask", onlyHandler = false)
	public static void test5() {
		throw new Exception("Fatal error 5! Turn off your computer");
	}

	public static void test6() throws Exception {
		throw new ArrayIndexOutOfBoundsException(
				"Fatal error 6! Turn off your computer");
	}
public static void main(String[] args) throws Exception {
//		test();
//		test2();
//		test3();
//		test4();
		//test6();
		test3();
	}

}
