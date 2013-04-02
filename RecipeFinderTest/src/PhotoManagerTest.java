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

	MainActivity main;
	Photo photo;
	PhotoManager pm;
	List<Photo> pL = new ArrayList<Photo>();

	public PhotoManagerTest() {
		super(MainActivity.class);
	}
	
	@BeforeClass
	public void setUp() throws Exception {
		super.setUp();
		
		main = getActivity();
		final Bitmap pic = BitmapFactory.decodeResource(main.getResources(), 
				team13.cmput301.recipefinder.R.drawable.ic_launcher);
		photo = new Photo("IngredientName", pic);
		pL.add(photo);
		pm = PhotoManager.getPhotoManager();
	}

	@Test
	public void testaddPhotoList() {
		pm.addList(pL);
		assertEquals(pm.getPhotoList().get(0), photo);
	}
	
	@Test
	public void testgetPhotoList() {
		List<Photo> tempList = pm.getPhotoList();
		assertEquals(tempList, pL);
	}
	
	@Test
	public void testremovePhotoList() {
		pm.clearList();
		assertEquals(pm.getPhotoList(), null);
	}

}
