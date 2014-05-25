package pl.edu.agh.toik.bughandler.interfaces;

import pl.edu.agh.toik.bughandler.interfaces.ITask;

public class DefaultCatchTask implements ITask {

	@Override
	public void proceed(Exception ex) {
		System.out.println("[Info] Exception occured with DefaultCatchTask:");
		ex.printStackTrace(System.err);
	}

}
