import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.test.ActivityInstrumentationTestCase2;

import team13.cmput301.recipefinder.R;
import team13.cmput301.recipefinder.activities.MainActivity;
import team13.cmput301.recipefinder.controllers.PhotoManager;
import team13.cmput301.recipefinder.model.Photo;


public class PhotoManagerTest 
extends ActivityInstrumentationTestCase2<MainActivity>{

	Photo photo;
	PhotoManager pm;
	List<Photo> pL = new ArrayList<Photo>();

	public PhotoManagerTest() {
		super(MainActivity.class);
	}
	
	@BeforeClass
	public void setUp() throws Exception {
		Bitmap pic = BitmapFactory.decodeResource( null,
                R.drawable.ic_launcher);
		photo = new Photo("IngredientName", pic);
		pL.add(photo);
		pm = PhotoManager.getPhotoManager();
	}

	@Test
	public void addPhotoList() {
		pm.addList(pL);
		assertEquals(pm.getPhotoList().get(0), photo);
	}
	
	@Test
	public void getPhotoList() {
		List<Photo> tempList = pm.getPhotoList();
		assertEquals(tempList, pL);
	}
	
	@Test
	public void removePhotoList() {
		pm.clearList();
		assertEquals(pm.getPhotoList(), null);
	}

}
