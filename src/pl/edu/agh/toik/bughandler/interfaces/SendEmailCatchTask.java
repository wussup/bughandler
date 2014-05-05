package pl.edu.agh.toik.bughandler.interfaces;

import pl.edu.agh.toik.bughandler.util.Utils;

public class SendEmailCatchTask implements ICatchTask {

	@Override
	public void proceed(Exception ex) {
		System.out.println("[Info] Exception occured with Catch annotation:");
		ex.printStackTrace(System.err);
		Utils.sendEmailMessage(ex);
	}

}
