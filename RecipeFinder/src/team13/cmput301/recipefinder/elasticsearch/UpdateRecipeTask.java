package team13.cmput301.recipefinder.elasticsearch;

import java.io.IOException;

import team13.cmput301.recipefinder.model.Recipe;
import android.os.AsyncTask;


public class UpdateRecipeTask extends AsyncTask<String, Void, Boolean> {
	
	private final Recipe newRecipe;
	private final String oldRecipeId;
	
	public UpdateRecipeTask(String oldRecipeId, Recipe newRecipe) {
		this.oldRecipeId = oldRecipeId;
		this.newRecipe = newRecipe;
	}

	@Override
	protected Boolean doInBackground(String... params) {
		try {
			ElasticSearchHelper.getElasticSearchHelper().replaceRecipe(oldRecipeId, newRecipe);
			return true;
		} catch (IOException e) {
			return false;
		}
		
	}
	
}
