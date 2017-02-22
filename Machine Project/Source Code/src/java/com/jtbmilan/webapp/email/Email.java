package com.jtbmilan.webapp.email;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/*
 *  Remember to allow access for less secure apps for
 *  the Gmail account that will send the message.
 *  
 *  See more at:
 *  https://support.google.com/accounts/answer/6010255
 */

public class Email {
	private MimeMessage message;
	
	public static class Builder {
		String senderEmail;
		String senderPassword;
		List<String> to;
		List<String> cc;
		List<String> bcc;
		String subject;
		String body;
		
		public Builder() {
			to = new ArrayList<>();
			cc = new ArrayList<>();
			bcc = new ArrayList<>();
		}
		
		public Builder senderEmail(String senderEmail) {
			this.senderEmail = senderEmail;
			return this;
		}
		
		public Builder senderPassword(String senderPassword) {
			this.senderPassword = senderPassword;
			return this;
		}
		
		public Builder to(String to) {
			this.to.add(to);
			return this;
		}
		
		public Builder cc(String cc) {
			this.cc.add(cc);
			return this;
		}
		
		public Builder bcc(String bcc) {
			this.bcc.add(bcc);
			return this;
		}
		
		public Builder subject(String subject) {
			this.subject = subject;
			return this;
		}
		
		public Builder body(String body) {
			this.body = body;
			return this;
		}
		
		public Email create() {
			if (this.senderEmail == null || this.senderPassword == null
					|| this.to.size() == 0 || this.subject == null) {
				throw new IllegalStateException();
			} else {
				return new Email(this);
			}
		}
	}
	
	private Email(Builder builder) {
		// TODO - refactor this, use EmailProvider ENUM to get properties
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
 
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(builder.senderEmail,
						builder.senderPassword);
			}
		  });
		
		try {
			message = new MimeMessage(session);
			message.setFrom(new InternetAddress(builder.senderEmail));
			for (String x : builder.to) {
			    message.addRecipient(Message.RecipientType.TO,
	                    new InternetAddress(x));
			}
			for (String x : builder.cc) {
			    message.addRecipient(Message.RecipientType.CC,
	                    new InternetAddress(x));
			}
			for (String x : builder.bcc) {
			    message.addRecipient(Message.RecipientType.BCC,
	                    new InternetAddress(x));
			}
			message.setSubject(builder.subject);
			message.setText(builder.body);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	public void send() {
		try {
			Transport.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
}
