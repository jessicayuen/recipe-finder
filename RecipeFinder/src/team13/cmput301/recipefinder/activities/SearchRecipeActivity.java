/**
 * Activity that displays the user search results.
 * 
 * CMPUT301 W13 T13
 * @author Han (Jim) Wen, Jessica Yuen, Shen Wei Liao, Fangyu Li
 */

package team13.cmput301.recipefinder.activities;

import team13.cmput301.recipefinder.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class SearchRecipeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_form);
		// Unpack possible extra, from ingredients activity, and get the ListView
		Bundle extras = getIntent().getExtras();
		
		// Add in that ListView
		// Code for add/remove to lists of ingred
		// onClick Search behavior
		// 
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_search_recipe, menu);
		return true;
	}

}
