/**
 * Displays local recipes
 * 
 * CMPUT301 W13 T13
 * @author Han (Jim) Wen, Jessica Yuen, Shen Wei Liao, Fangyu Li
 */

package team13.cmput301.recipefinder.activities;

import java.util.List;

import team13.cmput301.recipefinder.R;
import team13.cmput301.recipefinder.adapters.CustomListAdapter;
import team13.cmput301.recipefinder.controllers.RecipeManager;
import team13.cmput301.recipefinder.model.Recipe;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

public class RecipeListActivity extends Activity {

	private RecipeManager rm;
	private List<Recipe> allRecipes, favRecipes, ownRecipes, downloadedRecipes;
	private CustomListAdapter ownListAdapter, allListAdapter, 
		favListAdapter, downloadedListAdapter;
	private ListView myListView, allListView, favListView, downloadedListView;
	private TabHost recipeListTabs;
	private boolean inSearchMode;
	private String searchString;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recipe_list);

		rm = RecipeManager.getRecipeManager(this);

		ownRecipes = rm.getOwnRecipes();
		allRecipes = rm.getAllRecipes();
		favRecipes = rm.getFaveRecipes();
		downloadedRecipes = rm.getDownloadedRecipes();

		inSearchMode = false;
		searchString = "";

		recipeListTabs = (TabHost)findViewById(R.id.tabView);
		
		allListView = (ListView)findViewById(R.id.allRecipeList);
		favListView = (ListView)findViewById(R.id.favRecipeList);
		myListView = (ListView)findViewById(R.id.myRecipeList);
		downloadedListView = (ListView)findViewById(R.id.downloadedList);

		allListAdapter = new CustomListAdapter(this, allRecipes);
		favListAdapter = new CustomListAdapter(this, favRecipes);
		ownListAdapter = new CustomListAdapter(this, ownRecipes);
		downloadedListAdapter = new CustomListAdapter(this, downloadedRecipes);
		
		allListView.setAdapter(allListAdapter);
		favListView.setAdapter(favListAdapter);
		myListView.setAdapter(ownListAdapter);
		downloadedListView.setAdapter(downloadedListAdapter);
		
		/* Set up list listeners */
		setupListListeners();

		/* Set up tabs */
		setupTabs();
	}

	@Override
	protected void onResume(){
		super.onResume();
		if (inSearchMode)
			setAdaptersToSearch();
		else
			setAdaptersToAll();
	}
	
	/**
	 * Set up listeners for all the list views.
	 */
	private void setupListListeners() {
		allListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> av, View view, 
					int position, long i) {
				Intent displayIntent = new Intent(RecipeListActivity.this, 
						DisplayRecipeActivity.class);
				displayIntent.putExtra("recipe", position);
				startActivity(displayIntent);
			}
		});

		favListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> av, View view, 
					int position, long i) {
				Recipe temp = favRecipes.get(position);
				int recipeIndex = allRecipes.indexOf(temp);
				Intent displayIntent = new Intent(RecipeListActivity.this, 
						DisplayRecipeActivity.class);
				displayIntent.putExtra("recipe", recipeIndex);
				startActivity(displayIntent);
			}
		});

		myListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> av, View view, 
					int position, long i) {
				Recipe temp = ownRecipes.get(position);
				int recipeIndex = allRecipes.indexOf(temp);
				Intent displayIntent = new Intent(RecipeListActivity.this, 
						DisplayRecipeActivity.class);
				displayIntent.putExtra("recipe", recipeIndex);
				startActivity(displayIntent);
			}
		});
		
		downloadedListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> av, View view, 
					int position, long i) {
				Recipe temp = downloadedRecipes.get(position);
				int recipeIndex = allRecipes.indexOf(temp);
				Intent displayIntent = new Intent(RecipeListActivity.this, 
						DisplayRecipeActivity.class);
				displayIntent.putExtra("recipe", recipeIndex);
				startActivity(displayIntent);
			}
		});
	}

	/**
	 * Set up tabs for the current view
	 */
	private void setupTabs() {
		recipeListTabs.setup();

		TabSpec allTab = recipeListTabs.newTabSpec("All Recipe(s)");
		allTab.setContent(R.id.allRecipesTab);
		allTab.setIndicator("All");

		TabSpec favTab = recipeListTabs.newTabSpec("Favorite Recipe(s)");
		favTab.setContent(R.id.favRecipesTab);
		favTab.setIndicator("Faves");

		TabSpec ownTab = recipeListTabs.newTabSpec("My Recipe(s)");
		ownTab.setContent(R.id.myRecipesTab);
		ownTab.setIndicator("My Own");
		
		TabSpec downloadedTab = recipeListTabs.newTabSpec("Downloaded Recipe(s)");
		downloadedTab.setContent(R.id.downloadedTab);
		downloadedTab.setIndicator("Down-\nloaded");

		recipeListTabs.addTab(allTab);
		recipeListTabs.addTab(favTab);
		recipeListTabs.addTab(ownTab);
		recipeListTabs.addTab(downloadedTab);

		recipeListTabs.setCurrentTab(0);

		/* Set up a listener for tab changes */
		recipeListTabs.setOnTabChangedListener(new OnTabChangeListener() {
			@Override
			public void onTabChanged(String id) {
				if (inSearchMode)
					setAdaptersToSearch();
				else
					setAdaptersToAll();
			}
		});
	}

	/**
	 * Displays search results with the provided search string
	 * @param view The view of the search button
	 */
	public void localRecipeSearch(View view){
		EditText searchTextBox = (EditText)findViewById(R.id.searchBar);
		searchString = searchTextBox.getText().toString();

		inSearchMode = true;

		if(!searchString.equals("")){
			setAdaptersToSearch();
		} else {
			Toast.makeText(this, "No Text Entered", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * Refreshes the list to display all stored recipes
	 * @param view The view where the all button is
	 */
	public void viewAllRecipes(View view){
		inSearchMode = false;
		setAdaptersToAll();
	}

	/**
	 * Set the list view adapters to display only items with the search terms.
	 */
	private void setAdaptersToSearch() {
		ownRecipes = rm.searchForRecipe(searchString, rm.getOwnRecipes());
		ownListAdapter.setRecipeList(ownRecipes);

		favRecipes = rm.searchForRecipe(searchString, rm.getFaveRecipes());
		favListAdapter.setRecipeList(favRecipes);

		downloadedRecipes = rm.searchForRecipe(searchString, rm.getDownloadedRecipes());
		downloadedListAdapter.setRecipeList(downloadedRecipes);
		
		allRecipes = rm.searchForRecipe(searchString, rm.getAllRecipes());
		allListAdapter.setRecipeList(allRecipes);
	}

	/**
	 * Set the list view adapters to display all recipes in their respective tabs
	 */
	private void setAdaptersToAll() {
		ownRecipes = rm.getOwnRecipes();
		ownListAdapter.setRecipeList(ownRecipes);

		allRecipes = rm.getAllRecipes();
		allListAdapter.setRecipeList(allRecipes);

		favRecipes = rm.getFaveRecipes();
		favListAdapter.setRecipeList(favRecipes);
		
		downloadedRecipes = rm.getDownloadedRecipes();
		downloadedListAdapter.setRecipeList(downloadedRecipes);
	}
}
