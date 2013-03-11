/**
 * Sends a email to the recipient using the User's email.
 * 
 * CMPUT301 W13 T13
 * @author Han (Jim) Wen, Jessica Yuen, Shen Wei Liao, Fangyu Li
 */

package team13.cmput301.recipefinder;

import javax.activation.DataHandler;   
import javax.activation.DataSource;   
import javax.mail.Message;   
import javax.mail.PasswordAuthentication;   
import javax.mail.Session;   
import javax.mail.Transport;   
import javax.mail.internet.InternetAddress;   
import javax.mail.internet.MimeMessage;   
import java.io.ByteArrayInputStream;   
import java.io.IOException;   
import java.io.InputStream;   
import java.io.OutputStream;   
import java.security.Security;   
import java.util.Properties;   

public class EmailSender extends javax.mail.Authenticator {
	
	private String to;
	private String from;
	private String password;
	private String host; 
	private Session session;

    static {   
        Security.addProvider(new JSSEProvider());   
    }  
    
    /**
     * Constructor for email sender
     * @param to The email to have the recipe to be sent to
     */
	public EmailSender(String to) {
		this.to = to;
		this.from = User.getUser().getEmail();
		this.password = User.getUser().getEmailPassword();
		this.host = "smtp.gmail.com";
		
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");   
        props.setProperty("mail.host", host);   
        props.put("mail.smtp.auth", "true");   
        props.put("mail.smtp.port", "465");   
        props.put("mail.smtp.socketFactory.port", "465");   
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");   
        props.put("mail.smtp.socketFactory.fallback", "false");   
        props.setProperty("mail.smtp.quitwait", "false");   

        session = Session.getDefaultInstance(props, this);  
	} 

	/**
	 * Ensures password is valid for user email
	 */
    protected PasswordAuthentication getPasswordAuthentication() {   
        return new PasswordAuthentication(from, password);   
    }   

    /**
     * Send the email
     * @param subject
     * @param body
     * @throws Exception
     */
    public synchronized void sendMail(String subject, String body)
    		throws Exception {   

        MimeMessage message = new MimeMessage(session);   
        DataHandler handler = new DataHandler(
        		new ByteArrayDataSource(body.getBytes(), "text/plain"));
        message.setSender(new InternetAddress(from));   
        message.setSubject(subject);
        message.setDataHandler(handler);   
        if (to.indexOf(',') > 0) {
            message.setRecipients(Message.RecipientType.TO, 
            		InternetAddress.parse(to));   
        } else {  
            message.setRecipient(Message.RecipientType.TO, 
            		new InternetAddress(to));
        }
        Transport.send(message);   
    }   

    /**
     * Nested class to handle data sending and receiving
     */
    public class ByteArrayDataSource implements DataSource {   
        private byte[] data;   
        private String type;   

        public ByteArrayDataSource(byte[] data, String type) {   
            super();   
            this.data = data;   
            this.type = type;   
        }   

        public ByteArrayDataSource(byte[] data) {   
            super();   
            this.data = data;   
        }   

        public void setType(String type) {   
            this.type = type;   
        }   

        public String getContentType() {   
            if (type == null)   
                return "application/octet-stream";   
            else  
                return type;   
        }   

        public InputStream getInputStream() throws IOException {   
            return new ByteArrayInputStream(data);   
        }   

        public String getName() {   
            return "ByteArrayDataSource";   
        }   

        public OutputStream getOutputStream() throws IOException {   
            throw new IOException("Not Supported");   
        }   
    }   
}
