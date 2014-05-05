package pl.edu.agh.toik.bughandler;

import pl.edu.agh.toik.bughandler.annotations.Catch;
import pl.edu.agh.toik.bughandler.annotations.Ignore;
import pl.edu.agh.toik.bughandler.annotations.Repeat;
import pl.edu.agh.toik.bughandler.util.Utils;

public aspect Handler {

	pointcut repeat(Repeat adn) : execution(@Repeat * *.*(..)) && @annotation(adn);

	pointcut catchAdn(Catch adn) : execution(@Catch * *.*(..)) && @annotation(adn);

	Object around(Catch adn): catchAdn(adn){
		try {
			return proceed(adn);
		} catch (Exception ex) {
			Utils.invokeCatchTask(adn, ex);
		}

		return null;
	}

	Object around(Repeat adn): repeat(adn)
	{
		int i = 0;
		while (i < adn.count()) {
			try {
				return proceed(adn);
			} catch (Exception ex) {
				System.out.println("[Info] Exception occured with Repeat annotation:");
				ex.printStackTrace(System.err);
			}
			i++;
			try {
				Thread.sleep(adn.time());
			} catch (InterruptedException ex) {
				ex.printStackTrace(System.err);
			}
		}
		return null;
	}

	Object around(): execution(@Ignore * *.*(..)){
		try {
			return proceed();
		} catch (Exception ex) {
			// ignore
		}
		return null;
	}

}
