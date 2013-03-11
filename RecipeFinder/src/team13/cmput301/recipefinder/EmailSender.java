package team13.cmput301.recipefinder;

public class EmailSender extends javax.mail.Authenticator {
	
	private String to;
	private String from;
	private String host; 
	
	public EmailSender(String to) {
		this.to = to;
		this.from = User.getUser().getEmail();
	}
}
