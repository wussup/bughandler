package pl.edu.agh.toik.bughandler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


import pl.edu.agh.toik.bughandler.annotations.Catch;
import pl.edu.agh.toik.bughandler.annotations.Ignore;
import pl.edu.agh.toik.bughandler.annotations.LogToFile;
import pl.edu.agh.toik.bughandler.annotations.Repeat;
import pl.edu.agh.toik.bughandler.util.Utils;

public aspect Handler {

	pointcut repeat(Repeat adn) : execution(@Repeat * *.*(..)) && @annotation(adn);

	pointcut catchAdn(Catch adn) : execution(@Catch * *.*(..)) && @annotation(adn);

	pointcut logToFileAdn(LogToFile adn) : execution (@LogToFile * *.*(..)) && @annotation(adn);

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
				if (!Utils.isEmptyString(adn.handlerName())) {
					Utils.invokeRepeatTask(adn, ex);
				}
				if (!adn.onlyHandler()) {
					System.out
							.println("[Info] Exception occured with Repeat annotation:");
					ex.printStackTrace(System.err);
				}
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

	Object around(LogToFile adn) : logToFileAdn(adn){
		try {
			return proceed(adn);
		} catch (Exception ex) {
			try {

				File folder = new File("LogFolder");
				if (!folder.exists()) {
					folder.mkdir();
				}
				File file = new File("LogFolder/" + adn.fileName());
				if (!file.exists()) {
					file.createNewFile();
				}

				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				ex.printStackTrace(pw);
				DateFormat formatter = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String sb = new StringBuilder("")
						.append("Exception stack trace:" + "\n\n")
						.append(sw.toString())
						.append("\n\n" + "Details:\n")
						.append("\n\tTime: " + formatter.format(new Date())
								+ "\n")
						.append("\tOS: " + System.getProperty("os.name"))
						.append("\n\tOS version: "
								+ System.getProperty("os.version"))
						.append("\n\tUser name: "
								+ System.getProperty("user.name"))
						.append("\n\tUser language: "
								+ System.getProperty("user.language"))
						.append("\n\tUser country: "
								+ System.getProperty("user.country"))
						.append("\n\tJava version: "
								+ System.getProperty("java.version"))
						.append("\n\tJava runtime version: "
								+ System.getProperty("java.runtime.version"))
						.append("\n\tJava VM version: "
								+ System.getProperty("java.vm.version"))
						.toString();

				FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(sb);
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
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
