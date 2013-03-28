package team13.cmput301.recipefinder.elasticsearch;

import team13.cmput301.recipefinder.model.Recipe;
import android.os.AsyncTask;

public class InsertRecipeTask extends AsyncTask<Recipe, Void, Boolean> {

    public InsertRecipeTask() {
    }
	@Override
	protected Boolean doInBackground(Recipe... params) {
		
		ElasticSearchHelper.getElasticSearchHelper().insertRecipe(params[0]);
		return null;
	}
}
