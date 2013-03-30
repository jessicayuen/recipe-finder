/**
 * Stores information for a Photo.
 * 
 * CMPUT301 W13 T13
 * @author Han (Jim) Wen, Jessica Yuen, Shen Wei Liao, Fangyu Li
 */

package team13.cmput301.recipefinder.model;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;


/**
 * class used to store photos along with their author and date inserted
 */
public class Photo {

	private String author;
	private Bitmap photo;
	private Date date;
	
	/**
	 * photo class constructor
	 * @param author
	 * @param photo
	 */
	public Photo(String author, Bitmap photo) {
		this.author = author;
		this.photo = photo;
		this.date = new Date();
	}
	
	/**
	 * photo class constructor
	 * @param author
	 * @param photo
	 * @param date
	 */
	public Photo(String author, Bitmap photo, Date date) {
		this(author, photo);
		this.date = date;
	}
	
	/** 
	 * Encodes a bitmap image to a base64 representation.
	 * @param image image to encode
	 * @return image Encoded a string containing the encoded image
	 */
	public static String encodeTobase64(Bitmap image)
	{
	    Bitmap immagex=image;
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();  
	    immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
	    byte[] b = baos.toByteArray();
	    String imageEncoded = Base64.encodeToString(b,Base64.DEFAULT);
	    return imageEncoded;
	}
	/**
	 * Decodes a bitmap image from a base64 string representation.
	 * @param input an string containing encoded image
	 * @return the bitmap version of the string
	 */
	public static Bitmap decodeBase64(String input) 
	{
	    byte[] decodedByte = Base64.decode(input, 0);
	    return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length); 
	}

	/**
	 * @return photo bitmap
	 */
	public Bitmap getPhoto() {
		return photo;
	}
	
	/**
	 * @return the encoded 64bit photo
	 */
	public String getEncodedPhoto() {
		return encodeTobase64(photo);
	}
	
	/**
	 * @return photo creation date
	 */
	public Date getDate() {
		return this.date;
	}
	
	/**
	 * @return return author of the photo
	 */
	public String getAuthor() {
		return this.author;
	}
	
	/**
	 * sets author of the photo as given in the parameter
	 * @param author
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	
	/**
	 * sets the photo as the given parameter bitmap
	 * @param photo
	 */
	public void setPhoto(Bitmap photo) {
		this.photo = photo;
	}
	
	/**
	 * @param encodedPhoto Set the photo to this
	 */
	public void setPhoto(String encodedPhoto) {
		this.photo = decodeBase64(encodedPhoto);
	}
	
	/**
	 * sets the date as the given date
	 * @param date
	 */
	public void setDate(Date date) {
		this.date = date;
	}
}
