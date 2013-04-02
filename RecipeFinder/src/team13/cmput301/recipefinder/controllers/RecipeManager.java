package team13.cmput301.recipefinder.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import team13.cmput301.recipefinder.model.Recipe;
import team13.cmput301.recipefinder.model.User;
import team13.cmput301.recipefinder.resources.AuthorCompare;
import team13.cmput301.recipefinder.resources.NameCompare;
import team13.cmput301.recipefinder.resources.RatingCompare;
import team13.cmput301.recipefinder.sqlitedatabase.RecipeDataSource;
import android.content.Context;

/**
 * Singleton that manages all the user's recipes, including their own
 * and ones that were downloaded. Also keeps track of favourite recipes.
 * 
 * CMPUT301 W13 T13
 * @author Han (Jim) Wen, Jessica Yuen, Shen Wei Liao, Fangyu Li
 */
public class RecipeManager {
	// Singleton
	transient private static RecipeManager recipeManager = null;
	private RecipeDataSource dataSource;
	private List<Recipe> allRecipes;
	private List<Recipe> searchResultRecipes;

	/**
	 * DO NOT USE
	 * Constructor - exists only to defeat instantiation 
	 */
	protected RecipeManager() {}

	/**
	 * Returns the singleton RecipeManager
	 */
	public static RecipeManager getRecipeManager(Context context) {
		if (recipeManager == null) {
			startRecipeManager(context);
		}
		return recipeManager;
	}

	private static void startRecipeManager(Context context) {
		recipeManager = new RecipeManager();
		recipeManager.dataSource = new RecipeDataSource(context);
		recipeManager.allRecipes = new ArrayList<Recipe>();
		recipeManager.searchResultRecipes = new ArrayList<Recipe>();
	}

	/** 
	 * Loads the recipes from the database
	 */
	public void loadRecipes() {
		this.dataSource.open();
		this.allRecipes = this.dataSource.getAllRecipes();
		this.dataSource.close();
	}

	/**
	 * Stores the recipe into the database
	 * @param recipe The recipe to be stored
	 */
	public void addToUserRecipe(Recipe recipe) {
		this.dataSource.open();
		this.dataSource.insertRecipe(recipe);
		this.allRecipes.add(recipe);
		this.dataSource.close();
	}

	/**
	 * Set the recipe at location i to the one provided.
	 * @param recipe The new recipe
	 * @param i The location in the list
	 */
	public void setRecipeAtLocation(Recipe recipe, int i) {
		this.dataSource.open();
		this.dataSource.deleteRecipe(this.allRecipes.get(i));
		this.dataSource.insertRecipe(recipe);
		this.allRecipes.set(i, recipe);
		this.dataSource.close();
	}

	/**
	 * Return the index of the recipe in the list
	 * @param recipe The recipe to have the index returned
	 * @return the index of the recipe
	 */
	public int getRecipeIndex(Recipe recipe) {
		return this.allRecipes.indexOf(recipe);
	}
	
	/**
	 * Return the index of the recipes in the search recipe list
	 * @param recipe the recipe to be found
	 * @return index of the recipe
	 */
	public int getRecipeIndexOfSearchRecipe(Recipe recipe) {
		return this.searchResultRecipes.indexOf(recipe);
	}

	/**
	 * Removes a recipe from the ones stored
	 * @param recipe The recipe to be removed
	 */
	public void removeRecipe(Recipe recipe) {
		this.dataSource.open();
		this.dataSource.deleteRecipe(recipe);
		this.allRecipes.remove(recipe);
		this.dataSource.close();
	}

	/**
	 * Searches the list of recipes for the specified search term
	 * @param searchString The search term
	 * @param list The list to search within
	 * @return The list of recipes with matching the search term
	 */
	public List<Recipe> searchForRecipe(String searchString, List<Recipe> list) {
		List<Recipe> results = new ArrayList<Recipe>();
		for (int i = 0; i < list.size(); i++) {
			Recipe recipe = list.get(i);
			if (recipe.getName().toLowerCase().contains(
					searchString.toLowerCase()) || recipe.getAuthor()
					.toLowerCase().contains(searchString.toLowerCase())) {
				results.add(recipe);
			}
		}
		return results;
	}

	/**
	 * @return List of favorite recipes
	 */
	public List<Recipe> getFaveRecipes() {
		List<Recipe> faves = new ArrayList<Recipe>();

		for (int i = 0; i < this.allRecipes.size(); i++) {
			Recipe recipe = this.allRecipes.get(i);
			if (recipe.isFave())
				faves.add(recipe);
		}

		return faves;
	}

	/**
	 * @return List of own recipes
	 */
	public List<Recipe> getOwnRecipes() {
		List<Recipe> own = new ArrayList<Recipe>();

		for (int i = 0; i < this.allRecipes.size(); i++) {
			Recipe recipe = this.allRecipes.get(i);
			if (recipe.getAuthor().toLowerCase().
					equals(User.getUser().getUsername().toLowerCase()))
				own.add(recipe);
		}

		return own;
	}

	/**
	 * @return List of downloaded recipes
	 */
	public List<Recipe> getDownloadedRecipes() {
		List<Recipe> downloaded = new ArrayList<Recipe>();

		for (int i = 0; i < this.allRecipes.size(); i++) {
			Recipe recipe = this.allRecipes.get(i);
			if (!recipe.getAuthor().toLowerCase().
					equals(User.getUser().getUsername().toLowerCase()))
				downloaded.add(recipe);
		}

		return downloaded;
	}

	/**
	 * @return List of user recipes
	 */
	public List<Recipe> getAllRecipes() {
		return this.allRecipes;
	}

	/**
	 * Set the search result set
	 * @param result Search result set
	 */
	public void setSearchResultRecipes(List<Recipe> result) {
		this.searchResultRecipes = new ArrayList<Recipe>();
		this.searchResultRecipes = result;
	}

	/**
	 * @return The search result set
	 */
	public List<Recipe> getSearchResultRecipes() {
		return this.searchResultRecipes;
	}

	/**
	 * Checks whether provided recipe is in the recipe list
	 * @param recipe The recipe to be checked
	 * @return True if the recipe is in the recipe list, false otherwise
	 */
	public boolean checkForExistingRecipe(Recipe recipe) {
		for(Recipe r : this.allRecipes) {
			if(r.getId().equals(recipe.getId())){
				return false;
			}
		}
		return true;
	}

	/**
	 * Sorts the search result recipe list.
	 * @param sorted false to sort it in increasing order,true to sort in reverse.
	 */
	public void sortSearchResultByRating(boolean sorted) {
		if(!sorted)
			Collections.sort(searchResultRecipes, new RatingCompare());
		else
			Collections.reverse(searchResultRecipes);
	}

	/**
	 * Sorts the search result recipes list by name.
	 * @param sorted false to sort it in increasing order,true to sort in reverse.
	 */
	public void sortSearchResultByName(boolean sorted) {
		if(!sorted)
			Collections.sort(searchResultRecipes, new NameCompare());
		else
			Collections.reverse(searchResultRecipes);
	}

	/**
	 * Sorts the search result recipes by author
	 * @param sorted false to sort it in increasing order,true to sort in reverse.
	 */
	public void sortSearchResultByAuthor(boolean sorted) {
		if(!sorted)
			Collections.sort(searchResultRecipes, new AuthorCompare());
		else
			Collections.reverse(searchResultRecipes);
	}
}
