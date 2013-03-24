/**
 * Activity that allows user to create a recipe. The user is allowed to attach
 * one or multiple photos to the recipe. A name for the recipe, the instructions
 * of the recipe, the ingredients or the recipe and the description must be
 * present if user wishes to save the recipe.
 * 
 * CMPUT301 W13 T13
 * @author Han (Jim) Wen, Jessica Yuen, Shen Wei Liao, Fangyu Li
 */

package team13.cmput301.recipefinder;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.Toast;

public class CreateRecipeActivity extends Activity {

	// Request Codes
	private static final int CAMERA_REQUEST = 1;
	private static final int FILE_PATH_REQUEST = 2; 
	private static final int INGREDIENTDIALOG = 1;
	private static final int INSTRUCTIONDIALOG = 2;


	// Activity UI buttons, text fields, and galleries
	private boolean textChanged = false, contentChanged = false;
	private AutoCompleteTextView addIngredients;
	private Button exitButton, addPicButton, addIngredButton, 
	addInsButton, ingredListButton, instrListButton;
	private EditText addName, addInstructions, 
	addDescription, addAmount;
	private Gallery gallery;
	private ArrayAdapter<String> autoFillAdapter;

	private List<String> ingredientNames, ingredients, instructions;
	private List<Integer> mSelectedItems;
	private List<Photo> imageList;
	private PicAdapter picAdapt;
	Recipe recipe;

	@Override
	/**
	 * Started when create recipe is first created. Initializes 
	 * Button listeners.
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_recipe);

		ingredListButton = (Button) findViewById(R.id.ingredientListButton);
		instrListButton = (Button) findViewById(R.id.instructionListButton);
		exitButton = (Button) findViewById(R.id.exitButton);
		addPicButton = (Button) findViewById(R.id.addPicturesButn);
		addIngredButton = (Button) findViewById(R.id.addIngredientsButn);
		addInsButton = (Button) findViewById(R.id.addInstructionsButn);
		addName = (EditText) findViewById(R.id.addName);
		addIngredients = (AutoCompleteTextView) findViewById(R.id.addIngredients);
		addInstructions = (EditText) findViewById(R.id.addInstructions);
		addDescription = (EditText) findViewById(R.id.addDescription);
		addAmount = (EditText) findViewById(R.id.addAmount);

		ingredients = new ArrayList<String>();
		ingredientNames =  IngredientManager.getIngredientManager().getIngredientNamesList();
		instructions = new ArrayList<String>();
		imageList = new ArrayList<Photo>();
		autoFillAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, ingredientNames);

		addIngredients.setAdapter(autoFillAdapter);
		gallery = (Gallery) findViewById(R.id.gallery);
		picAdapt = new PicAdapter(this, imageList);
		gallery.setAdapter(picAdapt);

		/* Listen for clicks to the image gallery */
		gallery.setOnItemClickListener(new OnItemClickListener() {
		    //handle clicks
		    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		        picAdapt.enlargePhoto(position);
		        gallery.setAdapter(picAdapt);
		    }
		});

		/*
		 * close activity when user clicks exit button
		 */
		exitButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				finish();
			}
		});

		/*
		 * listen to add picture button click and promt user to use camera
		 * and take a picture or attach a pre-existing picture from the 
		 * phone memory.
		 */
		addPicButton.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				/* show a message if fields not entered */
				AlertDialog alertDialog = new AlertDialog.Builder(
						CreateRecipeActivity.this).create();
				alertDialog.setTitle("Add a Picture");
				alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Use Existing", 
						new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						/* opens embedded android gallery explorer when user 
						 * clicks on choose existing picture */
						Intent intent = new Intent(Intent.ACTION_PICK,
								android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
						startActivityForResult(intent, FILE_PATH_REQUEST);
					} });
				alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "Take a Picture", 
						new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Intent cameraIntent = new Intent(
								android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
						startActivityForResult(cameraIntent, CAMERA_REQUEST); 
					} });
				alertDialog.show();
			}
		});

		/*
		 * when add ingredient button is clicked, check if the text field is 
		 * filled out, if so the add the content to the ingredient list otherwise
		 * print an error alert
		 */
		addIngredButton.setOnClickListener(new View.OnClickListener() {
			Toast toast;
			@Override
			public void onClick(View arg0) {
				String tempStr = addIngredients.getText().toString();
				if(tempStr.length() != 0) {
					if(!ingredients.contains(tempStr)){
						ingredients.add(tempStr);
						contentChanged = true;
					}
					addIngredients.setText("");
				} else {
					toast = Toast.makeText(CreateRecipeActivity.this,
							"No Text Entered", Toast.LENGTH_SHORT);
					toast.show();
				}
			}
		});

		/*
		 * monitors the button for when user clicks, we add the text to a list
		 * if the text field is not empty
		 */
		addInsButton.setOnClickListener(new View.OnClickListener() {
			Toast toast;
			@Override
			public void onClick(View arg0) {
				String tempStr = addInstructions.getText().toString();
				if(tempStr.length() != 0){
					instructions.add(tempStr);
					addInstructions.setText("");
					contentChanged = true;
				} else {
					toast = Toast.makeText(CreateRecipeActivity.this,
							"No Text Entered", Toast.LENGTH_SHORT);
					toast.show();
				}
			}
		});

		/*
		 * of show all ingredient list button is clicked then 
		 * list out all of the ingredients user entered and allow user to delete
		 * selected
		 */
		ingredListButton.setOnClickListener(new View.OnClickListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View arg0) {
				if(contentChanged){
					removeDialog(INGREDIENTDIALOG);
				}
				showDialog(INGREDIENTDIALOG);
			}
		});

		/*
		 * displays the users intruction list and allow user to modify the list
		 */
		instrListButton.setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View arg0) {
				if(contentChanged){
					removeDialog(INSTRUCTIONDIALOG);
				}
				showDialog(INSTRUCTIONDIALOG);
			}
		});

	}

	@Override
	/**
	 * Takes the intent result and does something with it. In this case, watches
	 * for Camera and File results.
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
	 * override the createdialog and make it customized to allow for list and multi
	 * check mark display of either ingredients or instructions
	 * @param choice the list that user chose to display
	 */
	protected Dialog onCreateDialog(int choice) {
		Dialog dialog = null;
		AlertDialog.Builder dialogBuilder;
		mSelectedItems = new ArrayList<Integer>();  // Where we track the selected items
		contentChanged = false;

		switch(choice){
		case INGREDIENTDIALOG:
			dialogBuilder = new AlertDialog.Builder(this);
			CharSequence[] ingredCharSequence = ingredients.toArray(new 
					CharSequence[ingredients.size()]);
			// Set the dialog title
			dialogBuilder.setTitle("Your Ingredients: ")
			//set the choices as the list of ingredients
			.setMultiChoiceItems(ingredCharSequence, null,
					new DialogInterface.OnMultiChoiceClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which,
						boolean isChecked) {
					if (isChecked) {
						// If the user checked the item, add it to the selected items
						mSelectedItems.add(which);
					} else if (mSelectedItems.contains(which)) {
						// Else, if the item is already in the array, remove it 
						mSelectedItems.remove(Integer.valueOf(which));
					}
				}
			})
			// Set the action buttons
			.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int id) {
					// User clicked OK, we then remove the selected ingredients
					for(int i = mSelectedItems.size() - 1; i >= 0; i--){
						ingredients.remove(mSelectedItems.get(i).intValue());
					}
					contentChanged = true;
				}
			})
			.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int id) {
				}
			});
			dialog = dialogBuilder.create();
			break;
		case INSTRUCTIONDIALOG:
			/* handles the instruction list dialog*/
			dialogBuilder = new AlertDialog.Builder(this);
			ArrayList<String> tempStrList = new ArrayList<String>();
			for(int i = 0; i < instructions.size(); i++){
				tempStrList.add(i, new String(i + 1 + ". " + instructions.get(i))); 
			}
			CharSequence[] instrCharSequence = tempStrList.toArray(new 
					CharSequence[tempStrList.size()]);
			// Set the dialog title
			dialogBuilder.setTitle("Your Instructions: ")
			//set the choices as the list of instructions
			.setMultiChoiceItems(instrCharSequence, null,
					new DialogInterface.OnMultiChoiceClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which,
						boolean isChecked) {
					if (isChecked) {
						// If the user checked the item, add it to the selected items
						mSelectedItems.add(which);
					} else if (mSelectedItems.contains(which)) {
						// Else, if the item is already in the array, remove it 
						mSelectedItems.remove(Integer.valueOf(which));
					}
				}
			})
			// Set the action buttons
			.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int id) {
					// User clicked OK, so delete all the selected instructions
					for(int i = mSelectedItems.size() - 1; i >= 0; i--){
						instructions.remove(mSelectedItems.get(i).intValue());
					}
					contentChanged = true;
				}
			})
			.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int id) {
				}
			});
			dialog = dialogBuilder.create();
			break;
		default:
			break;
		}
		return dialog;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_create_recipe, menu);
		return true;
	}

	/**
	 * Saves the recipe and displays the recipe, when user clicks on the save
	 * button and all the necessary fields are filled in.
	 * 
	 * @param view
	 */
	public void createButtonClicked(View view) {			
		addedName();
		addedDescription();
		/* if all the field are filled out then we can create a recipe*/
		if (textChanged && ingredients.size() > 0 && instructions.size() > 0) {
			recipe = new Recipe(addName.getText().toString(), 
					addDescription.getText().toString(), User.getUser().getUsername(),
					ingredients, instructions, imageList);
			RecipeManager.getRecipeManager().AddToUserRecipe(recipe, this);
			Intent displayIntent = new Intent(CreateRecipeActivity.this, 
					DisplayRecipeActivity.class);
			displayIntent.putExtra
			("recipe", RecipeManager.getRecipeManager().getUserRecipes().size() - 1);
			startActivity(displayIntent);
			finish();
		} else {
			/* show a message if fields not entered */
			AlertDialog alertDialog = new AlertDialog.Builder(
					CreateRecipeActivity.this).create();
			alertDialog.setTitle("Error");
			alertDialog.setMessage("Necessary Field Not Entered!");
			alertDialog.setButton(Dialog.BUTTON_POSITIVE, "OK", new 
					DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {	
				} });
			alertDialog.show();
		}
	}

	/**
	 * checks whether name of the recipe is filled out or not
	 */
	private void addedName() {

		if(addName.getText().toString().length() != 0){
			textChanged = true;
		} else {
			textChanged = false;
		}
	}

	/**
	 * checks whether the description is filled out or not
	 */
	private void addedDescription() {

		if(addDescription.getText().toString().length() != 0){
			textChanged = true;
		} else {
			textChanged = false;
		}
	}
}
