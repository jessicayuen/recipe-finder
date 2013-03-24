package team13.cmput301.recipefinder;

import java.util.Properties;
import java.util.regex.Pattern;

import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class FirstTimeUserActivity extends Activity {

	private String username, email, password, host, port, sport;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first_time_user);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_first_time_user, menu);
		return true;
	}
	
	/**
	 * Listen for when the user clicks "Next"
	 * @param view The current activity's view
	 */
	public void clickedNext (View view) {
		User user = User.getUser();
		
		username = ((EditText) findViewById(R.id.username_input)).getText().toString().trim();
		email = ((EditText) findViewById(R.id.email_input)).getText().toString().trim();
		password = ((EditText) findViewById(R.id.email_password_input)).getText().toString().trim();
		host = ((EditText) findViewById(R.id.host_input)).getText().toString().trim();
		port = ((EditText) findViewById(R.id.port_input)).getText().toString().trim();		
		sport = ((EditText) findViewById(R.id.sport_input)).getText().toString().trim();
		
		/* Check for empty fields */
		if (username.equals("") || email.equals("") || password.equals("") ||
				host.equals("") || sport.equals("") || port.equals("")) {
			checkSetupErrors(0);
			return;
		} 
		
		/* Check for valid email */
		Pattern pattern = Patterns.EMAIL_ADDRESS;
		if (!pattern.matcher(email).matches()) {
			checkSetupErrors(1);
			return;
		}
		
		/* Try to connect to email server */
		Properties props = setProperties();
        Session mailSession = 
        		Session.getDefaultInstance(props, new SMTPAuthenticator());
        try {
			mailSession.getTransport();
		} catch (NoSuchProviderException e) {
			checkSetupErrors(2);
			return;
		}

        /* No errors - store information and start main activity */
        user.setUsername(username);
        user.setEmail(email);
        user.setEmailPassword(password);
        user.setEmailHost(host);
        user.setEmailPort(port);
        user.setEmailSocketPort(sport);
        user.saveUserSettings(this);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
	}
	
	/**
	 * Checks for the errors that occurred with user input such as
	 * invalid email, email server settings etc.
	 * @param errors The list of errors that occurred
	 * @return true if no errors, false otherwise
	 */
	private void checkSetupErrors(int error) {
		TextView errorField = (TextView) findViewById(R.id.error_text);
		
		/* Handle each error accordingly */
		String currText = errorField.getText().toString();

		switch (error) {
			case 0:
				errorField.setText(currText.concat(
						"Please enter all necessary fields.\n"));
				break;
			case 1:
				errorField.setText(currText.concat("Invalid email.\n"));
				break;
			case 2:
				errorField.setText(currText.concat("Could not connect " +
						"to email server. Please check SMTP configurations " +
						"and email/password combinations.\n"));
				break;
			default:
				break;
		}
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
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); 
		props.put("mail.smtp.socketFactory.fallback", "false"); 

		return props; 
	} 
	
	/**
	 * SMTPAuthenticator is a inner class used to authenticate the user's
	 * email and password.
	 */
    private class SMTPAuthenticator extends javax.mail.Authenticator {
        public PasswordAuthentication getPasswordAuthentication() {
           return new PasswordAuthentication(email, password);
        }
    }
}
