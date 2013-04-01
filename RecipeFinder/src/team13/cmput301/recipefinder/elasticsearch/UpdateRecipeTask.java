package team13.cmput301.recipefinder.elasticsearch;

import java.io.IOException;

import team13.cmput301.recipefinder.model.Recipe;
import android.os.AsyncTask;


/**
 * Task to manipulate the database when user updates a recipe in anyway shape
 * or form
 * 
 * CMPUT301 W13 T13
 * @author Han (Jim) Wen, Jessica Yuen, Shen Wei Liao, Fangyu Li
 */
public class UpdateRecipeTask extends AsyncTask<String, Void, Boolean> {
	
	private final Recipe newRecipe;
	private final String oldRecipeId;
	
	public UpdateRecipeTask(String oldRecipeId, Recipe newRecipe) {
		this.oldRecipeId = oldRecipeId;
		this.newRecipe = newRecipe;
	}

	@Override
	/**
	 * create an async thread to query the database for recipes
	 */
	protected Boolean doInBackground(String... params) {
		try {
			ElasticSearchHelper.getElasticSearchHelper().replaceRecipe(oldRecipeId, newRecipe);
			return true;
		} catch (IOException e) {
			return false;
		}
		
	}
	
}
