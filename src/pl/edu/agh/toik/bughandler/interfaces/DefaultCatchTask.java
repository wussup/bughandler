package pl.edu.agh.toik.bughandler.interfaces;

import pl.edu.agh.toik.bughandler.interfaces.ITask;

public class DefaultCatchTask implements ITask {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pl.edu.agh.toik.bughandler.interfaces.ITask#proceed(java.lang.Exception)
	 */
	@Override
	public Boolean proceed(Exception ex) {
		System.out.println("[Info] Exception occured with DefaultCatchTask:");
		ex.printStackTrace(System.err);
		return null;
	}

}
