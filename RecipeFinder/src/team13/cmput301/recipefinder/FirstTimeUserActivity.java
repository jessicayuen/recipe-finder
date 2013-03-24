/**
 * Activity is displayed when the user uses the app for the first time.
 * Gets setup information from the user.
 * 
 * CMPUT301 W13 T13
 * @author Han (Jim) Wen, Jessica Yuen, Shen Wei Liao, Fangyu Li
 */

package team13.cmput301.recipefinder;

import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class FirstTimeUserActivity extends Activity {
	
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
		String username, email, password, host, port, sport;
		
		/* Get the contents of the edit text fields */
		username = ((EditText) findViewById(R.id.username_input)).
				getText().toString().trim();
		email = ((EditText) findViewById(R.id.email_input)).
				getText().toString().trim();
		password = ((EditText) findViewById(R.id.email_password_input)).
				getText().toString().trim();
		host = ((EditText) findViewById(R.id.host_input)).
				getText().toString().trim();
		port = ((EditText) findViewById(R.id.port_input)).
				getText().toString().trim();		
		sport = ((EditText) findViewById(R.id.sport_input)).
				getText().toString().trim();
		
		/* Check for empty fields */
		if (username.equals("") || email.equals("") || password.equals("") ||
				host.equals("") || sport.equals("") || port.equals("")) {
			handleSetupErrors(0);
			return;
		} 
		
		/* Check for valid email */
		Pattern pattern = Patterns.EMAIL_ADDRESS;
		if (!pattern.matcher(email).matches()) {
			handleSetupErrors(1);
			return;
		}
		
		/* Try to connect to email server */
		EmailSender emailSender = new EmailSender(email, password, host,
				port, sport);
		String subject = "Confirmation for Recipe Finder";
		String body = "Welcome to Recipe Finder! We hope you enjoy your experience.\n\n\n"
				+ "- The Recipe Finder team";
		String to[] = new String[1];
		to[0] = email;
		if (!emailSender.send(subject, body, to)) {
			handleSetupErrors(2);
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
	private void handleSetupErrors(int error) {
		TextView errorField = (TextView) findViewById(R.id.error_text);
		
		/* Handle each error accordingly */
		switch (error) {
			case 0:
				errorField.setText("Please enter all necessary fields.\n");
				break;
			case 1:
				errorField.setText("Invalid email.\n");
				break;
			case 2:
				errorField.setText("Could not connect " +
						"to email server. Please check SMTP configurations " +
						"and email/password combinations.\n");
				break;
			default:
				break;
		}
	}
}
