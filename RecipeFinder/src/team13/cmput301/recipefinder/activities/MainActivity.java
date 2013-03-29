/**
 * Main activity when application first starts and displays the 
 * user's favourite recipes, search bar, and ability to create
 * their own recipe.
 * 
 * CMPUT301 W13 T13
 * @author Han (Jim) Wen, Jessica Yuen, Shen Wei Liao, Fangyu Li
 */

package team13.cmput301.recipefinder.activities;

import java.util.Collections;
import java.util.List;

import team13.cmput301.recipefinder.R;
import team13.cmput301.recipefinder.controllers.IngredientManager;
import team13.cmput301.recipefinder.controllers.ListManager;
import team13.cmput301.recipefinder.controllers.PhotoManager;
import team13.cmput301.recipefinder.controllers.RecipeManager;
import team13.cmput301.recipefinder.model.Recipe;
import team13.cmput301.recipefinder.model.User;
import team13.cmput301.recipefinder.sqlitedatabase.RecipeDataSource;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

	RecipeManager rm;
	IngredientManager im;
	PhotoManager pm;
	ListManager lm;
	public static RecipeDataSource manager;

	@Override
	/**
	 * Load user settings and recipes from the previous session.
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		User user = User.getUser();
		lm = ListManager.getListManager();
		pm = PhotoManager.getPhotoManager();
		manager = new RecipeDataSource(this);
		manager.open();
		rm = RecipeManager.getRecipeManager(this);

		/* Run setup if user has never used app before */
		if (user.getHasUsedApp().equals("0")) {
			Intent intent = new Intent(this, FirstTimeUserActivity.class);
			startActivity(intent);
		}

		/* Load user settings */
		user.loadUserSettings(this);
		
		/* Display 4 random favorite recipes */
		displayFaves();
		
		/* Set custom fonts */
		setCustomFonts();
	}
	
	/** 
	 * Starts the activity Create Recipe on 'Create My Own' button click
	 * @param view
	 */
	public void openCreateRecipe(View view) {
		lm.clearLists();
		pm.clearList();
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

	/**
	 * Listen for click on 'Search' perform a query based
	 * on the user's input.
	 * @param view The current activity view
	 */
	public void searchRecipe(View view) {
		Intent intent  = new Intent(this, SearchResultsActivity.class);
		// Pass the search query string into the 
		// search activity as an intent extra.
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
			if (faveRecipes.get(0).getPhotos().size() > 0)  {
				imageView.setImageBitmap(faveRecipes.get(0).
						getPhotos().get(0).getPhoto());
			}
			if (numFaves == 1) return;

			imageView = (ImageView) findViewById(R.id.faveTopRight);
			if (faveRecipes.get(1).getPhotos().size() > 0) { 
				imageView.setImageBitmap(faveRecipes.get(1).
						getPhotos().get(0).getPhoto());
			}
			if (numFaves == 2) return;

			imageView = (ImageView) findViewById(R.id.faveBottomLeft);
			if (faveRecipes.get(2).getPhotos().size() > 0) {
				imageView.setImageBitmap(faveRecipes.get(2).
						getPhotos().get(0).getPhoto());
			}
			if (numFaves == 3) return;

			imageView = (ImageView) findViewById(R.id.faveBottomRight);
			if (faveRecipes.get(3).getPhotos().size() > 0) {
				imageView.setImageBitmap(faveRecipes.get(3).
						getPhotos().get(0).getPhoto());
			}
		}
	}

	/**
	 * Set the TextViews to a custom font
	 */
	private void setCustomFonts() {
		Typeface typeface;
		
		typeface = Typeface.createFromAsset(getAssets(), 
				"fonts/Comfortaa-Bold.ttf");
	    
		((TextView)findViewById(R.id.fave)).setTypeface(typeface);
		((TextView)findViewById(R.id.find_recipes)).setTypeface(typeface);
		((TextView)findViewById(R.id.advanced)).setTypeface(typeface);
		((TextView)findViewById(R.id.customize)).setTypeface(typeface);
		((Button)findViewById(R.id.search_button)).setTypeface(typeface);
		((Button)findViewById(R.id.view_all)).setTypeface(typeface);
		((Button)findViewById(R.id.create_my_own)).setTypeface(typeface);
		((Button)findViewById(R.id.my_ingredients)).setTypeface(typeface);
	}
	
	/**
	 * Shows all recipe when user touches show all recipe text
	 */
	public void showAll(View view){
		Intent displayRecipeIntent = new Intent(this, 
				RecipeListActivity.class);
		startActivity(displayRecipeIntent);
	}
}
