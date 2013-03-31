package team13.cmput301.recipefinder.elasticsearch;

import java.util.ArrayList;

import team13.cmput301.recipefinder.model.Recipe;
import android.os.AsyncTask;

public class SearchRecipeTask extends AsyncTask<String, Void, ArrayList<Recipe>> {

	private final ArrayList<String> ingredients;
	
	public SearchRecipeTask() {
		ingredients = null;
	}

	public SearchRecipeTask(ArrayList<String> ingredients) {
		this.ingredients = ingredients;
	}

	@Override
	protected ArrayList<Recipe> doInBackground(String... queries) {

		if (ingredients != null) {
			return ElasticSearchHelper.getElasticSearchHelper()
					.advancedSearchRecipes("*", ingredients);
		} else {
			if (queries[0] != null) {
				return ElasticSearchHelper.getElasticSearchHelper()
						.searchRecipes(queries[0]);
			} else {
				return new ArrayList<Recipe>();
			}
		}
	}

}
