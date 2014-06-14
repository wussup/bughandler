package pl.edu.agh.toik.bughandler.interfaces;

import pl.edu.agh.toik.bughandler.util.ErrorType;

public class SecondCatchTask implements ITask {

	@Override
	public Boolean proceed(Exception ex, ErrorType errorType) {
		System.out.println("[Info] Exception occured with SecondCatchTask:");
		ex.printStackTrace(System.err);
		return null;
	}

}
