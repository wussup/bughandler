package pl.edu.agh.toik.bughandler.test;

public class Test {

	public static void test() throws Exception {
		throw new Exception("Fatal error! Turn off your computer");
	}

	public static void main(String[] args) throws Exception {
		test();
	}

}
