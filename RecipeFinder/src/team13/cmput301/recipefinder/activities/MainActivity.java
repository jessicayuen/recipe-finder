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
import team13.cmput301.recipefinder.resources.InternetConnectivity;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private RecipeManager rm;
	private PhotoManager pm;
	private ListManager lm;
	private IngredientManager im;
	private List<Recipe> faveRecipes;

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
		rm = RecipeManager.getRecipeManager(this);
		im = IngredientManager.getIngredientManager();

		/* Load user settings */
		user.loadUserSettings(this);
		
		/* Run setup if user has never used app before */
		if (user.getHasUsedApp().equals("0")) {
			Intent intent = new Intent(this, FirstTimeUserActivity.class);
			startActivity(intent);
		}
		
		/* Load recipes */
		rm.loadRecipes();
		
		/*load ingredients*/
		im.loadIngredients(this);

		
		/* Set custom fonts */
		setCustomFonts();
	}

	protected void onResume() {
		clearFavesDisplayed();

		/* Display 4 random favorite recipes */
		displayFaves();
		super.onResume();
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

	/**
	 * Listen for click on 'Search' perform a query based
	 * on the user's input.
	 * @param view The current activity view
	 */
	public void searchRecipe(View view) {
		Intent intent  = new Intent(this, SearchResultsActivity.class);

		/* Check if Internet connection exists */
		if (!InternetConnectivity.checkInternetConnection(this)) {
			Toast.makeText(this, "You have no internet connection. Try" +
					" again later.", Toast.LENGTH_LONG).show();
			return;
		}

		// Pass the search query string into the 
		// search activity as an intent extra.
		String simpleSearchQuery = 
				((EditText) findViewById(R.id.search_bar)).getText().toString();
		intent.putExtra("simpleSearchQuery", simpleSearchQuery); 
		startActivity(intent);
	}



	/**
	 * Shows all recipe when user touches show all recipe text
	 */
	public void showAll(View view){
		Intent displayRecipeIntent = new Intent(this, 
				RecipeListActivity.class);
		startActivity(displayRecipeIntent);
	}

	/**
	 * Displays a random 4 recipes from the favorite recipe list.
	 */
	private void displayFaves() {
		faveRecipes = rm.getFaveRecipes();
		Collections.shuffle(faveRecipes);
		int numFaves = faveRecipes.size();
		if (numFaves > 0) {
			ImageView imageView;

			imageView = (ImageView) findViewById(R.id.faveTopLeft);
			if (faveRecipes.get(0).getPhotos().size() > 0)  {
				imageView.setImageBitmap(faveRecipes.get(0).
						getPhotos().get(0).getPhoto());
				displayClickedRecipe(imageView, faveRecipes.get(0));
			}
			if (numFaves == 1) return;

			imageView = (ImageView) findViewById(R.id.faveTopRight);
			if (faveRecipes.get(1).getPhotos().size() > 0) { 
				imageView.setImageBitmap(faveRecipes.get(1).
						getPhotos().get(0).getPhoto());
				displayClickedRecipe(imageView, faveRecipes.get(1));
			}
			if (numFaves == 2) return;

			imageView = (ImageView) findViewById(R.id.faveBottomLeft);
			if (faveRecipes.get(2).getPhotos().size() > 0) {
				imageView.setImageBitmap(faveRecipes.get(2).
						getPhotos().get(0).getPhoto());
				displayClickedRecipe(imageView, faveRecipes.get(2));
			}
			if (numFaves == 3) return;

			imageView = (ImageView) findViewById(R.id.faveBottomRight);
			if (faveRecipes.get(3).getPhotos().size() > 0) {
				imageView.setImageBitmap(faveRecipes.get(3).
						getPhotos().get(0).getPhoto());
				displayClickedRecipe(imageView, faveRecipes.get(3));
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
	 * displays the recipe clicked on the main page
	 * @param image imageview that listens to user clicks
	 * @param recipe recipe to be displayed
	 */
	private void displayClickedRecipe(ImageView image, final Recipe recipe) {
		image.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				int index = RecipeManager.getRecipeManager(MainActivity
						.this).getAllRecipes().indexOf(recipe);
				Intent displayIntent = new Intent(MainActivity.this, 
						DisplayRecipeActivity.class);
				displayIntent.putExtra("recipe", index);
				startActivity(displayIntent);
			}					
		});
	}

	/**
	 * clears the display of faves on the main activity
	 */
	private void clearFavesDisplayed(){
		ImageView imageView;

		imageView = (ImageView) findViewById(R.id.faveTopLeft);
		imageView.setClickable(false);
		imageView.setImageResource(R.drawable.ic_launcher);

		imageView = (ImageView) findViewById(R.id.faveTopRight);
		imageView.setClickable(false);
		imageView.setImageResource(R.drawable.ic_launcher);

		imageView = (ImageView) findViewById(R.id.faveBottomLeft);
		imageView.setClickable(false);
		imageView.setImageResource(R.drawable.ic_launcher);

		imageView = (ImageView) findViewById(R.id.faveBottomRight);
		imageView.setClickable(false);
		imageView.setImageResource(R.drawable.ic_launcher);
	}
}
