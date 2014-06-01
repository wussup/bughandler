package pl.edu.agh.toik.bughandler.interfaces;

public class SecondCatchTask implements ITask {

	@Override
	public Boolean proceed(Exception ex) {
		System.out.println("[Info] Exception occured with SecondCatchTask:");
		ex.printStackTrace(System.err);
		return null;
	}

}
