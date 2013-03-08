package team13.cmput301.recipefinder;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;

public class CreateRecipeActivity extends Activity {

	final int FILE_PATH_REQUEST = 2; // request code
	private boolean textChanged = false;
	private Button saveButton, exitButton, addPictures, addIngredient;
	private EditText addName, addIngredients, addInstructions, addDescription;
	private Gallery gallery;
	private ImageView imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_recipe);

		saveButton = (Button) findViewById(R.id.createButton);
		exitButton = (Button) findViewById(R.id.exitButton);
		addPictures = (Button) findViewById(R.id.addPicturesButn);
		addIngredient = (Button) findViewById(R.id.addIngredientsButn);
		addName = (EditText) findViewById(R.id.addName);
		addIngredients = (EditText) findViewById(R.id.addIngredients);
		addInstructions = (EditText) findViewById(R.id.addInstructions);
		addDescription = (EditText) findViewById(R.id.addDescription);


		gallery = (Gallery)findViewById(R.id.gallery);
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
				addedIngredients();
				addedInstructions();
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
		 * listen to add picture button click
		 */
		addPictures.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				/* show a message if fields not entered */
				AlertDialog alertDialog = new AlertDialog.Builder(
						CreateRecipeActivity.this).create();
				alertDialog.setTitle("Add a Picture");
				alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Use Existing", 
						new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

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
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		// if the results is coming from BROWSER_ACTIVATION_REQUEST 
		if (requestCode == FILE_PATH_REQUEST) {

			// check the result code set by the activity
			if (resultCode == RESULT_OK) {

				// get the intent extras and get the value returned
				String filePath = data.getExtras().getString("filePath");
				
				System.out.println(filePath);

				// do something with returned value
				// Tip: check for the null value before you use the returned value,
				// otherwise it will throw you a NullPointerException
			}
		}
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
	private void addedInstructions() {

		addInstructions.addTextChangedListener(new TextWatcher(){
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
	private void addedIngredients() {

		addIngredients.addTextChangedListener(new TextWatcher(){
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
