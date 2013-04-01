/**
 * Activity displays the recipe selected from the search
 * results.
 * 
 * CMPUT301 W13 T13
 * @author Han (Jim) Wen, Jessica Yuen, Shen Wei Liao, Fangyu Li
 */

package team13.cmput301.recipefinder.activities;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import team13.cmput301.recipefinder.R;
import team13.cmput301.recipefinder.adapters.SearchListAdapter;
import team13.cmput301.recipefinder.controllers.RecipeManager;
import team13.cmput301.recipefinder.elasticsearch.SearchRecipeTask;
import team13.cmput301.recipefinder.model.Recipe;
import team13.cmput301.recipefinder.resources.InternetConnectivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SearchResultsActivity extends Activity {

	private boolean sortedByRating = false;
	private boolean sortedByName = false;
	private boolean sortedByAuthor = false;
	private SearchListAdapter search;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ArrayList<Recipe> recipeList = new ArrayList<Recipe>();

		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		// Note that both query fields are optional
		ArrayList<String> ingredientQuery = extras.getStringArrayList("Ingredients");
		String query = extras.getString("simpleSearchQuery");
		setContentView(R.layout.activity_search_results);
		SearchRecipeTask searchRecipeTask = new SearchRecipeTask(ingredientQuery);
		searchRecipeTask.execute(query);
		TextView ratingText = (TextView) findViewById(R.id.rating1);
		TextView sortOption = (TextView) findViewById(R.id.discrip1);

		try {
			recipeList = searchRecipeTask.get();
		} catch (InterruptedException e) {
			Log.e("SearchResultsActivity", "Search interrupted");
		} catch (ExecutionException e) {
			Log.e("SearchResultsActivity", "Error occurred, please try again");
		}
		RecipeManager.getRecipeManager(this).setSearchResultRecipes(recipeList);

		search = new SearchListAdapter(this, RecipeManager.getRecipeManager(this)
				.getSearchResultRecipes());
		ListView searchResultListView = 
				(ListView) findViewById(R.id.searchResultListView);
		searchResultListView.setAdapter(search);

		ratingText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				sortResultByRating();				
			}

		});

		sortOption.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showSortOptions();				
			}			
		});

		searchResultListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {			
				Intent displayIntent = new Intent(SearchResultsActivity.this, 
						SearchRecipeActivity.class);
				displayIntent.putExtra("recipe", pos);
				startActivity(displayIntent);	
			}
		});
	}

	/**
	 * Listen for click on 'Search' 
	 * perform a query based on the user's input.
	 * @param view The current activity view
	 */
	public void searchFor(View view) {
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
				((EditText) findViewById(R.id.searchBar)).getText().toString();
		intent.putExtra("simpleSearchQuery", simpleSearchQuery); 
		finish();
		startActivity(intent);
	}

	/**
	 * pop up a dialog when user presses the description text to allow user
	 * to choose whether to sort by name or author
	 */
	public void showSortOptions() {
		List<String> options = new ArrayList<String>();
		options.add("Name");
		options.add("Author");
		CharSequence[] cs = options.toArray(new CharSequence[options.size()]);
		new AlertDialog.Builder(this)
		.setTitle("Sort By")
		.setSingleChoiceItems(cs, 0, null)
		.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				dialog.dismiss();
				int selectedPosition = ((AlertDialog)dialog).getListView().getCheckedItemPosition();
				if(selectedPosition == 0) {              	
					sortResultByName();
					sortedByName = true;
					sortedByRating = false;
					sortedByAuthor = false;
				}
				else {
					sortResultByAuthor();
					sortedByAuthor = true;
					sortedByName = false;
					sortedByRating = false;
				}
			}
		})
		.show();
	}

	/**
	 * sorts the recipes by rating when called
	 */
	public void sortResultByRating() {
		RecipeManager.getRecipeManager(this).sortSearchResultByRating(sortedByRating);
		search.setRecipeList(RecipeManager.getRecipeManager(this)
				.getSearchResultRecipes());
		sortedByRating = true;
		sortedByName = false;
		sortedByAuthor = false;
	}

	/**
	 * sorts the results by name when called
	 */
	public void sortResultByName() {
		RecipeManager.getRecipeManager(this).sortSearchResultByName(sortedByName);
		search.setRecipeList(RecipeManager.getRecipeManager(this)
				.getSearchResultRecipes());
	}

	/**
	 * sorts the results by author when called
	 */
	public void sortResultByAuthor() {
		RecipeManager.getRecipeManager(this).sortSearchResultByAuthor(sortedByAuthor);
		search.setRecipeList(RecipeManager.getRecipeManager(this)
				.getSearchResultRecipes());
	}
}
