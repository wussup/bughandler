package pl.edu.agh.toik.bughandler.interfaces;

import pl.edu.agh.toik.bughandler.interfaces.ITask;

public class DefaultCatchTask implements ITask {

	@Override
	public void proceed(Exception ex) {
		System.out.println("[Info] LALALALA Exception occured with Catch annotation:");
		ex.printStackTrace(System.err);
	}

}
