package team13.cmput301.recipefinder.activities;

import java.util.ArrayList;
import java.util.List;

import team13.cmput301.recipefinder.R;
import team13.cmput301.recipefinder.adapters.PicAdapter;
import team13.cmput301.recipefinder.controllers.IngredientManager;
import team13.cmput301.recipefinder.controllers.ListManager;
import team13.cmput301.recipefinder.controllers.PhotoManager;
import team13.cmput301.recipefinder.controllers.RecipeManager;
import team13.cmput301.recipefinder.model.Photo;
import team13.cmput301.recipefinder.model.Recipe;
import team13.cmput301.recipefinder.model.User;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity that allows users to create a recipe. The user is allowed to attach
 * one or more photos to the recipe, a name for the recipe, the instructions,
 * the ingredients, and the description.
 * 
 * CMPUT301 W13 T13
 * @author Han (Jim) Wen, Jessica Yuen, Shen Wei Liao, Fangyu Li
 */
public class CreateRecipeActivity extends Activity {

	/* Request Codes */
	private static final int CAMERA_REQUEST = 1;
	private static final int FILE_PATH_REQUEST = 2; 

	/* Activity UI */
	private AutoCompleteTextView addIngredients;
	private ArrayAdapter<String> autoFillAdapter;
	private EditText addName, addInstructions, addDescription;
	private Gallery gallery;

	private List<String> ingredients, instructions, ingredientAutoFillList;
	private List<Photo> imageList;
	private PicAdapter picAdapt;
	private Recipe recipe;

	@Override
	/**
	 * Started when create recipe is first created. Initializes 
	 * Button listeners.
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_recipe);

		/* Set custom fonts */
		setCustomFonts();

		addName = (EditText) findViewById(R.id.addName);
		addIngredients = (AutoCompleteTextView) findViewById(R.id.addIngredients);
		addInstructions = (EditText) findViewById(R.id.addInstructions);
		addDescription = (EditText) findViewById(R.id.addDescription);

		ingredients = ListManager.getListManager().getIngredList();
		ingredientAutoFillList = IngredientManager.getIngredientManager()
				.getIngredientAutoFillList();
		instructions = ListManager.getListManager().getInstrList();
		imageList = PhotoManager.getPhotoManager().getPhotoList();
		autoFillAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, 
				ingredientAutoFillList);
		
		/* Number of letters required to have drop list shown is set to 1*/
		addIngredients.setThreshold(1);
		addIngredients.setAdapter(autoFillAdapter);

		gallery = (Gallery) findViewById(R.id.gallery);
		picAdapt = new PicAdapter(this, imageList);
		gallery.setAdapter(picAdapt);

		/* Listen for clicks to the image gallery */
		gallery.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View v, 
					int position, long id) {
				picAdapt.enlargePhoto(position);
				gallery.setAdapter(picAdapt);
			}
		});
	}

	protected void onPause(){
		PhotoManager.getPhotoManager().addList(imageList);
		ListManager.getListManager().addIngredList(ingredients);
		ListManager.getListManager().addInstrList(instructions);
		super.onPause();
	}

	/**
	 * Called on add picture button click - prompts user to 
	 * take a new picture or choose from existing ones
	 * @param view The view where the add picture button is
	 */
	public void addPicturesClicked(View view) {
		AlertDialog alertDialog = new AlertDialog.Builder(
				CreateRecipeActivity.this).create();

		alertDialog.setTitle("Add a Picture");

		/* Set up button to choose from existing file */
		alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Use Existing", 
				new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.
						EXTERNAL_CONTENT_URI);
				startActivityForResult(intent, FILE_PATH_REQUEST);
			} });

		/* Set up button to take a new picture */
		alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "Take a Picture", 
				new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				Intent cameraIntent = new Intent(
						android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
				startActivityForResult(cameraIntent, CAMERA_REQUEST); 
			} });

		alertDialog.show();	
	}

	/**
	 * Called on add instructions button click - adds the
	 * instruction to the list of instructions
	 * @param view The view where the add instructions button is
	 */
	public void addInstructionsClicked(View view) {
		String tempStr = addInstructions.getText().toString();
		if (tempStr.length() != 0) {
			instructions.add(tempStr);
			addInstructions.setText("");
		} else {
			Toast.makeText(CreateRecipeActivity.this,
					"No Text Entered", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * Called on add ingredients button click - adds the ingredient
	 * to the list of ingredients
	 * @param view The view where the add ingredients button is
	 */
	public void addIngredientsClicked(View view) {
		String tempStr = addIngredients.getText().toString();
		if(tempStr.length() != 0) {
			if(!ingredients.contains(tempStr)){
				ingredients.add(tempStr);
			}
			addIngredients.setText("");
		} else {
			Toast.makeText(CreateRecipeActivity.this,
					"No Text Entered", Toast.LENGTH_SHORT).show();
		}
	}

	/** 
	 * Called on listing ingredients button click.
	 * Expands the list of ingredients the user added.
	 * @param view The view where the button is
	 */
	public void ingredientListClicked(View view) {
		CustomDialog dialog = 
				new CustomDialog("Your ingredients: ", ingredients);
		dialog.getCustomDialog().show();
	}

	/** 
	 * Called on listing instructions button click.
	 * Expands the list of instructions the user added.
	 * @param view The view where the button is
	 */
	public void instructionListClicked(View view) {
		CustomDialog dialog = 
				new CustomDialog("Your instructions: ", instructions);
		dialog.getCustomDialog().show();
	}

	/**
	 * Saves and displays the recipe when save button is clicked.
	 * Only does so if all necessary fields are filled out.
	 * @param view Current activity view
	 */
	public void createClicked(View view) {			
		/* if all the field are filled out then we can create a recipe*/
		if (addedName() && addedDescription() &&
				ingredients.size() > 0 && instructions.size() > 0) {

			recipe = new Recipe(addName.getText().toString(), 
					addDescription.getText().toString(), 
					User.getUser().getUsername(),
					ingredients, instructions, imageList);

			RecipeManager.getRecipeManager(this).addToUserRecipe(recipe);

			Intent displayIntent = new Intent(CreateRecipeActivity.this, 
					DisplayRecipeActivity.class);

			displayIntent.putExtra("recipe", 
					RecipeManager.getRecipeManager(this).getAllRecipes().size() - 1);

			startActivity(displayIntent);
			finish();
		} else {
			/* Let the user know a field is empty */
			Toast.makeText(CreateRecipeActivity.this, 
					"Please fill out all necessary fields", 
					Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * Return to previous activity on exit button click
	 * @param view The view where the exit button is located
	 */
	public void exitClicked(View view) {
		finish();
	}

	/**
	 * Checks whether name of the recipe is filled out.
	 * @return true if the name is filled out, false otherwise
	 */
	private boolean addedName() {
		if (addName.getText().toString().length() != 0)
			return true;
		else 
			return false;
	}

	/**
	 * Checks whether the description is filled out.
	 * @return true if the description is filled out, false otherwise
	 */
	private boolean addedDescription() {
		if (addDescription.getText().toString().length() != 0)
			return true;
		else
			return false;
	}

	@Override
	/**
	 * Takes the intent result and does something with it. In this case, 
	 * watches for Camera and File results.
	 */
	protected void onActivityResult(int requestCode, 
			int resultCode, Intent data) {

		/* Display the image taken by the camera or from chosen file */
		if (resultCode == RESULT_OK) {
			Bitmap photo = null;

			if (requestCode == CAMERA_REQUEST){
				photo = (Bitmap) data.getExtras().get("data"); 
			} else if(requestCode == FILE_PATH_REQUEST) {
				Uri filePath = data.getData();
				try {
					photo = MediaStore.Images.Media.getBitmap(
							getContentResolver(), filePath);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			imageList.add(new Photo(User.getUser().getUsername(), photo));
			gallery.setAdapter(picAdapt);
		}
	}

	/**
	 * Set the TextViews and Buttons to a custom font.
	 */
	private void setCustomFonts() {
		Typeface typeface;

		typeface = Typeface.createFromAsset(getAssets(), 
				"fonts/Comfortaa-Regular.ttf");

		((TextView)findViewById(R.id.nameText)).setTypeface(typeface);
		((TextView)findViewById(R.id.descrText)).setTypeface(typeface);
		((TextView)findViewById(R.id.instructionText)).setTypeface(typeface);
		((TextView)findViewById(R.id.ingredientText)).setTypeface(typeface);
		((Button)findViewById(R.id.createButton)).setTypeface(typeface);
		((Button)findViewById(R.id.exitButton)).setTypeface(typeface);
		((Button)findViewById(R.id.addPicturesButn)).setTypeface(typeface);
		((Button)findViewById(R.id.addIngredientsButn)).setTypeface(typeface);
		((Button)findViewById(R.id.ingredientListButton)).setTypeface(typeface);
		((Button)findViewById(R.id.addInstructionsButn)).setTypeface(typeface);
		((Button)findViewById(R.id.instructionListButton)).setTypeface(typeface);
	}

	/**
	 * Private class used for displaying a list with checklist 
	 * and delete options
	 */
	private class CustomDialog {
		String title;
		List<String> items;
		List<Integer> selectedItems;

		public CustomDialog(String title, List<String> items) {
			this.title = title;
			this.items = items;
		}

		public Dialog getCustomDialog() {
			AlertDialog.Builder dialogBuilder;
			CharSequence[] charSequence; 

			dialogBuilder = new AlertDialog.Builder(CreateRecipeActivity.this);
			charSequence = items.toArray(new CharSequence[items.size()]);
			selectedItems = new ArrayList<Integer>();

			dialogBuilder.setTitle(title);

			/* set up multiple selection */
			dialogBuilder.setMultiChoiceItems(charSequence, null,
					new DialogInterface.OnMultiChoiceClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which,
						boolean isChecked) {
					if (isChecked && !selectedItems.contains(which)) {
						selectedItems.add(which);
					} 
				}
			});

			/* set up delete button */
			dialogBuilder.setPositiveButton("Delete", 
					new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int id) {
					for(int i = selectedItems.size() - 1; i >= 0; i--){
						items.remove(selectedItems.get(i).intValue());
					}
				}
			});

			/* set up cancel button */
			dialogBuilder.setNegativeButton("Cancel", 
					new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int id) {}
			});

			return dialogBuilder.create();
		}
	}
}
