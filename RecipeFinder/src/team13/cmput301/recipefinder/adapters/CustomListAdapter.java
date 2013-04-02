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

/**
 * Adapter used to display the user recipe infromation when list of recipes are
 * displayed
 * 
 * CMPUT301 W13 T13
 * @author Han (Jim) Wen, Jessica Yuen, Shen Wei Liao, Fangyu Li
 */
public class CustomListAdapter extends BaseAdapter implements OnClickListener {

	private Context context;
	private List<Recipe> recipeList;
	private final int fontSize = 10;
	private static final int FAV_BUTTON_CLICK = 1;
	private static final int REMOVE_BUTTON_CLICK = 2;

	/**
	 * Constructor
	 * @param context The activity context
	 * @param recipeList The recipe list to set the adapter to
	 */
	public CustomListAdapter(Context context, List<Recipe> recipeList) {
		this.context = context;
		this.recipeList = recipeList;
	}

	/** 
	 * Set the recipe list to the one provied
	 * @param list The recipe list 
	 */
	public void setRecipeList(List<Recipe> list){
		this.recipeList = list;
		notifyDataSetChanged();
	}

	@Override
	/**
	 * @return the number of recipes in the list
	 */
	public int getCount() {
		return this.recipeList.size();
	}

	@Override
	/**
	 * @return the recipe at location of the position
	 * @param position The position of the recipe
	 */
	public Object getItem(int position) {
		return this.recipeList.get(position);
	}

	@Override
	/**
	 * @return the position of the recipe
	 * @param the position of the recipe
	 */
	public long getItemId(int position) {
		return position;
	}

	@Override
	/**
	 * Set up the list view
	 */
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
		Button btnRemove = (Button) convertView.findViewById(R.id.removeRecipe);

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
		btnRemove.setBackgroundResource(R.drawable.deletebutton);
		btnRemove.setFocusableInTouchMode(false);
		btnRemove.setFocusable(false);
		btnRemove.setOnClickListener(this);

		/* Keep track of which entry was removed */
		btnRemove.setTag(new TempRecipe(REMOVE_BUTTON_CLICK,recipe));   

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
			Recipe recipe = createRecipe(tRecipe);
			rm.setRecipeAtLocation(recipe, rm.getRecipeIndex(recipe));
		} else if(tRecipe.id == REMOVE_BUTTON_CLICK){
			Recipe recipe = tRecipe.recipe;
			recipeList.remove(recipe);
			RecipeManager.getRecipeManager(context).removeRecipe(recipe);
		}
		
		notifyDataSetChanged();
	}
	
	private Recipe createRecipe(TempRecipe tRecipe) {
		Recipe recipe = tRecipe.recipe;
		setFavorite(recipe);
		return recipe;
	}
	
	private void setFavorite(Recipe recipe) {
		if (!recipe.isFave()) {
			recipe.setFave(true);
		} else {
			recipe.setFave(false);
		}
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
