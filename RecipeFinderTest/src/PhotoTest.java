

import static org.junit.Assert.*;

import javax.mail.internet.NewsAddress;

import org.junit.Test;

import team13.cmput301.recipefinder.Photo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class PhotoTest {
	final Bitmap bitmap = BitmapFactory.decodeFile("res/drawable-hdpi/ic_launcher.png");
	Photo photo;
	@Test
	public void testPhotoConstructor() {
		photo = new Photo("logo", bitmap);
		assertEquals("logo", photo.getAuthor());
		assertEquals(photo.getPhoto(), bitmap);
	}
}
