package pl.edu.agh.toik.bughandler.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Utils {

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
						return new PasswordAuthentication(Const.EMAIL_LOGIN,
								Const.PASSWORD);
					}
				});

		try {
			Message message = new MimeMessage(session);
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(Const.RECIPIENTS));
			message.setSubject("[BugHandler] New error: "
					+ ex.getClass().getName() + ": " + ex.getMessage());

			String stackTrace = new StringBuilder()
					.append("Exception stack trace:" + "\n\n")
					.append(stackTraceAsString(ex))
					.append("\n\n" + "Details:\n")
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
					.append("\n\n\nBest regards,\nYour BugHandler Team").toString();

			message.setText(stackTrace);

			Transport.send(message);

			System.out.println("Bug report has been sent");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	private static String stackTraceAsString(Exception ex) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		String stackTrace = sw.toString(); // stack trace as a string
		return stackTrace;
	}
	
	public static void printSystemProperties()
	{
		System.getProperties().list(System.out);
	}

}
