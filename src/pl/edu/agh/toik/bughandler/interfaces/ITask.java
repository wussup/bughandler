package pl.edu.agh.toik.bughandler.interfaces;

public interface ITask {

	/**
	 * Make something with exception object
	 * 
	 * @param ex
	 *            exception object
	 * @return is onlyHandler
	 */
	Boolean proceed(Exception ex);

}
