package team13.cmput301.recipefinder;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
			displayRecipe();
		} else {
			// TEST - CONVERT TO JUNIT LATER
			List<String> ingredients = new ArrayList<String>();
			ingredients.add("apples");
			ingredients.add("oranges");
			List<String> instructions = new ArrayList<String>();
			instructions.add("Smash the apples");
			instructions.add("Smash the oranges");
			instructions.add("Smash the apples and oranges together");
			recipe = new Recipe("Hamburger", "This is some description. BlahBlah.",
					"=)", ingredients, instructions, new ArrayList<Photo>());
			displayRecipe();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_display_recipe, menu);
		return true;
	}
	
	/**
	 * Display the contents of the recipe
	 */
	private void displayRecipe() {
        TextView textView;
        ImageView imageView;
        String ingredients = "";
        String instructions = "";
        
        for (int i = 0; i < recipe.getIngredients().size(); i++) {
        	ingredients = ingredients.concat(i+1 + ". " + 
        			recipe.getIngredients().get(i) + "\n");
        }
        
        for (int i = 0; i < recipe.getInstructions().size(); i++) {
        	instructions = instructions.concat(i+1 + ". " + 
        			recipe.getInstructions().get(i) + "\n");
        }
        
        setTitle(recipe.getName());
        
        if (!recipe.getPhotos().isEmpty()) {
        	imageView = (ImageView) this.findViewById(R.id.displayPic);
        	imageView.setImageBitmap(recipe.getPhotos().get(0).getPhoto());
        }
        
        textView = (TextView) this.findViewById(R.id.authorInfo);
        textView.setText(recipe.getAuthor());
        
        textView = (TextView) this.findViewById(R.id.descriptionInfo);
        textView.setText(recipe.getDescription());
        
        textView = (TextView) this.findViewById(R.id.ingredientsInfo);
        textView.setText(ingredients);
 
        textView = (TextView) this.findViewById(R.id.instructionsInfo);
        textView.setText(instructions);
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
			
			/* Listen for Use Existing button click */
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(DisplayRecipeActivity.this, 
						FileExplorerActivity.class);
				startActivityForResult(intent, FILE_PATH_REQUEST);
			} 
		});
		alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "Take a Picture", 
				new DialogInterface.OnClickListener() {
			
			/* Listen for Take a Picture button click */
			public void onClick(DialogInterface dialog, int which) {} });
		alertDialog.show();
	}
	
	/**
	 * Allows the user to email a recipe on 'Share' button click
	 * @param views
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

			/* Listen for Send button click */
			public void onClick(DialogInterface dialog, int which) {
				String recipient = input.getText().toString();
				// test start
				EmailSender sender = new EmailSender(recipient);
				try {
					sender.sendMail("Test", "testtets");
				} catch (Exception e) {
					e.printStackTrace();
				}
				// test end
				return;
			}
		});
		alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "Cancel", 
				new DialogInterface.OnClickListener() {

			/* Listen for Cancel button click */
			public void onClick(DialogInterface dialog, int which) {
				return;
			}
		});
		alertDialog.show();
	}
}
