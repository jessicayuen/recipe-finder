package team13.cmput301.recipefinder;

import java.util.List;

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
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CustomListAdapter extends BaseAdapter implements OnClickListener {

	private Context context;
	private List<Recipe> recipeList;
	private final int fontSize = 10;
	private final int descriptionWidth = 16;
	private final int authorWidth = 10;
	private static final int FAV_BUTTON_CLICK = 1;
	private static final int REMOVE_BUTTON_CLICK = 2;
	private boolean searchMode;

	public CustomListAdapter(Context context, List<Recipe> recipeList) {
		this.context = context;
		this.recipeList = recipeList;
		this.searchMode = false;
	}
	
	public class TempRecipe {
		private int id;
		private Recipe recipe;
		
		public TempRecipe(int id, Recipe recipe){
			this.id = id;
			this.recipe = recipe;
		}
	}
	
	public void setRecipeList(List<Recipe> list, boolean searchMode){
		this.searchMode = searchMode;
		this.recipeList = list;
		notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.recipeList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return this.recipeList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Recipe recipe = recipeList.get(position);

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.custom_recipe_display, null);
		}
		ImageButton fav = (ImageButton) convertView.findViewById(R.id.favStarButton);
		//add fav star clicked or not here
		
		fav.setFocusableInTouchMode(false);
		fav.setFocusable(false);
		fav.setOnClickListener(this);		
		fav.setTag(new TempRecipe(FAV_BUTTON_CLICK,recipe));

		TextView descr = (TextView) convertView.findViewById(R.id.descriptionBox);
		descr.setTextSize(fontSize);
		descr.setText(recipe.getDescription());

		TextView name = (TextView) convertView.findViewById(R.id.recipeNameDisplay);
		name.setTextSize(fontSize);
		name.setText(recipe.getName());
		
		ImageView recipePic = (ImageView) convertView.findViewById(R.id.recipePicture);
		if(recipe.getPhotos().size() > 0){
			recipePic.setImageBitmap(recipe.getPhotos().get(0).getPhoto());
		}
		
		RatingBar recipeRating =  (RatingBar) convertView.findViewById(R.id.recipeRating);
		recipeRating.setIsIndicator(true);
		if(recipe.getRating() > 0){
			recipeRating.setRating(recipe.getRating());
		}
		
		
		TextView author = (TextView) convertView.findViewById(R.id.authorBox);
		author.setTextSize(fontSize);
		author.setText(recipe.getAuthor());

		// Set the onClick Listener on this button
		Button btnRemove = (Button) convertView.findViewById(R.id.removeRecipe);
		btnRemove.setFocusableInTouchMode(false);
		btnRemove.setFocusable(false);
		btnRemove.setOnClickListener(this);

		//keep track of which entry was removed so that it
		// can be removed from the database
		btnRemove.setTag(new TempRecipe(REMOVE_BUTTON_CLICK,recipe));   
		
		return convertView;
	}

	/**
	 * listens to button clicks on a recipe be it the favorite button or
	 * remove button
	 */
	@Override
	public void onClick(View v) {
		TempRecipe tRecipe = (TempRecipe)v.getTag();
		if(tRecipe.id == FAV_BUTTON_CLICK){
			Recipe recipe = tRecipe.recipe;
			if(!recipe.isFave()){
				// add the recipe to favorites
				RecipeManager.getRecipeManager().addToFavList(recipe, searchMode);
			}
			else{
				//remove recipe from fav if it is already favorited
				RecipeManager.getRecipeManager().removeFromFavList(recipe, searchMode);
			}

		} else if(tRecipe.id == REMOVE_BUTTON_CLICK){
			Recipe recipe = tRecipe.recipe;
	        recipeList.remove(recipe);
	        RecipeManager.getRecipeManager().removeFromAllLists(recipe);
		}
        notifyDataSetChanged();
	}

}
