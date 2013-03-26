/**
 * Activity displays the recipe selected from the search
 * results.
 * 
 * CMPUT301 W13 T13
 * @author Han (Jim) Wen, Jessica Yuen, Shen Wei Liao, Fangyu Li
 */

package team13.cmput301.recipefinder;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class SearchResultsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ArrayList<Recipe> recipeList = new ArrayList<Recipe>();
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		String query = extras.getString("simpleSearchQuery");
		setContentView(R.layout.activity_search_recipe);
		SearchRecipeTask searchRecipeTask = new SearchRecipeTask();
		searchRecipeTask.execute(query);
		try {
			recipeList = searchRecipeTask.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ListView searchResultListView = (ListView) findViewById(R.id.searchResultListView);
		searchResultListView
				.setAdapter(new CustomListAdapter(this, recipeList));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_search_results, menu);
		return true;
	}

}