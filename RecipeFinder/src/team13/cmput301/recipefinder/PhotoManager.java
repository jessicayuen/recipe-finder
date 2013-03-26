/**
 * class used to hold photos to handle case when changing orientation the
 * photos are removed
 */

package team13.cmput301.recipefinder;

import java.util.ArrayList;
import java.util.List;

public class PhotoManager {
	
	public static PhotoManager photoManager = null;
	private List<Photo> photoList;
	
	protected PhotoManager(){}
	
	public static PhotoManager getPhotoManager() {
		if(photoManager == null) {
			photoManager = new PhotoManager();
			photoManager.photoList = new ArrayList<Photo>();
		}
		return photoManager;
	}
	
	/**
	 * store the provided list into the single photomanager class
	 * @param list
	 */
	public void addList(List<Photo> list) {
		photoList = new ArrayList<Photo>();
		photoList = list;
	}
	
	/**
	 * retrieve the photo list stored
	 * @return
	 */
	public List<Photo> getPhotoList() {
		return photoList;
	}
}
