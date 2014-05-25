package pl.edu.agh.toik.bughandler.test;

import pl.edu.agh.toik.bughandler.annotations.ErrorCatch;
import pl.edu.agh.toik.bughandler.annotations.ErrorIgnore;
import pl.edu.agh.toik.bughandler.annotations.ErrorLogToFile;
import pl.edu.agh.toik.bughandler.annotations.ErrorRepeat;

public class Test {

	@ErrorRepeat(count = 2, time = 2000, handlerName = "DefaultCatchTask", onlyHandler = true)
	public static void test() throws Exception {
		throw new Exception("Fatal error! Turn off your computer");
	}

	@ErrorCatch
	public static void test2() throws Exception {
		throw new Exception("Fatal error2! Turn off your computer");
	}

	@ErrorIgnore(handlerName = "DefaultCatchTask")
	public static void test3() throws Exception {
		throw new Exception("Fatal error3! Turn off your computer");
	}

	public static void test4() throws Exception {
		throw new Exception("Fatal error4! Turn off your computer");
	}

	@ErrorLogToFile(fileName = "lol.txt", handlerName = "DefaultCatchTask", onlyHandler = false)
	public static void test5() throws Exception {
		throw new Exception("Fatal error 5! Turn off your computer");
	}

	public static void main(String[] args) throws Exception {
		// test();
		// test2();
		// test3();
		test5();
	}

}
