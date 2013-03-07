package team13.cmput301.recipefinder;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Gallery;
import android.widget.ImageView;

public class CreateRecipeActivity extends Activity {

	Gallery gallery;
	ImageView imageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_recipe);
		
		gallery = (Gallery)findViewById(R.id.gallery1);
		//imageView = (ImageView)findViewById(R.id.i)
		gallery.setAdapter(new ImageAdapter(this));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_create_recipe, menu);
		return true;
	}

}
