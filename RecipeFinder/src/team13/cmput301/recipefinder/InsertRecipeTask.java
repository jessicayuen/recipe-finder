package team13.cmput301.recipefinder;

import android.os.AsyncTask;

public class InsertRecipeTask extends AsyncTask<Recipe, Void, Boolean> {
    private OnTaskCompletionListener listener;

    public InsertRecipeTask(OnTaskCompletionListener listener) {
    	this.listener = listener;
    }
	@Override
	protected Boolean doInBackground(Recipe... params) {
		
		ElasticSearchHelper.getElasticSearchHelper().insertRecipe(params[0]);
		return null;
	}

	protected void onPostExecute() {
        listener.onTaskCompletion("Recipe was inserted successfully");
    }
}
