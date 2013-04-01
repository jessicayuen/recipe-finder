package team13.cmput301.recipefinder.elasticsearch;

import java.io.IOException;

import team13.cmput301.recipefinder.model.Photo;
import team13.cmput301.recipefinder.model.Recipe;
import android.os.AsyncTask;

/**
 * Class in charge of updating the database of recipes when new photos
 * are added to a specific recipe
 * 
 * CMPUT301 W13 T13
 * @author Han (Jim) Wen, Jessica Yuen, Shen Wei Liao, Fangyu Li
 */
public class AddPhotoTask extends AsyncTask<Photo, Void, Boolean> {
	
	private final String id;
	
	public AddPhotoTask(String id) {
		this.id = id;
	}

	@Override
	protected Boolean doInBackground(Photo... photos) {
		try {
			ElasticSearchHelper.getElasticSearchHelper().addRecipePhoto(id, photos);
			return true;
		} catch (IOException e) {
			return false;
		}
		
	}
	
}
