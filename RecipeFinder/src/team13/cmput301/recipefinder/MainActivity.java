package team13.cmput301.recipefinder;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	RecipeManager rm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		rm = RecipeManager.getRecipeManager();
		//displayFaves();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	/** 
	 * Starts the activity Create Recipe on 'Create My Own' button click
	 * @param view
	 */
	public void openCreateRecipe(View view) {
		Intent intent = new Intent(this, CreateRecipeActivity.class);
		startActivity(intent);
	}
	
	private void displayFaves() {
		Random r = new Random();
		int numFaves = rm.getFaveRecipes().size();
		int recipe = r.nextInt(numFaves - 1);
		
		
	}
}
