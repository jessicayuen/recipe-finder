package team13.cmput301.recipefinder;

import java.util.ArrayList;
import java.util.List;

public class RecipeManager {
	// Singleton
	transient private static RecipeManager recipeManager = null;
	
	private List<Recipe> faveRecipes;
	private List<Recipe> userRecipes;
	
	protected RecipeManager() {
		// Exists only to defeat instantiation
	}
	
	public static RecipeManager getRecipeManager() {
		if (recipeManager == null) {
			recipeManager = new RecipeManager();
			recipeManager.faveRecipes = new ArrayList<Recipe>();
			recipeManager.userRecipes = new ArrayList<Recipe>();
		}
		return recipeManager;
	}
	
	/**
	 * Add recipe to the favorite list.
	 * @param recipe
	 */
	public void addRecipeToFave(Recipe recipe) {
		if (userRecipes.contains(recipe)) {
			faveRecipes.add(recipe);
		}
	}
	
	/**
	 * Add recipe to the user list.
	 * @param recipe
	 */
	public void addRecipeToUser(Recipe recipe) {
		userRecipes.add(recipe);
	}

	public List<Recipe> getFaveRecipes() {
		return faveRecipes;
	}

	public List<Recipe> getUserRecipes() {
		return userRecipes;
	}
}
