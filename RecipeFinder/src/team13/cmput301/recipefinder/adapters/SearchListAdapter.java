package team13.cmput301.recipefinder.adapters;

import java.util.List;

import team13.cmput301.recipefinder.R;
import team13.cmput301.recipefinder.controllers.RecipeManager;
import team13.cmput301.recipefinder.model.Recipe;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class SearchListAdapter extends BaseAdapter implements OnClickListener {

	private Context context;
	private List<Recipe> recipeList;
	private final int fontSize = 10;
	private static final int FAV_BUTTON_CLICK = 1;
	private static final int DOWNLOAD_BUTTON_CLICK = 2;

	public SearchListAdapter(Context context, List<Recipe> recipeList) {
		this.context = context;
		this.recipeList = recipeList;
	}

	public void setRecipeList(List<Recipe> list){
		this.recipeList = list;
		notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		return this.recipeList.size();
	}

	@Override
	public Object getItem(int position) {
		return this.recipeList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.custom_recipe_display, null);
		}

		Recipe recipe = recipeList.get(position);
		ImageButton fav = (ImageButton) convertView.findViewById(R.id.favStarButton);
		ImageView recipePic = (ImageView) convertView.findViewById(R.id.recipePicture);
		TextView descr = (TextView) convertView.findViewById(R.id.descriptionBox);
		TextView name = (TextView) convertView.findViewById(R.id.recipeNameDisplay);
		TextView author = (TextView) convertView.findViewById(R.id.authorBox);
		RatingBar recipeRating =  (RatingBar) convertView.findViewById(R.id.recipeRating);
		Button btnDownload = (Button) convertView.findViewById(R.id.removeRecipe);
		
		/* Draw a star for fave recipes */
		if(recipe.isFave()){
			fav.setImageResource(R.drawable.star);
			fav.setBackgroundResource(R.drawable.star);
		} else {
			fav.setImageResource(R.drawable.staroff);
			fav.setBackgroundResource(R.drawable.staroff);
		}
		fav.setFocusableInTouchMode(false);
		fav.setFocusable(false);
		fav.setOnClickListener(this);		
		fav.setTag(new TempRecipe(FAV_BUTTON_CLICK,recipe));

		/* Set description text format */
		descr.setTextSize(fontSize);
		descr.setText(recipe.getDescription());

		/* Set name text format */
		name.setTextSize(fontSize);
		name.setText(recipe.getName());

		/* Set up author text format */
		author.setTextSize(fontSize);
		author.setText(recipe.getAuthor());
		
		/* Set up display photo */
		if(recipe.getPhotos().size() > 0){
			recipePic.setImageBitmap(recipe.getPhotos().get(0).getPhoto());
		}

		/* Set up rating display*/
		recipeRating.setIsIndicator(true);
		if(recipe.getRating() > 0){
			recipeRating.setRating(recipe.getRating());
		}

		/* Set up the remove button */
		btnDownload.setFocusableInTouchMode(false);
		btnDownload.setFocusable(false);
		btnDownload.setOnClickListener(this);

		/* Keep track of which entry was removed */
		btnDownload.setTag(new TempRecipe(DOWNLOAD_BUTTON_CLICK,recipe));   

		return convertView;
	}

	/**
	 * Listens for favorite or remove button clicks
	 * @param v The view where the button is
	 */
	@Override
	public void onClick(View v) {
		TempRecipe tRecipe = (TempRecipe) v.getTag();
		RecipeManager rm = RecipeManager.getRecipeManager(context);
		
		if (tRecipe.id == FAV_BUTTON_CLICK) {
			Recipe recipe = tRecipe.recipe;
			if (!recipe.isFave()) {
				recipe.setFave(true);
			} else {
				recipe.setFave(false);
			}
			rm.setRecipeAtLocation(recipe, rm.getRecipeIndex(recipe));
			
		} else if(tRecipe.id == DOWNLOAD_BUTTON_CLICK){
			Recipe recipe = tRecipe.recipe;
			recipeList.remove(recipe);
			rm.addToUserRecipe(recipe);
		}
		
		notifyDataSetChanged();
	}
	
	private class TempRecipe {
		private int id;
		private Recipe recipe;

		public TempRecipe(int id, Recipe recipe){
			this.id = id;
			this.recipe = recipe;
		}
	}
}
