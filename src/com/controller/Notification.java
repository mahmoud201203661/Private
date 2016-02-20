package com.controller;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Notification {
	public Notification() {
		
	}
	public static void sendMailToLateUsers(String userEmail , String bookISBN) {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		props.put("mail.smtp.socketFactory.fallback", "false");
		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					//return new PasswordAuthentication("engmahmoudmohamed779@gmail.com", "0125256740");
					return new PasswordAuthentication("fci.library.management@gmail.com","987654321M");
				}
			});

		try {
			session.setDebug(true);
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("fci.library.management@gmail.com"));
			message.setRecipient(Message.RecipientType.TO,
					new InternetAddress("mahmoudelprins22@gmail.com"));
			
			message.setSubject("Testing Subject");
			String messageContent = "Dear "+userEmail+", \n you must retun the Book That has ISBN :"+bookISBN +" Beause you have Exceeds Dead Line \n thank you "  ;
			message.setContent(messageContent, "text/html");
			Transport transport = session.getTransport("smtp");
			transport.connect("smtp.gmail.com", "fci.library.management@gmail.com", "987654321M");
			transport.send(message, message.getAllRecipients());
			
			System.out.println("Done");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
