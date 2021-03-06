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
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * Adapter used to display search results of recipes in a list view
 * 
 * CMPUT301 W13 T13
 * @author Han (Jim) Wen, Jessica Yuen, Shen Wei Liao, Fangyu Li
 */
public class SearchListAdapter extends BaseAdapter implements OnClickListener {

	private Context context;
	private List<Recipe> recipeList;
	private final int fontSize = 10;

	/**
	 * Constructor
	 * @param context The activity context
	 * @param recipeList The list of recipes
	 */
	public SearchListAdapter(Context context, List<Recipe> recipeList) {
		this.context = context;
		this.recipeList = recipeList;
	}

	/**
	 * Set recipe list to the one provided
	 * @param list The list to be set to
	 */
	public void setRecipeList(List<Recipe> list){
		this.recipeList = list;
		notifyDataSetChanged();
	}

	@Override
	/**
	 * @return the size of the recipe list
	 */
	public int getCount() {
		return this.recipeList.size();
	}

	@Override
	/**
	 * @return the recipe at position 
	 */
	public Object getItem(int position) {
		return this.recipeList.get(position);
	}

	@Override
	/**
	 * @return the position of the recipe
	 */
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.custom_search_recipe, null);
		}

		Recipe recipe = recipeList.get(position);
		System.out.println(recipe);
		ImageView recipePic = (ImageView) convertView.findViewById(R.id.recipePicture);
		TextView descr = (TextView) convertView.findViewById(R.id.descriptionBox);
		TextView name = (TextView) convertView.findViewById(R.id.recipeNameDisplay);
		TextView author = (TextView) convertView.findViewById(R.id.authorBox);
		RatingBar recipeRating =  (RatingBar) convertView.findViewById(R.id.recipeRating);
		Button btnDownload = (Button) convertView.findViewById(R.id.removeRecipe);

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
		} else {
			recipePic.setImageResource(R.drawable.ic_launcher);
		}

		/* Set up rating display*/
		recipeRating.setIsIndicator(true);
		recipeRating.setRating(recipe.getRating());

		/* Set up the remove button */
		btnDownload.setFocusableInTouchMode(false);
		btnDownload.setFocusable(false);
		btnDownload.setOnClickListener(this);

		/* Keep track of which entry was removed */
		btnDownload.setTag(recipe);   

		return convertView;
	}

	/**
	 * Listens for favorite or remove button clicks
	 * @param v The view where the button is
	 */
	@Override
	public void onClick(View v) {
		Recipe recipe = (Recipe)v.getTag();
		RecipeManager rm = RecipeManager.getRecipeManager(context);

		if (!rm.checkForExistingRecipe(recipe)) {
			Toast.makeText(context, "Recipe already downloaded", Toast.LENGTH_SHORT).show();
		} else {
			recipe.setFave(false);
			rm.addToUserRecipe(recipe);
			Toast.makeText(context, "Recipe successfully downloaded", Toast.LENGTH_SHORT).show();
		}
		notifyDataSetChanged();	
	}

}
