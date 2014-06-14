package pl.edu.agh.toik.bughandler.interfaces;

import pl.edu.agh.toik.bughandler.interfaces.ITask;
import pl.edu.agh.toik.bughandler.util.ErrorType;

/**
 * @author Taras Melon & Jakub Kolodziej
 * 
 *         Default task for annotations
 */
public class DefaultCatchTask implements ITask {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pl.edu.agh.toik.bughandler.interfaces.ITask#proceed(java.lang.Exception)
	 */
	@Override
	public Boolean proceed(Exception ex, ErrorType errorType) {
		System.out.println("[Info] Exception occured with DefaultCatchTask:");
		ex.printStackTrace(System.err);
		return null;
	}

}
