package team13.cmput301.recipefinder;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class DisplayRecipeActivity extends Activity {

	private final int FILE_PATH_REQUEST = 1; // request code
	Recipe recipe;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_recipe);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			recipe = (Recipe) extras.getParcelable("recipe");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_display_recipe, menu);
		return true;
	}
	
	/**
	 * Allows the user to add a photo on 'Add Photo' button click
	 * @param view
	 */
	public void addPhoto(View view) {
		/* show a message if fields not entered */
		AlertDialog alertDialog = 
				new AlertDialog.Builder(DisplayRecipeActivity.this).create();
		alertDialog.setTitle("Add a Picture");
		alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Use Existing", 
				new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(DisplayRecipeActivity.this, 
						FileExplorerActivity.class);
				startActivityForResult(intent, FILE_PATH_REQUEST);
			} 
		});
		alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "Take a Picture", 
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {} });
		alertDialog.show();
	}
	
	/**
	 * Allows the user to email a recipe on 'Share' button click
	 * @param view
	 */
	public void shareRecipe(View view) {
		AlertDialog alertDialog =
				new AlertDialog.Builder(DisplayRecipeActivity.this).create();
		alertDialog.setTitle("Email Recipe");
		alertDialog.setMessage("Enter recipient: ");
		
		// Set an EditText view to get user input
		final EditText input = new EditText(this);
		alertDialog.setView(input);
		alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Send", 
				new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				String recipient = input.getText().toString();
				Intent intent = new Intent(Intent.ACTION_SEND);
				intent.setType("text/html");
				intent.putExtra(Intent.EXTRA_EMAIL, recipient);
				intent.putExtra(Intent.EXTRA_SUBJECT, 
						"You have a new Recipe from " + 
						User.getUser().getUsername() + "!");
				intent.putExtra(Intent.EXTRA_TEXT, 
						Html.fromHtml(recipe.toString()));
				startActivity(intent);
				return;
			}
		});
		alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "Cancel", 
				new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				return;
			}
		});
		alertDialog.show();
	}
}
