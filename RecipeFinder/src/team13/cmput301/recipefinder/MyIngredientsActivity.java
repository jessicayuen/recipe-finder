package team13.cmput301.recipefinder;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MyIngredientsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_ingredients);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_my_ingredients, menu);
		return true;
	}

}
