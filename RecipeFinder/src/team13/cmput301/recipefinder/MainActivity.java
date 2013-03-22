/**
 * Main activity when application first starts and displays the 
 * user's favourite recipes, search bar, and ability to create
 * their own recipe.
 * 
 * CMPUT301 W13 T13
 * @author Han (Jim) Wen, Jessica Yuen, Shen Wei Liao, Fangyu Li
 */

package team13.cmput301.recipefinder;

import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

	RecipeManager rm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		User user = User.getUser();
		
		/* Load user settings */
		user.loadUserSettings(this);
		
		/* Run setup if user has never used app before */
		if (user.getHasUsedApp().equals("0")) {
			Intent intent = new Intent(this, FirstTimeUserActivity.class);
			startActivity(intent);
		}
		
		// test start - remove in finished product
		user.setUsername("Mr. Tomato Man");
		user.setEmail("cmput301w13t13@gmail.com");
		user.setEmailPassword("ualberta");
		// test end - remove in finished product
		
		/* Load recipes */
		rm = RecipeManager.getRecipeManager();
		rm.loadRecipes(this);
		displayFaves();
		showAll();
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
	
	/**
	 * Starts the activity My Ingredients on 'My Ingredients' button click
	 * @param view
	 */
	public void openMyIngredients(View view) {
		Intent intent = new Intent(this, MyIngredientsActivity.class);
		startActivity(intent);
	}
	
	public void TEST(View view) {
		Intent intent = new Intent(this, DisplayRecipeActivity.class);
		startActivity(intent);
	}
	
	public void searchRecipe(View view) {
		Intent intent  = new Intent(this, SearchResultsActivity.class);
		 // Pass the search query string into the search activity as an intent extra.
		 String simpleSearchQuery = 
				 ((EditText) findViewById(R.id.search_bar)).getText().toString();
		 intent.putExtra("simpleSearchQuery", simpleSearchQuery); 
		 startActivity(intent);
	}
	
	/**
	 * Displays a random 4 recipes from the favorite recipe list.
	 */
	private void displayFaves() {
		List<Recipe> faveRecipes = rm.getFaveRecipes();
		Collections.shuffle(faveRecipes);
		int numFaves = faveRecipes.size();
		if (numFaves > 0) {
			ImageView imageView;
			
			imageView = (ImageView) findViewById(R.id.faveTopLeft);
			if (faveRecipes.get(0).getPhotos().size() > 0) 
				imageView.setImageBitmap(faveRecipes.get(0).getPhotos().get(0).getPhoto());
			if (numFaves == 1) return;
			
			imageView = (ImageView) findViewById(R.id.faveTopRight);
			if (faveRecipes.get(1).getPhotos().size() > 0) 
				imageView.setImageBitmap(faveRecipes.get(1).getPhotos().get(0).getPhoto());
			if (numFaves == 2) return;
			
			imageView = (ImageView) findViewById(R.id.faveBottomLeft);
			if (faveRecipes.get(2).getPhotos().size() > 0) 
				imageView.setImageBitmap(faveRecipes.get(2).getPhotos().get(0).getPhoto());
			if (numFaves == 3) return;
			
			imageView = (ImageView) findViewById(R.id.faveBottomRight);
			if (faveRecipes.get(3).getPhotos().size() > 0) 
				imageView.setImageBitmap(faveRecipes.get(3).getPhotos().get(0).getPhoto());
		}
	}
	
	/**
	 * Shows all recipe when user touches show all recipe text
	 */
	private void showAll(){
		
		String first = "( ";
		String word = "<font color='#0000FF'>Show All </font>)";
		
		TextView showAllRecipe = (TextView)findViewById(R.id.view_all);
		showAllRecipe.setText(Html.fromHtml(first+word));
		
		showAllRecipe.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				Intent displayRecipeIntent = new Intent(MainActivity.this, 
						RecipeListActivity.class);
				
				startActivity(displayRecipeIntent);
				
				return true;
			}
			
		});
	}
}
