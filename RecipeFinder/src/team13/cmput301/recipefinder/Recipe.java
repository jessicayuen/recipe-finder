package team13.cmput301.recipefinder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Recipe {
	
	private String name;
	private String description;
	private String author;
	private List<String> instructions;
	private List<String> ingredients;
	private List<Photo> photos;
	private float rating;
	private Date date;

	
	/** 
	 * Constructor for Recipe object
	 * @param name
	 * @param description
	 * @param author
	 * @param ingredients 
	 * @param instructions
	 * @param photos
	 * @param rating
	 */
	public Recipe(String name, String description, String author,
			List<String> ingredients, List<String> instructions, 
			List<Photo> photos, float rating) {
		this.name = name;
		this.description = description;
		this.instructions = instructions;
		this.author = author;
		this.ingredients = ingredients;
		this.photos = photos;
		this.rating = rating;
		this.date = new Date();
	}
	
	/**
	 * Constructor for Recipe object
	 * @param name
	 * @param description
	 * @param author
	 * @param ingredients
	 * @param instructions
	 * @param photos
	 */
	public Recipe(String name, String description, String author,
			List<String> ingredients, List<String> instructions, 
			List<Photo> photos) {
		this.name = name;
		this.description = description;
		this.instructions = instructions;
		this.author = author;
		this.ingredients = ingredients;
		this.photos = photos;
		this.rating = 0;
		this.date = new Date();
	}
	
	/** 
	 * Constructor for Recipe object
	 * @param name
	 * @param description
	 * @param author
	 * @param ingredients
	 * @param instructions
	 */
	public Recipe(String name, String description, String author,
			List<String> ingredients, List<String> instructions) {
		this.name = name;
		this.description = description;
		this.instructions = instructions;
		this.author = author; 
		this.ingredients = ingredients;
		this.photos = new ArrayList<Photo>();
		this.rating = 0;
		this.date = new Date();
	}

	/** 
	 * Add a photo to the recipe
	 * @param photo
	 */
	public void addPhoto(Photo photo) {
		photos.add(photo);
	}
	
	/**
	 * Add a ingredient to the recipe
	 * @param ingredient
	 */
	public void addIngredient(String ingredient) {
		ingredients.add(ingredient);
	}
	
	/**
	 * Add an instruction to the end of the recipe
	 * @param instruction
	 */
	public void addInstruction(String instruction) {
		instructions.add(instruction);
	}
	
	/**
	 * Add an instruction to the recipe at location i
	 * @param instruction
	 * @param i
	 * @return false if i is out of bounds, true otherwise
	 */
	public boolean addInstruction(String instruction, int i) {
		try {
			instructions.add(i, instruction);
		} catch (IndexOutOfBoundsException e) {
			return false;
		}
		return true;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public List<String> getInstructions() {
		return instructions;
	}

	public void setInstructions(List<String> instructions) {
		this.instructions = instructions;
	}

	public List<String> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<String> ingredients) {
		this.ingredients = ingredients;
	}

	public List<Photo> getPhotos() {
		return photos;
	}

	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}
}
