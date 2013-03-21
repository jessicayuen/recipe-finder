/**
 * Recipe class that models a Recipe
 * 
 * CMPUT301 W13 T13
 * @author Han (Jim) Wen, Jessica Yuen, Shen Wei Liao, Fangyu Li
 */

package team13.cmput301.recipefinder;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Recipe implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String description;
	private String author;
	private List<String> instructions;
	private List<String> ingredients;
	private List<Photo> photos;
	private boolean fave;
	private float rating;
	private Date date;
	private UUID id;

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
		this.id = new UUID(name.hashCode(), author.hashCode());
		this.fave = false;
	}
	
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
		this(name, description, author, ingredients, instructions,
				photos);
		this.rating = rating;
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
	
	/** 
	 * Converts Recipe to a string that is suitable for email
	 * @return a string describing the Recipe in a email-able format.
	 */
	public String toEmailString() {
		String instr = "";
		String ingred = "";
		
		for (int i = 0; i < instructions.size(); i++)
			instr = instr.concat(i + 1 + ". " + instructions.get(i) + "\n");
		for (int i = 0; i < ingredients.size(); i++)
			ingred = ingred.concat("->" + ingredients.get(i) + "\n");
		
		return new String(name + "\n" +
				author + "\n\n\n" +
				"Description:\n" + description + "\n\n" +
				"Instructions:\n" + instr + "\n" +
				"Ingredients:\n" + ingred + "\n");
	}
	
	/**
	 * @return Recipe name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name Set the name of the recipe to this
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Recipe description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description Set the description of the recipe to this
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return Author of the recipe
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author Set the author of the recipe to this
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @return the instructions
	 */
	public List<String> getInstructions() {
		return instructions;
	}

	/**
	 * @param instructions Set the recipe instructions to this
	 */
	public void setInstructions(List<String> instructions) {
		this.instructions = instructions;
	}

	/**
	 * @return The list of recipe ingredients
	 */
	public List<String> getIngredients() {
		return ingredients;
	}

	/**
	 * @param ingredients Set the recipe ingredients to this
	 */
	public void setIngredients(List<String> ingredients) {
		this.ingredients = ingredients;
	}

	/**
	 * @return The list of photos
	 */
	public List<Photo> getPhotos() {
		return photos;
	}

	/**
	 * @param photos Set the list of photos for the recipe to this
	 */
	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}

	/**
	 * @return The recipe rating
	 */
	public float getRating() {
		return rating;
	}

	/**
	 * @param rating Set the recipe rating to this
	 */
	public void setRating(float rating) {
		this.rating = rating;
	}

	/**
	 * @return The date the recipe was created
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date Set the date of the recipe to this
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the recipe ID
	 */
	public String getId() {
		return id.toString();
	}

	/**
	 * @return Whether the recipe is favorited
	 */
	public boolean isFave() {
		return fave;
	}

	/**
	 * @return the String version of the recipe object
	 */
	public String toString() {
		return "[ name: " + name + ", " + "author: " + 
				author + ", " + "description: " + description + "]";
	}
	
	/**
	 * @param fave Sets whether the recipe is favorited
	 */
	public void setFave(boolean fave) {
		this.fave = fave;
		RecipeManager.getRecipeManager().addRecipeToFave(this);
	}
}
