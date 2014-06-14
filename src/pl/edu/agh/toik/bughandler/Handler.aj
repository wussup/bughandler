package pl.edu.agh.toik.bughandler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import pl.edu.agh.toik.bughandler.annotations.ErrorCatch;
import pl.edu.agh.toik.bughandler.annotations.ErrorIgnore;
import pl.edu.agh.toik.bughandler.annotations.ErrorLogToFile;
import pl.edu.agh.toik.bughandler.annotations.ErrorRepeat;
import pl.edu.agh.toik.bughandler.util.BadParametersException;
import pl.edu.agh.toik.bughandler.util.ErrorType;
import pl.edu.agh.toik.bughandler.util.Settings;
import pl.edu.agh.toik.bughandler.util.Utils;

public aspect Handler {

	public static boolean shouldIgnore;
	public static boolean shouldLog;
	public static String catchTask;
	public static String[] catchExceptionsArr;
	public static String[] uncatchExceptionsArr;

	public Handler() {
		Map<String, String> settings = new Settings().getSettings();
		String ignore = "shouldIgnore";
		String log = "shouldLog";
		String task = "catchTask";
		String catchExceptionsStr = "catchExceptions";
		String uncatchExceptionsStr = "uncatchExceptions";
		shouldIgnore = settings.containsKey(ignore) ? Boolean.valueOf(settings
				.get(ignore)) : true;
		shouldLog = settings.containsKey(log) ? Boolean.valueOf(settings
				.get(log)) : true;
		catchTask = settings.containsKey(task) ? settings.get(task) : null;
		catchExceptionsArr = settings.containsKey(catchExceptionsStr) ? settings
				.get(catchExceptionsStr).split(",") : new String[] {};
		uncatchExceptionsArr = settings.containsKey(uncatchExceptionsStr) ? settings
				.get(uncatchExceptionsStr).split(",") : new String[] {};
	}

	pointcut catched(): execution(* *.*(..)) && if(catchTask != null);

	pointcut repeat(ErrorRepeat adn) : execution(@ErrorRepeat * *.*(..)) && @annotation(adn);

	pointcut catchAdn(ErrorCatch adn) : execution(@ErrorCatch * *.*(..)) && @annotation(adn);

	pointcut logToFileAdn(ErrorLogToFile adn) : execution (@ErrorLogToFile * *.*(..)) && @annotation(adn) && if(shouldLog);

	pointcut ignoreAdn(ErrorIgnore adn) : execution (@ErrorIgnore * *.*(..)) && @annotation(adn) && if(shouldIgnore);

	Object around(): catched()
	{
		try {
			return proceed();
		} catch (Exception ex) {
			ArrayList<String> catchExceptions = new ArrayList<String>(
					Arrays.asList(catchExceptionsArr));
			ArrayList<String> uncatchExceptions = new ArrayList<String>(
					Arrays.asList(uncatchExceptionsArr));
			String exceptionClass = ex.getClass().getSimpleName();
			if ((!catchExceptions.isEmpty() && uncatchExceptions.isEmpty())
					|| (!catchExceptions.isEmpty() && !uncatchExceptions
							.isEmpty())) {
				if (catchExceptions.contains(exceptionClass)) {
					Utils.invokeCatchTask(catchTask, ex);
				} else {
					// ignore
				}
			} else if (catchExceptions.isEmpty()
					&& !uncatchExceptions.isEmpty()) {
				if (uncatchExceptions.contains(exceptionClass)) {
					// ignore
				} else {
					Utils.invokeCatchTask(catchTask, ex);
				}
			} else {
				Utils.invokeCatchTask(catchTask, ex);
			}
		}
		return null;
	}

	Object around(ErrorCatch adn) throws BadParametersException: catchAdn(adn){
		ErrorType errorType = adn.errorType();
		if (errorType == ErrorType.EASY)
			return proceed(adn);
		else {
			try {
				return proceed(adn);
			} catch (Exception ex) {
				ArrayList<String> catchExceptions = new ArrayList<String>(
						Arrays.asList(adn.catchExceptions()));
				ArrayList<String> uncatchExceptions = new ArrayList<String>(
						Arrays.asList(adn.uncatchExceptions()));
				if (!catchExceptions.isEmpty() && !uncatchExceptions.isEmpty())
					throw new BadParametersException(
							"You should not pass parameters catchExceptions and uncatchExceptions together");
				else {
					String exceptionClass = ex.getClass().getSimpleName();
					if (!catchExceptions.isEmpty()
							&& uncatchExceptions.isEmpty()) {
						if (catchExceptions.contains(exceptionClass)) {
							proceedCatch(adn, ex);
						} else {
							// ignore
						}
					} else if (catchExceptions.isEmpty()
							&& !uncatchExceptions.isEmpty()) {
						if (uncatchExceptions.contains(exceptionClass)) {
							// ignore
						} else {
							proceedCatch(adn, ex);
						}
					} else {
						proceedCatch(adn, ex);
					}
				}
				if (errorType == ErrorType.CRITICAL)
					System.exit(1);
			}
			return null;
		}
	}

	private void proceedCatch(ErrorCatch adn, Exception ex) {
		Boolean returnValue = false;
		if (!Utils.isEmptyString(adn.handlerName())) {
			returnValue = Utils.invokeCatchTask(adn.handlerName(), ex);
		}
		if ((returnValue == null && !adn.onlyHandler())
				|| (returnValue != null && !returnValue)) {
			System.out
					.println("[Info] Exception occured with Catch annotation:");
			ex.printStackTrace(System.err);
		}
	}

	Object around(ErrorRepeat adn) throws BadParametersException: repeat(adn)
	{
		int i = 0;
		while (i < adn.count()) {
			try {
				return proceed(adn);
			} catch (Exception ex) {
				ArrayList<String> catchExceptions = new ArrayList<String>(
						Arrays.asList(adn.catchExceptions()));
				ArrayList<String> uncatchExceptions = new ArrayList<String>(
						Arrays.asList(adn.uncatchExceptions()));
				if (!catchExceptions.isEmpty() && !uncatchExceptions.isEmpty())
					throw new BadParametersException(
							"You should not pass parameters catchExceptions and uncatchExceptions together");
				else {
					String exceptionClass = ex.getClass().getSimpleName();
					if (!catchExceptions.isEmpty()
							&& uncatchExceptions.isEmpty()) {
						if (catchExceptions.contains(exceptionClass)) {
							proceedRepeat(adn, ex);
						} else {
							// ignore
						}
					} else if (catchExceptions.isEmpty()
							&& !uncatchExceptions.isEmpty()) {
						if (uncatchExceptions.contains(exceptionClass)) {
							// ignore
						} else {
							proceedRepeat(adn, ex);
						}
					} else {
						proceedRepeat(adn, ex);
					}
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

	private void proceedRepeat(ErrorRepeat adn, Exception ex) {
		Boolean returnValue = false;
		if (!Utils.isEmptyString(adn.handlerName())) {
			returnValue = Utils.invokeRepeatTask(adn, ex);
		}
		if ((returnValue == null && !adn.onlyHandler())
				|| (returnValue != null && !returnValue)) {
			System.out
					.println("[Info] Exception occured with Repeat annotation:");
			ex.printStackTrace(System.err);
		}
	}

	Object around(ErrorLogToFile adn) throws BadParametersException : logToFileAdn(adn){
		try {
			return proceed(adn);
		} catch (Exception ex) {
			ArrayList<String> catchExceptions = new ArrayList<String>(
					Arrays.asList(adn.catchExceptions()));
			ArrayList<String> uncatchExceptions = new ArrayList<String>(
					Arrays.asList(adn.uncatchExceptions()));
			if (!catchExceptions.isEmpty() && !uncatchExceptions.isEmpty())
				throw new BadParametersException(
						"You should not pass parameters catchExceptions and uncatchExceptions together");
			else {
				String exceptionClass = ex.getClass().getSimpleName();
				if (!catchExceptions.isEmpty() && uncatchExceptions.isEmpty()) {
					if (catchExceptions.contains(exceptionClass)) {
						proceedLog(adn, ex);
					} else {
						// ignore
					}
				} else if (catchExceptions.isEmpty()
						&& !uncatchExceptions.isEmpty()) {
					if (uncatchExceptions.contains(exceptionClass)) {
						// ignore
					} else {
						proceedLog(adn, ex);
					}
				} else {
					proceedLog(adn, ex);
				}
			}
		}
		return null;
	}

	private void proceedLog(ErrorLogToFile adn, Exception ex) {
		try {
			Boolean returnValue = false;
			if (!Utils.isEmptyString(adn.handlerName())) {
				returnValue = Utils.invokeLogToFileTask(adn, ex);
			}

			if ((returnValue == null && !adn.onlyHandler())
					|| (returnValue != null && !returnValue)) {
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
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	Object around(ErrorIgnore adn) throws BadParametersException: ignoreAdn(adn){
		try {
			return proceed(adn);
		} catch (Exception ex) {
			ArrayList<String> catchExceptions = new ArrayList<String>(
					Arrays.asList(adn.catchExceptions()));
			ArrayList<String> uncatchExceptions = new ArrayList<String>(
					Arrays.asList(adn.uncatchExceptions()));
			if (!catchExceptions.isEmpty() && !uncatchExceptions.isEmpty())
				throw new BadParametersException(
						"You should not pass parameters catchExceptions and uncatchExceptions together");
			else {
				String exceptionClass = ex.getClass().getSimpleName();
				if (!catchExceptions.isEmpty() && uncatchExceptions.isEmpty()) {
					if (catchExceptions.contains(exceptionClass)) {
						proceedIgnore(adn, ex);
					} else {
						// ignore
					}
				} else if (catchExceptions.isEmpty()
						&& !uncatchExceptions.isEmpty()) {
					if (uncatchExceptions.contains(exceptionClass)) {
						// ignore
					} else {
						proceedIgnore(adn, ex);
					}
				} else {
					proceedIgnore(adn, ex);
				}
			}
		}
		return null;
	}

	private void proceedIgnore(ErrorIgnore adn, Exception ex) {
		if (!Utils.isEmptyString(adn.handlerName())) {
			Utils.invokeIgnoreTask(adn, ex);
		}
	}

}
