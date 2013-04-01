package team13.cmput301.recipefinder.elasticsearch;

import java.util.ArrayList;

import team13.cmput301.recipefinder.model.Recipe;
import android.os.AsyncTask;

/**
 * Task used query the database for recipes depending on the search text
 * 
 * CMPUT301 W13 T13
 * @author Han (Jim) Wen, Jessica Yuen, Shen Wei Liao, Fangyu Li
 */
public class SearchRecipeTask extends AsyncTask<String, Void, ArrayList<Recipe>> {

	private final ArrayList<String> ingredients;
	
	public SearchRecipeTask() {
		ingredients = null;
	}

	public SearchRecipeTask(ArrayList<String> ingredients) {
		this.ingredients = ingredients;
	}

	@Override
	/**
	 * Create an async thread to query the database for recipes
	 */
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
