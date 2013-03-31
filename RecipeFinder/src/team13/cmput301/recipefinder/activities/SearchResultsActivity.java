/**
 * Activity displays the recipe selected from the search
 * results.
 * 
 * CMPUT301 W13 T13
 * @author Han (Jim) Wen, Jessica Yuen, Shen Wei Liao, Fangyu Li
 */

package team13.cmput301.recipefinder.activities;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import team13.cmput301.recipefinder.R;
import team13.cmput301.recipefinder.adapters.CustomListAdapter;
import team13.cmput301.recipefinder.adapters.SearchListAdapter;
import team13.cmput301.recipefinder.controllers.RecipeManager;
import team13.cmput301.recipefinder.elasticsearch.SearchRecipeTask;
import team13.cmput301.recipefinder.model.Recipe;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SearchResultsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ArrayList<Recipe> recipeList = new ArrayList<Recipe>();

		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		String query = extras.getString("simpleSearchQuery");
		setContentView(R.layout.activity_search_results);
		SearchRecipeTask searchRecipeTask = new SearchRecipeTask();
		searchRecipeTask.execute(query);

		try {
			recipeList = searchRecipeTask.get();
		} catch (InterruptedException e) {
			Log.e("SearchResultsActivity", "Search interrupted");
		} catch (ExecutionException e) {
			Log.e("SearchResultsActivity", "Error occurred, please try again");
		}
		RecipeManager.getRecipeManager(this).setSearchResultRecipes(recipeList);

		ListView searchResultListView = 
				(ListView) findViewById(R.id.searchResultListView);
		searchResultListView.setAdapter(
				new SearchListAdapter(this, RecipeManager.getRecipeManager(this)
						.getSearchResultRecipes()));


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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_search_results, menu);
		return true;
	}

}
