

import static org.junit.Assert.*;

import javax.mail.internet.NewsAddress;

import org.junit.Test;

import team13.cmput301.recipefinder.activities.MainActivity;
import team13.cmput301.recipefinder.activities.RecipeListActivity;
import team13.cmput301.recipefinder.model.*;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.test.ActivityInstrumentationTestCase2;

public class PhotoTest extends ActivityInstrumentationTestCase2
<MainActivity> {
	
	MainActivity main;
	public PhotoTest() {
		super(MainActivity.class);
		// TODO Auto-generated constructor stub
	}
	
	public void setUp() throws Exception {
		super.setUp();
		main = getActivity();
	}
	
	@Test
	public void testPhotoConstructor() {
		final Bitmap bitmap = BitmapFactory.decodeResource(main.getResources(), 
				team13.cmput301.recipefinder.R.drawable.ic_launcher);
		Photo photo;
		photo = new Photo("logo", bitmap);
		assertEquals("logo", photo.getAuthor());
		assertEquals(photo.getPhoto(), bitmap);
	}
}
