package pl.edu.agh.toik.bughandler.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import pl.edu.agh.toik.bughandler.annotations.ErrorCatch;
import pl.edu.agh.toik.bughandler.annotations.ErrorIgnore;
import pl.edu.agh.toik.bughandler.annotations.ErrorLogToFile;
import pl.edu.agh.toik.bughandler.annotations.ErrorRepeat;
import pl.edu.agh.toik.bughandler.interfaces.ITask;

public class Utils {

	public static Boolean invokeCatchTask(String handlerName, Exception ex) {
		List<Class<?>> processorCandidates = ReflectionHelper
				.findClassesImpmenenting(ITask.class, ITask.class.getPackage());
		String taskClassName;
		if (isContainName(processorCandidates, handlerName))
			taskClassName = handlerName;
		else
			taskClassName = "DefaultCatchTask";
		for (Class<?> c : processorCandidates) {
			String[] splittedName = c.getName().split("\\.");
			if (splittedName[splittedName.length - 1].equals(taskClassName)) {
				Method[] allMethods = c.getDeclaredMethods();
				for (Method m : allMethods) {
					if (m.getName().equals("proceed")) {
						try {
							return (Boolean) m.invoke(c.newInstance(), ex,
									ErrorType.MEDIUM);
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						} catch (InstantiationException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return null;
	}

	public static Boolean invokeCatchTask(ErrorCatch adn, Exception ex) {
		List<Class<?>> processorCandidates = ReflectionHelper
				.findClassesImpmenenting(ITask.class, ITask.class.getPackage());
		String taskClassName;

		taskClassName = adn.handlerName();

		for (Class<?> c : processorCandidates) {
			String[] splittedName = c.getName().split("\\.");
			if (splittedName[splittedName.length - 1].equals(taskClassName)) {
				Method[] allMethods = c.getDeclaredMethods();
				for (Method m : allMethods) {
					if (m.getName().equals("proceed")) {
						try {
							return (Boolean) m.invoke(c.newInstance(), ex,
									adn.errorType());
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						} catch (InstantiationException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return null;
	}

	public static Boolean invokeRepeatTask(ErrorRepeat adn, Exception ex) {
		List<Class<?>> processorCandidates = ReflectionHelper
				.findClassesImpmenenting(ITask.class, ITask.class.getPackage());
		String taskClassName;

		taskClassName = adn.handlerName();

		for (Class<?> c : processorCandidates) {
			String[] splittedName = c.getName().split("\\.");
			if (splittedName[splittedName.length - 1].equals(taskClassName)) {
				Method[] allMethods = c.getDeclaredMethods();
				for (Method m : allMethods) {
					if (m.getName().equals("proceed")) {
						try {
							return (Boolean) m.invoke(c.newInstance(), ex,
									adn.errorType());
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						} catch (InstantiationException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return null;
	}

	public static Boolean invokeLogToFileTask(ErrorLogToFile adn, Exception ex) {
		List<Class<?>> processorCandidates = ReflectionHelper
				.findClassesImpmenenting(ITask.class, ITask.class.getPackage());
		String taskClassName;

		taskClassName = adn.handlerName();

		for (Class<?> c : processorCandidates) {
			String[] splittedName = c.getName().split("\\.");
			if (splittedName[splittedName.length - 1].equals(taskClassName)) {
				Method[] allMethods = c.getDeclaredMethods();
				for (Method m : allMethods) {
					if (m.getName().equals("proceed")) {
						try {
							return (Boolean) m.invoke(c.newInstance(), ex,
									adn.errorType());
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						} catch (InstantiationException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return null;
	}

	public static Boolean invokeIgnoreTask(ErrorIgnore adn, Exception ex) {
		List<Class<?>> processorCandidates = ReflectionHelper
				.findClassesImpmenenting(ITask.class, ITask.class.getPackage());
		String taskClassName;

		taskClassName = adn.handlerName();

		for (Class<?> c : processorCandidates) {
			String[] splittedName = c.getName().split("\\.");
			if (splittedName[splittedName.length - 1].equals(taskClassName)) {
				Method[] allMethods = c.getDeclaredMethods();
				for (Method m : allMethods) {
					if (m.getName().equals("proceed")) {
						try {
							return (Boolean) m.invoke(c.newInstance(), ex,
									adn.errorType());
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						} catch (InstantiationException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return null;
	}

	private static boolean isContainName(List<Class<?>> list, String name) {
		for (Class<?> c : list) {
			String[] splittedName = c.getName().split("\\.");
			if (splittedName[splittedName.length - 1].equals(name)) {
				return true;
			}
		}
		System.out.println("[Warning] Can not find class " + name
				+ ". Replace class name with DefaultCatchTask.");
		return false;
	}

	public static void sendEmailMessage(Exception ex) {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication("", "");
					}
				});

		try {
			Message message = new MimeMessage(session);
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(""));
			message.setSubject("[BugHandler] New error: "
					+ ex.getClass().getName() + ": " + ex.getMessage());

			String stackTrace = new StringBuilder()
					.append("Exception stack trace:" + "\n\n")
					.append(stackTraceAsString(ex))
					.append("\n\n" + "Details:\n")
					.append("\tOS: " + System.getProperty("os.name"))
					.append("\n\tOS version: "
							+ System.getProperty("os.version"))
					.append("\n\tUser name: " + System.getProperty("user.name"))
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
					.append("\n\n\nBest regards,\nYour BugHandler Team")
					.toString();

			message.setText(stackTrace);

			Transport.send(message);

			System.out.println("[Info] Bug report has been sent");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	public static boolean isEmptyString(String str) {
		return str == null || str.isEmpty();
	}

	private static String stackTraceAsString(Exception ex) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		String stackTrace = sw.toString(); // stack trace as a string
		return stackTrace;
	}

	public static void printSystemProperties() {
		System.getProperties().list(System.out);
	}

}
