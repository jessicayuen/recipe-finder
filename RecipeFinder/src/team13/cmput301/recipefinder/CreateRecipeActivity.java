package team13.cmput301.recipefinder;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class CreateRecipeActivity extends Activity {

	private static final int FILE_PATH_REQUEST = 2; // request code
	private static final int INGREDIENTDIALOG = 1;
	private static final int INSTRUCTIONDIALOG = 2;
	private boolean textChanged = false, contentChanged = false;
	private Button saveButton, exitButton, addPicButton, addIngredButton, addInsButton;
	private Button ingredListButton, instrListButton;
	private EditText addName, addIngredients, addInstructions, addDescription;
	private Gallery gallery;
	private ImageView imageView;
	//private ListView ingredListView, instrListView;
	private ArrayList<String> ingredients, instructions;
	private List<Integer> mSelectedItems;
	private ArrayAdapter<String> instrAdapter, ingredAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_recipe);

		ingredListButton = (Button) findViewById(R.id.ingredientListButton);
		instrListButton = (Button) findViewById(R.id.instructionListButton);
		saveButton = (Button) findViewById(R.id.createButton);
		exitButton = (Button) findViewById(R.id.exitButton);
		addPicButton = (Button) findViewById(R.id.addPicturesButn);
		addIngredButton = (Button) findViewById(R.id.addIngredientsButn);
		addInsButton = (Button) findViewById(R.id.addInstructionsButn);
		addName = (EditText) findViewById(R.id.addName);
		addIngredients = (EditText) findViewById(R.id.addIngredients);
		addInstructions = (EditText) findViewById(R.id.addInstructions);
		addDescription = (EditText) findViewById(R.id.addDescription);
		//		ingredListView = (ListView) findViewById(R.id.ingredientListView);
		//		instrListView = (ListView) findViewById(R.id.instructionListView);

		ingredients = new ArrayList<String>();
		instructions = new ArrayList<String>();

		//		instrAdapter =      
		//				new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, instructions);
		//		instrListView.setAdapter(instrAdapter); 
		//
		//		ingredAdapter =      
		//				new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, ingredients);
		//		ingredListView.setAdapter(ingredAdapter); 

		gallery = (Gallery) findViewById(R.id.gallery);
		//imageView = (ImageView)findViewById(R.id.i)
		gallery.setAdapter(new ImageAdapter(this));
		gallery.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> adapter, View arg1, int position,
					long arg3) {
				// TODO when clicked on a picture we are going to make it bigger
			}		

		});

		saveButton.setOnClickListener(new View.OnClickListener() {			
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				addedName();
				addedDescription();
				if(textChanged) {

				}
				else if(!textChanged) {

				}
				else {
					/* show a message if fields not entered */
					AlertDialog alertDialog = new AlertDialog.Builder(
							CreateRecipeActivity.this).create();
					alertDialog.setTitle("Error");
					alertDialog.setMessage("Necessary Field Not Entered!");
					alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {	
						} });
					alertDialog.show();
				}
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
		 * listen to add picture button click
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

						/* open file explorer when user clicks on choose existing
						 * picture */
						Intent intent = new Intent(CreateRecipeActivity.this, 
								FileExplorerActivity.class);
						startActivityForResult(intent, FILE_PATH_REQUEST);
					} });
				alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "Take a Picture", 
						new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {	
					} });
				alertDialog.show();
			}
		});

		addIngredButton.setOnClickListener(new View.OnClickListener() {

			Toast toast;
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String tempStr = addIngredients.getText().toString();
				if(tempStr.length() != 0) {
					if(!ingredients.contains(tempStr)){
						ingredients.add(tempStr);
						addIngredients.setText("");
						contentChanged = true;
						//						ingredAdapter.notifyDataSetChanged();
					} else {
						toast = Toast.makeText(CreateRecipeActivity.this,
								"Ingredient Already Exist", Toast.LENGTH_SHORT);
						toast.show();
					}
				} else {
					toast = Toast.makeText(CreateRecipeActivity.this,
							"No Text Entered", Toast.LENGTH_SHORT);
					toast.show();
				}
			}
		});

		addInsButton.setOnClickListener(new View.OnClickListener() {

			Toast toast;
			@Override
			public void onClick(View arg0) {
				String tempStr = addIngredients.getText().toString();
				// TODO Auto-generated method stub
				if(tempStr.length() != 0){
					instructions.add(tempStr);
					addInstructions.setText("");
					contentChanged = true;
					//					instrAdapter.notifyDataSetChanged();
				} else {
					toast = Toast.makeText(CreateRecipeActivity.this,
							"No Text Entered", Toast.LENGTH_SHORT);
					toast.show();
				}
			}
		});

		ingredListButton.setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(contentChanged){
					removeDialog(INGREDIENTDIALOG);
				}
				showDialog(INGREDIENTDIALOG);
			}
		});

		instrListButton.setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(contentChanged){
					removeDialog(INSTRUCTIONDIALOG);
				}
				showDialog(INSTRUCTIONDIALOG);
			}
		});

	}

	/*
	 * Obtain the filepath of the image returned from file explorer activity
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// if the results is coming from BROWSER_ACTIVATION_REQUEST 
		if (requestCode == FILE_PATH_REQUEST) {

			// check the result code set by the activity
			if (resultCode == RESULT_OK) {
				// get the intent extras and get the value returned
				String filePath = data.getExtras().getString("path");
				// TODO: check for the null value before you use the returned value,
				// otherwise it will throw you a NullPointerException
			}
		}
	}

	protected Dialog onCreateDialog(int choice) {
		Dialog dialog = null;
		AlertDialog.Builder dialogBuilder;
		mSelectedItems = new ArrayList<Integer>();  // Where we track the selected items
		contentChanged = false;
		
		switch(choice){
		case INGREDIENTDIALOG:
			dialogBuilder = new AlertDialog.Builder(this);
			CharSequence[] ingredCharSequence = ingredients.toArray(new CharSequence[ingredients.size()]);
			// Set the dialog title
			dialogBuilder.setTitle("Your Ingredients: ")
			// Specify the list array, the items to be selected by default (null for none),
			// and the listener through which to receive callbacks when items are selected
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
					// User clicked OK, so save the mSelectedItems results somewhere
					// or return them to the component that opened the dialog
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
			dialogBuilder = new AlertDialog.Builder(this);
			for(int i = 0; i < instructions.size(); i++){
				instructions.set(i, new String(i + 1 + ". " + instructions.get(i))); 
			}
			CharSequence[] instrCharSequence = instructions.toArray(new CharSequence[instructions.size()]);
			// Set the dialog title
			dialogBuilder.setTitle("Your Instructions: ")
			// Specify the list array, the items to be selected by default (null for none),
			// and the listener through which to receive callbacks when items are selected
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
					// User clicked OK, so save the mSelectedItems results somewhere
					// or return them to the component that opened the dialog7
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
	 * checks whether name of the recipe is filled out or not
	 */
	private void addedName() {

		addName.addTextChangedListener(new TextWatcher(){
			@Override
			public void afterTextChanged(Editable s) {
				if(s.toString().trim().length() != 0){
					textChanged = true;
				}
				else{
					textChanged = false;
				}
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {}
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
		});
	}

	private void addedDescription() {

		addDescription.addTextChangedListener(new TextWatcher(){
			@Override
			public void afterTextChanged(Editable s) {
				if(s.toString().trim().length() != 0){
					textChanged = true;
				}
				else{
					textChanged = false;
				}
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {}
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
		});
	}
}
