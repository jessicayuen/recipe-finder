package team13.cmput301.recipefinder;

import java.util.ArrayList;

import android.os.AsyncTask;

public class SearchRecipeTask extends AsyncTask<String, Void, ArrayList<Recipe>> {

	@Override
	protected ArrayList<Recipe> doInBackground(String... queries) {
		return ElasticSearchHelper.getElasticSearchHelper().searchRecipes(queries[0]);
	}

}
