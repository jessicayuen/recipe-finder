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
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

public class RecipeListActivity extends Activity {

	private List<Recipe> allRecipes;
	private List<Recipe> favRecipes;
	private List<Recipe> ownRecipes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recipe_list);

		ownRecipes = new ArrayList<Recipe>();
		allRecipes = RecipeManager.getRecipeManager().getUserRecipes();
		favRecipes =  RecipeManager.getRecipeManager().getFaveRecipes();

		if(allRecipes.size() > 0){
			findOwnRecipes();
		}
		TabHost recipeListTabs = (TabHost)findViewById(R.id.tabView);
		ListView allListView = (ListView)findViewById(R.id.allRecipeList);
		ListView favListView = (ListView)findViewById(R.id.favRecipeList);
		ListView myListView = (ListView)findViewById(R.id.myRecipeList);

		CustomListAdapter allListAdapter = new CustomListAdapter(this, allRecipes);
		CustomListAdapter favListAdapter = new CustomListAdapter(this, favRecipes);
		CustomListAdapter ownListAdapter = new CustomListAdapter(this, ownRecipes);

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

		recipeListTabs.setCurrentTab(0);
	}

	private void findOwnRecipes() {
		Recipe temp;
		for(int i = 0; i < allRecipes.size(); i++){
			temp = allRecipes.get(i);
			if(temp.getAuthor().trim().compareTo(User.getUser().getUsername().trim()) == 0){
				System.out.println(User.getUser().getUsername());
				ownRecipes.add(temp);
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
