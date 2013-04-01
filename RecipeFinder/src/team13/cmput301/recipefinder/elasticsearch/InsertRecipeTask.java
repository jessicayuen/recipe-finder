package team13.cmput301.recipefinder.elasticsearch;

import team13.cmput301.recipefinder.model.Recipe;
import android.os.AsyncTask;

/**
 * Task Used to insert recipe into database.
 * 
 * CMPUT301 W13 T13
 * @author Han (Jim) Wen, Jessica Yuen, Shen Wei Liao, Fangyu Li
 */
public class InsertRecipeTask extends AsyncTask<Recipe, Void, Boolean> {

	@Override
	/**
	 * Create an async thread to query the database for recipes
	 */
	protected Boolean doInBackground(Recipe... params) {
		
		ElasticSearchHelper.getElasticSearchHelper().insertRecipe(params[0]);
		return true;
	}
}
