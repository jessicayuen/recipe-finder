package team13.cmput301.recipefinder.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;


/**
 * Recipe class that models a Recipe
 * 
 * CMPUT301 W13 T13
 * @author Han (Jim) Wen, Jessica Yuen, Shen Wei Liao, Fangyu Li
 */
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
	private int numOfRatings;
	private float totalRating;
	private Date date;
	private UUID id;
	private long sqlID;

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
		this.numOfRatings = 0;
		this.totalRating = 0;
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
			List<Photo> photos, float rating, int numOfRatings,
			float totalRating) {
		this(name, description, author, ingredients, instructions,
				photos, rating);
		this.numOfRatings = numOfRatings;
		this.totalRating = totalRating;
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
	 * @param date
	 */
	public Recipe(String name, String description, String author,
			List<String> ingredients, List<String> instructions, 
			List<Photo> photos, float rating, int numOfRatings,
			float totalRating, Date date, boolean fave,
			UUID id, long sqlID) {
		this(name, description, author, ingredients, instructions,
				photos, rating, numOfRatings, totalRating);
		this.date = date;
		this.fave = fave;
		this.id = id;
		this.sqlID = sqlID;
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
		String instr = instructionsToString();
		String ingred = ingredientsToString();

		return new String(name + "\n" +
				author + "\n\n\n" +
				"Description:\n" + description + "\n\n" +
				"Instructions:\n" + instr + "\n" +
				"Ingredients:\n" + ingred);
	}
	
	private String instructionsToString() {
		String instr = "";
		for (int i = 0; i < instructions.size(); i++)
			instr = instr.concat(i + 1 + ". " + instructions.get(i) + "\n");
		return instr;
	}
	
	private String ingredientsToString() {
		String ingred = "";
		for (int i = 0; i < ingredients.size(); i++)
			ingred = ingred.concat("->" + ingredients.get(i) + "\n");
		return ingred;
	}

	/**
	 * @return the String version of the recipe object
	 */
	public String toString() {
		return "[ name: " + name + ", " + "author: " + 
				author + ", " + "description: " + description + "]";
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
		if(numOfRatings > 0){
			calculateAvgRating();
		}
		return rating;
	}

	/**
	 * @param rating Set the recipe rating to this
	 */
	public void setRating(float rating) {
		this.totalRating += rating;
		this.numOfRatings++;
		calculateAvgRating();
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
	 * @param fave Sets whether the recipe is favorited
	 */
	public void setFave(boolean fave) {
		this.fave = fave;
	}

	/**
	 * Set the uuid of the recipe
	 */
	public void setId(UUID id) {
		this.id = id;
	}

	/**
	 * @return the SQL ID;
	 */
	public long getSqlID() {
		return sqlID;
	}

	/**
	 * @param sqlID Sets the sqlID to this
	 */
	public void setSqlID(long sqlID) {
		this.sqlID = sqlID;
	}

	private void calculateAvgRating() {
		rating = totalRating / numOfRatings;
	}
	
	/**
	 * Return the total sum rating of this recipe
	 * @return
	 */
	public float getTotalRating() {
		return totalRating;
	}
	
	/**
	 * Return the number of rating son this recipe
	 * @return
	 */
	public int getNumOfRatings() {
		return numOfRatings;
	}
	
	/**
	 * Set the total rating sum of this recipe as totalRating
	 * @param totalRating
	 */
	public void setTotalRating(int totalRating) {
		this.totalRating = totalRating;
	}
	
	/**
	 * Set the total number of ratings of this recipe as provided
	 * @param numOfRating
	 */
	public void setNumOfRatings(int numOfRating) {
		this.numOfRatings = numOfRating;
	}
}
