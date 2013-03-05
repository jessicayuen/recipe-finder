package team13.cmput301.recipefinder;

import java.util.List;

public class Recipe {
	String name;
	String description;
	String author;
	List<String> ingredients;
	List<Photo> photos;
	float rating;
	
	public Recipe(String name, String description, String author,
			List<String> ingredients, List<Photo> photos, float rating) {
		this.name = name;
		this.description = description;
		this.author = author;
		
	}
}
