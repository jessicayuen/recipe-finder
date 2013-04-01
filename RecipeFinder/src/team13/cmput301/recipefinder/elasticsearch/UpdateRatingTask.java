package team13.cmput301.recipefinder.elasticsearch;

import java.io.IOException;

import android.os.AsyncTask;

public class UpdateRatingTask extends AsyncTask<Void, Void, Boolean>  {

	private final String id;
	private final float newRating;
	
	public UpdateRatingTask(String id, float newRating) {
		this.id = id;
		this.newRating = newRating;
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		try {
			ElasticSearchHelper.getElasticSearchHelper().updateRecipeRating(id, newRating);
			return true;
		} catch (IOException e) {
			return false;
		}
	}

}
