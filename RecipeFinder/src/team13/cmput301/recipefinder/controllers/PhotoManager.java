package team13.cmput301.recipefinder.controllers;

import java.util.ArrayList;
import java.util.List;

import team13.cmput301.recipefinder.model.Photo;

/**
 * Class used to hold manage photos
 * 
 * CMPUT301 W13 T13
 * @author Han (Jim) Wen, Jessica Yuen, Shen Wei Liao, Fangyu Li
 */
public class PhotoManager {
	
	// Singleton
	public static PhotoManager photoManager = null;
	private List<Photo> photoList;
	
	/** 
	 * Constructor - DO NOT USE
	 * Exists only to defeat instantiation
	 */
	protected PhotoManager(){}
	
	public static PhotoManager getPhotoManager() {
		if(photoManager == null) {
			photoManager = new PhotoManager();
			photoManager.photoList = new ArrayList<Photo>();
		}
		return photoManager;
	}
	
	/**
	 * Store the provided list into the single photomanager class
	 * @param list The list to be stored
	 */
	public void addList(List<Photo> list) {
		photoList = new ArrayList<Photo>();
		photoList = list;
	}
	
	/**
	 * Retrieve the photo list stored
	 * @return the list of photos stored
	 */
	public List<Photo> getPhotoList() {
		return photoList;
	}
	
	/**
	 * Clear the list of photos
	 */
	public void clearList() {
		photoList = new ArrayList<Photo>();
	}
}
