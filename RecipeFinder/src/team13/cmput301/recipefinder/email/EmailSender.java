package team13.cmput301.recipefinder.email;

import java.util.Date;
import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.MailcapCommandMap;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


/**
 * Sends a email to the recipient using the admin's email.
 * 
 * CMPUT301 W13 T13
 * @author Han (Jim) Wen, Jessica Yuen, Shen Wei Liao, Fangyu Li
 */
public class EmailSender extends javax.mail.Authenticator { 
	private String email; 
	private String pass; 
	
	private String port; 
	private String sport; 

	private String host; 

	private Multipart multipart; 

	/**
	 * Constructor for creating a email sender. Uses the email
	 * in the email's settings.
	 */
	public EmailSender() { 
		host = "smtp.gmail.com";
		port = "465";
		sport = "465";
		email = "cmput301w13t13@gmail.com"; 
		pass = "ualberta"; 
		
		multipart = new MimeMultipart(); 

		// There is something wrong with MailCap, javamail cannot find a 
		// handler for the multipart/mixed part, so this bit needs to be added.
		MailcapCommandMap mc = 
				(MailcapCommandMap) CommandMap.getDefaultCommandMap(); 
		mc.addMailcap("text/html;; " +
				"x-java-content-handler=com.sun.mail.handlers.text_html"); 
		mc.addMailcap("text/xml;; " +
				"x-java-content-handler=com.sun.mail.handlers.text_xml"); 
		mc.addMailcap("text/plain;; " +
				"x-java-content-handler=com.sun.mail.handlers.text_plain"); 
		mc.addMailcap("multipart/*;; " +
				"x-java-content-handler=com.sun.mail.handlers.multipart_mixed"); 
		mc.addMailcap("message/rfc822;; " +
				"x-java-content-handler=com.sun.mail.handlers.message_rfc822"); 
		CommandMap.setDefaultCommandMap(mc); 
	} 

	public EmailSender(String email, String pass, String host, 
			String port, String sport) {
		this();
		this.email = email;
		this.pass = pass;
		this.host = host;
		this.port = port;
		this.sport = sport;
	}
	
	/**
	 * Sends the email to the recipient
	 * @param subject The subject of the email
	 * @param body The body of the email
	 * @param to The recipient
	 * @return true if successfully sent, false otherwise
	 */
	public boolean send(final String subject, final String body, 
			final String[] to) {
		
		final Properties props = setProperties(); 
		SendEmail sendEmail = new SendEmail(props, EmailSender.this, email, 
				subject, body, to, multipart);
		
		if(!to.equals("")) { 
			Thread thread = new Thread(sendEmail);
			thread.start();
			try {
				thread.join();
			} catch (InterruptedException e) {
				return false;
			}
		}
		
		if (sendEmail.isSent())
			return true;
		else
			return false;
	} 

	/**
	 * Add an attachment to the email
	 * @param filename The path where the photo is located
	 * @throws Exception if a problem occured while attaching to the email
	 */
	public void addAttachment(String filename) throws Exception { 
		BodyPart messageBodyPart = new MimeBodyPart(); 
		DataSource source = new FileDataSource(filename); 
		messageBodyPart.setDataHandler(new DataHandler(source)); 
		messageBodyPart.setFileName(filename); 

		multipart.addBodyPart(messageBodyPart); 
	} 

	@Override 
	/**
	 * Authenticate email email and email password
	 */
	public PasswordAuthentication getPasswordAuthentication() { 
		return new PasswordAuthentication(email, pass); 
	} 

	/**
	 * Set server properties
	 * @return properties of the server
	 */
	private Properties setProperties() { 
		Properties props = new Properties(); 

		props.put("mail.smtp.host", host); 
		props.put("mail.smtp.auth", "true"); 

		props.put("mail.smtp.port", port); 
		props.put("mail.smtp.socketFactory.port", sport); 
		props.put("mail.smtp.socketFactory.class", 
				"javax.net.ssl.SSLSocketFactory"); 
		props.put("mail.smtp.socketFactory.fallback", "false"); 

		return props; 
	} 
	
	/**
	 * Inner class that implements the Runnable interface for 
	 * sending a email.
	 */
	private static class SendEmail implements Runnable {
		
		private Properties props;
		private Authenticator auth;
		private String email, subject, body;
		private String[] to;
		private Multipart multipart;
		private boolean sent = false;
		
		/**
		 * Constructor for SendEmail
		 * @param props Properties for the email server
		 * @param auth Authentication for the email and password
		 * @param email Sender's email
		 * @param subject Subject of the email
		 * @param body Body of the email
		 * @param to Recipients of the email
		 * @param multipart Multipart for the body of the message
		 */
		public SendEmail(Properties props, Authenticator auth,
				String email, String subject,
				String body, String[] to, Multipart multipart) {
			this.props = props;
			this.auth = auth;
			this.email = email;
			this.subject = subject;
			this.body = body;
			this.to = to;
			this.multipart = multipart;
		}
		
		/**
		 * Run the thread - send the email
		 */
		public void run() {
			Session session = Session.getInstance(props, auth); 

			MimeMessage msg = new MimeMessage(session); 
			
			try {
				InternetAddress[] addressesTo = new InternetAddress[to.length];
				for (int i = 0; i < to.length; i++) {
					addressesTo[i] = new InternetAddress(to[i]);
				}
				
				msg.setFrom(new InternetAddress(email)); 
				msg.setSubject(subject); 
				msg.setSentDate(new Date()); 
				msg.setRecipients(MimeMessage.RecipientType.TO, addressesTo);
				
				// setup message body 
				BodyPart messageBodyPart = new MimeBodyPart(); 
				messageBodyPart.setText(body); 
				multipart.addBodyPart(messageBodyPart); 

				// Put parts in message 
				msg.setContent(multipart); 

				// send email 
				Transport.send(msg); 
				sent = true;
			} catch (Exception e) {
				return;
			}
		}
		
		/**
		 * @return Whether the email was sent.
		 */
		public boolean isSent() {
			return sent;
		}
	}
} 