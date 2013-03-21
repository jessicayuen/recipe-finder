package team13.cmput301.recipefinder;

import java.util.ArrayList;
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
import android.widget.Toast;

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

		ownRecipes = new ArrayList<Recipe>();
		allRecipes = RecipeManager.getRecipeManager().getUserRecipes();
		favRecipes = new ArrayList<Recipe>();

		if(allRecipes.size() > 0){
			findOwnRecipes();
			findFavRecipes();
		}
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

		favListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View view, int position, long index) {
				Intent displayIntent = new Intent(RecipeListActivity.this, 
						DisplayRecipeActivity.class);
				displayIntent.putExtra
					("recipe", position);
				startActivity(displayIntent);
				finish();
			}
		});
		favListView.setAdapter(favListAdapter);
		
		myListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View view, int position, long index) {
				Intent displayIntent = new Intent(RecipeListActivity.this, 
						DisplayRecipeActivity.class);
				displayIntent.putExtra
					("recipe", position);
				startActivity(displayIntent);
				finish();
			}
		});
		myListView.setAdapter(ownListAdapter);
		
		// TODO make sure that when one recipe is deleted that it is deleted from
		// other lists as well

		recipeListTabs.setup();

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
		
		recipeListTabs.setOnTabChangedListener(new OnTabChangeListener(){

			@Override
			public void onTabChanged(String id) {
				allRecipes = RecipeManager.getRecipeManager().getUserRecipes();
				if(id.equals("Favorite Recipe(s)")) {
			        findFavRecipes();
			        favListAdapter.setRecipeList(favRecipes);
			    }
				else if(id.equals("My Recipe(s)")) {
			        findOwnRecipes();
			        ownListAdapter.setRecipeList(ownRecipes);
			    }
				else {
					ownListAdapter.setRecipeList(allRecipes);
				}
			}
			
		});

		recipeListTabs.setCurrentTab(0);
	}

	/**
	 *  populate users own recipe list from the all recipe list
	 */
	private void findOwnRecipes() {
		ownRecipes = new ArrayList<Recipe>();
		Recipe temp;
		for(int i = 0; i < allRecipes.size(); i++){
			temp = allRecipes.get(i);
			if(temp.getAuthor().trim().compareTo(User.getUser().getUsername().trim()) == 0
					&& !ownRecipes.contains(temp)){
				ownRecipes.add(temp);
			}
		}
	}
	
	/**
	 * populate users favourite recipes with recipes from all recipe list
	 */
	private void findFavRecipes(){
		favRecipes = new ArrayList<Recipe>();
		Recipe temp;
		for(int i = 0; i < allRecipes.size(); i++){
			temp = allRecipes.get(i);
			if(temp.isFave() && !favRecipes.contains(temp)){
				favRecipes.add(temp);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.recipe_list, menu);
		return true;
	}

}