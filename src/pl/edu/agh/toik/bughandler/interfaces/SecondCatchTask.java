package pl.edu.agh.toik.bughandler.interfaces;

public class SecondCatchTask implements ITask {

	@Override
	public void proceed(Exception ex) {
		System.out.println("[Info] Exception occured with SecondCatchTask:");
		ex.printStackTrace(System.err);
	}

}
