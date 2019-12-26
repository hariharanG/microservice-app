package com.hari.xxx.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaxMailSender {

	private final Properties mailProps;
	private final static Logger logger = LoggerFactory.getLogger(JavaxMailSender.class);

	private final String username;
	private final String password;
	private final String senderName;

	public JavaxMailSender(String smtpHost, Integer smtpPort, String username, String password, String senderName,
			Map<String, String> properties) {
		this.username = username;
		this.senderName = senderName;
		mailProps = new Properties();
		mailProps.put("mail.smtp.host", smtpHost);
		mailProps.put("mail.smtp.port", smtpPort);
		this.password = password;
		mailProps.putAll(properties);
	}

	public static void main(String[] args) {
		Map<String, String> addProps = new HashMap<>();
		addProps.put("mail.smtp.auth", "true");
		addProps.put("mail.smtp.starttls.enable", "true");
		JavaxMailSender javaxMailSender = new JavaxMailSender("smtp.gmail.com", 587, "hariharan.ganpathy@gmail.com",
				"xxxx@123", "testing-cib123", addProps);
		javaxMailSender.sendMail("hariharan.ganpathy@gmail.com", "test", "hi", "/home/hariharan/Downloads/data.pdf");
	}

	public void sendMail(String toEmail, String subject, String messageText, String attachmentPath) {
		logger.info("MAIL Content : {} ", messageText);
		Session session = null;
		if (mailProps.get("mail.smtp.auth") != null && "true".equals(mailProps.get("mail.smtp.auth"))) {
			logger.info("Creating Mail Session using hasAuth...");
			String[] credentials = password.split(":");
			session = Session.getInstance(mailProps, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(credentials[0], credentials[1]);					
				}
			});
		} else {
			logger.info("Creating Mail Session....");
			session = Session.getInstance(mailProps);
		}
		session.setDebug(false);
		try {
			logger.info("Creating Message part...");
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(this.username, senderName));
			InternetAddress[] iaddress = InternetAddress.parse(toEmail);
			message.setRecipients(RecipientType.TO, iaddress);
			message.setSubject(subject);

			logger.info("Creating Message Body part...Content");
			// Create the message part
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(messageText, "text/html");
			// Create a multipart message
			Multipart multipart = new MimeMultipart();

			// Set text message part
			multipart.addBodyPart(messageBodyPart);
			logger.info("the attachment as part of this email is {} ", attachmentPath);
			if (attachmentPath != null) {
				logger.info("Creating Message Attachment part...Attachment");
				// Part two is attachment
				MimeBodyPart messageBodyPart1 = new MimeBodyPart();
				DataSource source = new FileDataSource(attachmentPath);
				messageBodyPart1.setDataHandler(new DataHandler(source));
				messageBodyPart1.setFileName(source.getName());
				multipart.addBodyPart(messageBodyPart1);
			}

			// Send the complete message parts
			message.setContent(multipart);
			logger.info("About to Send Mail to {}", toEmail);
			Transport.send(message);
			logger.info("Email send to {} Successfully", toEmail);
		} catch (Exception ex) {
			logger.error("ERROR sending email", ex);
			ex.printStackTrace();
		}
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

}
