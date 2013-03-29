/**
 * Displays the user created recipe.
 * 
 * CMPUT301 W13 T13
 * @author Han (Jim) Wen, Jessica Yuen, Shen Wei Liao, Fangyu Li
 */

package team13.cmput301.recipefinder.activities;

import java.io.File;
import java.io.FileOutputStream;

import team13.cmput301.recipefinder.R;
import team13.cmput301.recipefinder.adapters.PicAdapter;
import team13.cmput301.recipefinder.controllers.RecipeManager;
import team13.cmput301.recipefinder.elasticsearch.InsertRecipeTask;
import team13.cmput301.recipefinder.email.EmailSender;
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
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayRecipeActivity extends Activity  {

	private final int FILE_PATH_REQUEST = 1; 
	private static final int CAMERA_REQUEST = 1888; 
	private Gallery picGallery;
	private PicAdapter imgAdapt;
	private Recipe recipe;
	private RatingBar recipeRating, dialogRatingBar;
	private float userRating = 0;
	private Button ratingClose, ratingAccept;
	private Dialog ratingDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_recipe);

		/* Set the custom fonts */
		setCustomFonts();

		/* Set up custom dialog pop up for rating*/
		setUpRatingBar();

		/* Get the recipe to be displayed */
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			recipe = RecipeManager.getRecipeManager().
					getAllRecipes().get(extras.getInt("recipe"));
			displayRecipe();
		} 


		recipeRating.setRating(recipe.getRating());

		picGallery = (Gallery) findViewById(R.id.gallery);
		imgAdapt = new PicAdapter(this, recipe.getPhotos());
		picGallery.setAdapter(imgAdapt);

		/* Listen for clicks to the image gallery */
		picGallery.setOnItemClickListener(new OnItemClickListener()  {
			public void onItemClick(AdapterView<?> parent, View v, 
					int position, long id) {
				imgAdapt.enlargePhoto(position);
				picGallery.setAdapter(imgAdapt);
			}
		});
	}

	/**
	 * Display the contents of the recipe.
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

		recipeRating.setRating(recipe.getRating());

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
	 * Allows the user to add a photo on 'Add Photo' button click.
	 * @param view
	 */
	public void addPhoto(View view) {
		AlertDialog alertDialog = 
				new AlertDialog.Builder(DisplayRecipeActivity.this).create();
		alertDialog.setTitle("Add a Picture");

		alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Use Existing", 
				new DialogInterface.OnClickListener() {

			/* Listen for Use Existing button click */
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(Intent.ACTION_PICK, android.
						provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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
	 * Takes the intent result and does something with it. In this case, 
	 * watches for Camera and File results.
	 */
	protected void onActivityResult(int requestCode, int resultCode, 
			Intent data) {

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

			recipe.addPhoto(new Photo(User.getUser().getUsername(), photo));
			picGallery.setAdapter(imgAdapt);
		}
	} 	

	/**
	 * Allows the user to email a recipe on 'Share' button click
	 * @param view The current activity view
	 */
	public void shareRecipe(View view) {
		/* Set an EditText view to get user input */
		final EditText input = new EditText(this);
		input.setHeight(200);
		input.setHint("Seperate recipents by ,");
		input.setGravity(0);

		/* Setup the alert dialog */
		AlertDialog alertDialog =
				new AlertDialog.Builder(DisplayRecipeActivity.this).create();
		alertDialog.setTitle("Email Recipe");
		alertDialog.setMessage("Enter recipient(s): ");
		alertDialog.setView(input);
		alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Send", 
				new DialogInterface.OnClickListener() {

			/* Listen for Send button click */
			public void onClick(DialogInterface dialog, int which) {
				String[] recipients = input.getText().toString().
						replaceAll("\\s", "").split(",");
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
						Log.e("DisplayRecipeActivity", 
								"Problems attaching photo", e);
					}
				}
				/* Try to send the email */
				if(sender.send(new String(User.getUser().getUsername() + 
						" wants to share a recipe with you!"), 
						recipe.toEmailString(), recipients)) { 
					Toast.makeText(DisplayRecipeActivity.this, 
							"Email sent successfully.", 
							Toast.LENGTH_LONG).show();
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
		InsertRecipeTask irt = new InsertRecipeTask();
		irt.execute(recipe);
		Toast.makeText(DisplayRecipeActivity.this, 
				"Your recipe has been published!", Toast.LENGTH_LONG).show();
	}

	/**
	 * Add recipe to the list of favorites on button click
	 * @param view
	 */
	public void addToFave(View view) {
		RecipeManager rm = RecipeManager.getRecipeManager();
		recipe.setFave(true);
		rm.setRecipeAtLocation(recipe, rm.getRecipeIndex(recipe), this);
	}


	/**
	 * Set the TextViews and Buttons to a custom font.
	 */
	private void setCustomFonts() {
		Typeface typeface;

		typeface = Typeface.createFromAsset(getAssets(), 
				"fonts/Comfortaa-Regular.ttf");

		((TextView)findViewById(R.id.author)).setTypeface(typeface);
		((TextView)findViewById(R.id.description)).setTypeface(typeface);
		((TextView)findViewById(R.id.ingredients)).setTypeface(typeface);
		((TextView)findViewById(R.id.instructions)).setTypeface(typeface);
		((Button)findViewById(R.id.publish)).setTypeface(typeface);
		((Button)findViewById(R.id.share)).setTypeface(typeface);
		((Button)findViewById(R.id.addPhoto)).setTypeface(typeface);
		((Button)findViewById(R.id.fave_button)).setTypeface(typeface);
	}

	/**
	 * set up the custom rating bar that is displayed when
	 * user touches the tiny rating bar
	 */
	private void setUpRatingBar() {

		recipeRating = (RatingBar) findViewById(R.id.displayRecipeRating);
		recipeRating.setIsIndicator(true);

		recipeRating.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View arg0, MotionEvent arg1) {
				ratingDialog = new Dialog(DisplayRecipeActivity.this);
				ratingDialog.setContentView(R.layout.custom_rating_display);
				ratingDialog.setCancelable(true);
				ratingDialog.setTitle("Recipe Rating");

				dialogRatingBar = (RatingBar) ratingDialog.findViewById(R.id.custRatingBar);
				ratingClose = (Button) ratingDialog.findViewById(R.id.ratingClose);
				ratingAccept = (Button) ratingDialog.findViewById(R.id.ratingAccept);

				ratingClose.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						ratingDialog.dismiss();						
					}					
				});

				ratingAccept.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						userRating = dialogRatingBar.getRating();
						recipe.setRating(userRating);
						recipeRating.setRating(recipe.getRating());
						ratingDialog.dismiss();						
					}					
				});
				ratingDialog.show();
				return false;
			}			
		});
	}

}
