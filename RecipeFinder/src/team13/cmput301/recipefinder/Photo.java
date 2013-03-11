/**
 * Stores information for a Photo.
 * 
 * CMPUT301 W13 T13
 * @author Han (Jim) Wen, Jessica Yuen, Shen Wei Liao, Fangyu Li
 */

package team13.cmput301.recipefinder;

import java.util.Date;
import android.graphics.Bitmap;


/**
 * class used to store photos along with their author and date inserted
 *
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

	/*
	 * below are gets and setters of the class
	 */
	
	public Bitmap getPhoto() {
		return this.photo;
	}
	
	public Date getDate() {
		return this.date;
	}
	
	public String getAuthor() {
		return this.author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public void setPhoto(Bitmap photo) {
		this.photo =  photo;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
}
