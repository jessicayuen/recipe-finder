/**
 * Displays the user created recipe.
 * 
 * CMPUT301 W13 T13
 * @author Han (Jim) Wen, Jessica Yuen, Shen Wei Liao, Fangyu Li
 */

package team13.cmput301.recipefinder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

public class DisplayRecipeActivity extends Activity {

	private final int FILE_PATH_REQUEST = 1; // request code
    private static final int CAMERA_REQUEST = 1888; 
    private Gallery picGallery;
    private PicAdapter imgAdapt;
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
		
		picGallery = (Gallery) findViewById(R.id.gallery);
		imgAdapt = new PicAdapter(this, recipe.getPhotos());
		picGallery.setAdapter(imgAdapt);
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
			public void onClick(DialogInterface dialog, int which) {
                Intent cameraIntent = new Intent(
                		android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
                startActivityForResult(cameraIntent, CAMERA_REQUEST); 
			} 
		});
		alertDialog.show();
	}
	
	/**
	 * Takes the intent result and does something with it.
	 */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	/* Display the image taken by the camera or from chosen file */
        if ((requestCode == CAMERA_REQUEST || requestCode == FILE_PATH_REQUEST)
        		&& resultCode == RESULT_OK) {  
            Bitmap photo = (Bitmap) data.getExtras().get("data"); 
    		recipe.addPhoto(new Photo(User.getUser().getUsername(), photo));
            picGallery.setAdapter(imgAdapt);
        }
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
	public void publishRecipe(View view) {
		ElasticSearchHelper esh = ElasticSearchHelper.getElasticSearchHelper();
		try {
			esh.insertRecipe(recipe);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
