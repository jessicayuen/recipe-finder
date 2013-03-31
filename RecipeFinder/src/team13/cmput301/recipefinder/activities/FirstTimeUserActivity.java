/**
 * Activity is displayed when the user uses the app for the first time.
 * Gets setup information from the user.
 * 
 * CMPUT301 W13 T13
 * @author Han (Jim) Wen, Jessica Yuen, Shen Wei Liao, Fangyu Li
 */

package team13.cmput301.recipefinder.activities;

import team13.cmput301.recipefinder.R;
import team13.cmput301.recipefinder.model.User;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class FirstTimeUserActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first_time_user);
	}
	
	/**
	 * Listen for when the user clicks "Next"
	 * @param view The current activity's view
	 */
	public void clickedNext (View view) {
		TextView errorField = (TextView) findViewById(R.id.error_text);
		User user = User.getUser();
		String username;
		
		/* Get the contents of the edit text fields */
		username = ((EditText) findViewById(R.id.username_input)).
				getText().toString().trim();
		
		/* Check for empty field */
		if (username.equals("")) {
			errorField.setText("Please enter a username.\n");
			return;
		} 
		
        /* No errors - store information and start main activity */
        user.setUsername(username);
        user.setHasUsedApp("1");
        user.saveUserSettings(this);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
	}
	
	@Override
	/**
	 * Disable back button from being pressed.
	 */
	public void onBackPressed() {}
}
