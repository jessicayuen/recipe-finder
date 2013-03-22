package team13.cmput301.recipefinder;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

public class RecipeListActivity extends Activity {

	private List<Recipe> allRecipes;
	private List<Recipe> favRecipes;
	private List<Recipe> ownRecipes;
	private CustomListAdapter ownListAdapter, allListAdapter, favListAdapter;
	private ListView myListView, allListView, favListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recipe_list);

		ownRecipes = RecipeManager.getRecipeManager().getOwnRecipes();
		allRecipes = RecipeManager.getRecipeManager().getUserRecipes();
		favRecipes = RecipeManager.getRecipeManager().getFaveRecipes();

		TabHost recipeListTabs = (TabHost)findViewById(R.id.tabView);
		allListView = (ListView)findViewById(R.id.allRecipeList);
		favListView = (ListView)findViewById(R.id.favRecipeList);
		myListView = (ListView)findViewById(R.id.myRecipeList);

		allListAdapter = new CustomListAdapter(this, allRecipes);
		favListAdapter = new CustomListAdapter(this, favRecipes);
		ownListAdapter = new CustomListAdapter(this, ownRecipes);

		allListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View view, int position, long index) {
				Intent displayIntent = new Intent(RecipeListActivity.this, 
						DisplayRecipeActivity.class);
				displayIntent.putExtra
					("recipe", position);
				startActivity(displayIntent);
				finish();
			}
		});

		allListView.setAdapter(allListAdapter);

		/*
		 * set up the adapter to display favorite recipes when in favoritate tab
		 * recipe view
		 */
		favListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View view, int position, long index) {
				Recipe temp = favRecipes.get(position);
				int recipeIndex = allRecipes.indexOf(temp);
				Intent displayIntent = new Intent(RecipeListActivity.this, 
						DisplayRecipeActivity.class);
				displayIntent.putExtra
					("recipe", recipeIndex);
				startActivity(displayIntent);
				finish();
			}
		});
		favListView.setAdapter(favListAdapter);
		
		/*
		 * set up view to display users recipes when under my recipes view
		 */
		myListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View view, int position, long index) {
				Recipe temp = ownRecipes.get(position);
				int recipeIndex = allRecipes.indexOf(temp);
				Intent displayIntent = new Intent(RecipeListActivity.this, 
						DisplayRecipeActivity.class);
				displayIntent.putExtra
					("recipe", recipeIndex);
				startActivity(displayIntent);
				finish();
			}
		});
		myListView.setAdapter(ownListAdapter);

		recipeListTabs.setup();

		//Add the tabs to display
		TabSpec allTab = recipeListTabs.newTabSpec("All Recipe(s)");
		allTab.setContent(R.id.allRecipesTab);
		allTab.setIndicator("All Recipe(s)");

		TabSpec favTab = recipeListTabs.newTabSpec("Favorite Recipe(s)");
		favTab.setContent(R.id.favRecipesTab);
		favTab.setIndicator("Favorite Recipe(s)");

		TabSpec ownTab = recipeListTabs.newTabSpec("My Recipe(s)");
		ownTab.setContent(R.id.myRecipesTab);
		ownTab.setIndicator("My Recipe(s)");

		recipeListTabs.addTab(allTab);
		recipeListTabs.addTab(favTab);
		recipeListTabs.addTab(ownTab);
		
		/*
		 * handle change to a tab to refresh all the tabs 
		 */
		recipeListTabs.setOnTabChangedListener(new OnTabChangeListener(){

			@Override
			public void onTabChanged(String id) {
				if(id.equals("Favorite Recipe(s)")) {
			        favRecipes = RecipeManager.getRecipeManager().getFaveRecipes();
			        favListAdapter.setRecipeList(favRecipes);
			        favListView.setAdapter(favListAdapter);
			    }
				else if(id.equals("My Recipe(s)")) {
			        ownRecipes = RecipeManager.getRecipeManager().getOwnRecipes();
			        ownListAdapter.setRecipeList(ownRecipes);
			        myListView.setAdapter(ownListAdapter);
			    }
				else {
					allRecipes = RecipeManager.getRecipeManager().getUserRecipes();
					allListAdapter.setRecipeList(allRecipes);
					allListView.setAdapter(allListAdapter);
				}
			}
			
		});

		recipeListTabs.setCurrentTab(0);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.recipe_list, menu);
		return true;
	}

	@Override
	protected void onPause() {
		RecipeManager.getRecipeManager().AddToUserRecipe(
				RecipeManager.getRecipeManager().getUserRecipes(), this);
		super.onPause();
	}
	
}
