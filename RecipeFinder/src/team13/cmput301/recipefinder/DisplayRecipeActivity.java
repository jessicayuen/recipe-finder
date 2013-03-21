/**
 * Displays the user created recipe.
 * 
 * CMPUT301 W13 T13
 * @author Han (Jim) Wen, Jessica Yuen, Shen Wei Liao, Fangyu Li
 */

package team13.cmput301.recipefinder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class DisplayRecipeActivity extends Activity {

	private final int FILE_PATH_REQUEST = 1; // request code
	private static final int CAMERA_REQUEST = 1888; 
	private Gallery picGallery;
	private PicAdapter imgAdapt;
	private Recipe recipe;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_recipe);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			recipe = RecipeManager.getRecipeManager().
					getUserRecipes().get(extras.getInt("recipe"));
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
			recipe = new Recipe("Hamburger", 
					"This is some description. BlahBlah.",
					"=)", ingredients, instructions, new ArrayList<Photo>());
			displayRecipe();
		}

		picGallery = (Gallery) findViewById(R.id.gallery);
		imgAdapt = new PicAdapter(this, recipe.getPhotos());
		picGallery.setAdapter(imgAdapt);
		
		/* Listen for clicks to the image gallery */
		picGallery.setOnItemClickListener(new OnItemClickListener() {
		    //handle clicks
		    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		        imgAdapt.enlargePhoto(position);
		        picGallery.setAdapter(imgAdapt);
		    }
		});
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
				Intent intent = new Intent(Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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
	 * Takes the intent result and does something with it. In this case, watches
	 * for Camera and File results.
	 */
	protected void onActivityResult(int requestCode, int resultCode, 
			Intent data) {
		Bitmap photo = null;
		/* Display the image taken by the camera or from chosen file */
		if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK){
				photo = (Bitmap) data.getExtras().get("data"); 
		} else if(requestCode == FILE_PATH_REQUEST && resultCode == RESULT_OK) {
				Uri filePath = data.getData();
				try {
					photo = MediaStore.Images.Media.getBitmap(
							getContentResolver(), filePath);
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
		recipe.addPhoto(new Photo(User.getUser().getUsername(), photo));
		picGallery.setAdapter(imgAdapt);
	} 	
	
	/**
	 * Allows the user to email a recipe on 'Share' button click
	 * @param view
	 */
	public void shareRecipe(View view) {
		AlertDialog alertDialog =
				new AlertDialog.Builder(DisplayRecipeActivity.this).create();
		alertDialog.setTitle("Email Recipe");
		alertDialog.setMessage("Enter recipient(s): ");

		/* Set an EditText view to get user input */
		final EditText input = new EditText(this);
		input.setHeight(200);
		input.setHint("Seperate recipents by ,");
		input.setGravity(0);
		alertDialog.setView(input);
		alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Send", 
				new DialogInterface.OnClickListener() {

			/* Listen for Send button click */
			public void onClick(DialogInterface dialog, int which) {
				String[] recipients = input.getText().toString().replaceAll("\\s", "").split(",");
				EmailSender sender = new EmailSender();
				File cacheDir = getBaseContext().getCacheDir();
				File f = new File(cacheDir, "temp_email_image.jpg");

				/* Attach the photos to the email */
				for (int i = 0; i < recipe.getPhotos().size(); i++) {
					try {
						FileOutputStream out = new FileOutputStream(f);
						recipe.getPhotos().get(i).getPhoto().compress(
								Bitmap.CompressFormat.JPEG,
								100, out);
						out.flush();
						out.close();
						sender.addAttachment(cacheDir.getAbsolutePath() + 
								"/temp_email_image.jpg");
					} catch (Exception e) {
						Log.e("DisplayRecipeActivity", "Problems attaching photo", e); 
					}
				}
				/* Try to send the email */
				if(sender.send(new String(User.getUser().getUsername() + 
						" wants to share a recipe with you!"), 
						recipe.toEmailString(), recipients)) { 
					Toast.makeText(DisplayRecipeActivity.this, 
							"Email was sent successfully.", Toast.LENGTH_LONG).show(); 
				} else { 
					Toast.makeText(DisplayRecipeActivity.this, 
							"Email was not sent.", Toast.LENGTH_LONG).show(); 
				} 

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

	/**
	 * Add the recipe to the online database
	 * @param view
	 */
	public void publishRecipe(View view) {
		ElasticSearchHelper esh = ElasticSearchHelper.getElasticSearchHelper();
		try {
			esh.insertRecipe(recipe);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
