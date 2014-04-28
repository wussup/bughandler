package pl.edu.agh.toik.bughandler;

import pl.edu.agh.toik.bughandler.util.Utils;

public aspect Handler {

	void around(): call(void *.*(..)){
		try {
			proceed();
		} catch (Exception ex) {
			ex.printStackTrace(System.err);
			Utils.sendEmailMessage(ex);
		}
	}

}
