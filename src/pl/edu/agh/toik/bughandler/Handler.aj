package pl.edu.agh.toik.bughandler;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import pl.edu.agh.toik.bughandler.util.Const;

public aspect Handler {

	void around(): call(void *.*(..)){
		try {
			proceed();
		} catch (Exception ex) {
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
						return new PasswordAuthentication(Const.EMAIL_LOGIN, Const.PASSWORD);
					}
				});
	 
			try {
	 
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress("kolotek13@gmail.com"));
				message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse("kolotek13@o2.pl"));
				message.setSubject("Error");
				message.setText("Dear Mail Crawler," +
						"\n\n here is the error message: " + ex.getMessage());
	 
				Transport.send(message);
	 
				System.out.println("Done");
	 
			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}
		}
	}

	
}
