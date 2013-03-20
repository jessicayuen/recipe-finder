package team13.cmput301.recipefinder;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class RecipeListActivity extends Activity {

	private List<Recipe> allRecipes;
	private List<Recipe> favRecipes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recipe_list);

		allRecipes = RecipeManager.getRecipeManager().getUserRecipes();
		favRecipes =  RecipeManager.getRecipeManager().getFaveRecipes();

		TabHost recipeListTabs = (TabHost)findViewById(R.id.tabView);
		ListView allListView = (ListView)findViewById(R.id.allRecipeList);
		ListView favListView = (ListView)findViewById(R.id.favRecipeList);
		ListView myListView = (ListView)findViewById(R.id.myRecipeList);

		CustomListAdapter allListAdapter = new CustomListAdapter(this, allRecipes);
		CustomListAdapter favListAdapter = new CustomListAdapter(this, favRecipes);

		allListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View view, int position, long index) {
			}
		});

		allListView.setAdapter(allListAdapter);
		
		favListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View view, int position, long index) {
			}
		});
		
		favListView.setAdapter(favListAdapter);
		
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

		recipeListTabs.addTab(ownTab);
		recipeListTabs.addTab(favTab);
		recipeListTabs.addTab(allTab);
		
		recipeListTabs.setCurrentTab(1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.recipe_list, menu);
		return true;
	}

}
