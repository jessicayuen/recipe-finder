package team13.cmput301.recipefinder;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

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

}
