package team13.cmput301.recipefinder.elasticsearch;

import java.io.IOException;

import android.os.AsyncTask;

/**
 * Task used to update the database when user updates a rating on a recipe
 * 
 * CMPUT301 W13 T13
 * @author Han (Jim) Wen, Jessica Yuen, Shen Wei Liao, Fangyu Li
 */
public class UpdateRatingTask extends AsyncTask<Void, Void, Boolean>  {

	private final String id;
	private final float newRating;
	
	public UpdateRatingTask(String id, float newRating) {
		this.id = id;
		this.newRating = newRating;
	}

	@Override
	/**
	 * Create an async thread to query the database for recipes
	 */
	protected Boolean doInBackground(Void... params) {
		try {
			ElasticSearchHelper.getElasticSearchHelper().updateRecipeRating(id, newRating);
			return true;
		} catch (IOException e) {
			return false;
		}
	}

}
