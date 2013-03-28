/**
 * Singleton that manages all the user's recipes, including their own
 * and ones that were downloaded. Also keeps track of favourite recipes.
 * 
 * CMPUT301 W13 T13
 * @author Han (Jim) Wen, Jessica Yuen, Shen Wei Liao, Fangyu Li
 */

package team13.cmput301.recipefinder;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;

public class RecipeManager {
	// Singleton
	transient private static RecipeManager recipeManager = null;

	private static final String PATH = "recipelog.sav";

	private List<Recipe> allRecipes;

	/**
	 * DO NOT USE
	 * Constructor - exists only to defeat instantiation 
	 */
	protected RecipeManager() {}

	/**
	 * Returns the singleton RecipeManager
	 */
	public static RecipeManager getRecipeManager() {
		if (recipeManager == null) {
			recipeManager = new RecipeManager();
			recipeManager.allRecipes = new ArrayList<Recipe>();
		}
		return recipeManager;
	}

	/**
	 * Loads the user and favorite recipes
	 * @param ctx Context
	 */
	@SuppressWarnings("unchecked")
	public void loadRecipes(Context ctx) {
		try {
			FileInputStream fis;
			ObjectInputStream in;
			
			// read user recipes
			fis = ctx.openFileInput(PATH);
			in = new ObjectInputStream(fis);
			getRecipeManager().allRecipes = 
					(ArrayList<Recipe>) in.readObject();
			in.close();
		} catch (Exception e) {
			Log.e("RecipeManager", "Problems loading recipes", e); 
		}
	}

	/**
	 * Appends the recipe into the existing user recipe list 
	 * and saves it onto file
	 * @param recipe Recipe to be appended
	 * @param ctx Context
	 */
	public void AddToUserRecipe(Recipe recipe, Context ctx) {
		try {
			getRecipeManager().allRecipes.add(recipe);
			FileOutputStream fos = ctx.openFileOutput(PATH, Context.MODE_PRIVATE);
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(getRecipeManager().allRecipes);
			out.close();
		} catch (Exception e) {
			Log.e("User", "Problems saving recipes to file", e); 
		}
	}

	/**
	 * Saves the provided list of user recipes onto file, overwriting previous
	 * @param recipes List of user recipes
	 * @param ctx Context
	 */
	public void AddToUserRecipe(List<Recipe> recipes, Context ctx) {
		try {
			getRecipeManager().allRecipes = recipes;
			FileOutputStream fos = ctx.openFileOutput(PATH, Context.MODE_PRIVATE);
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(getRecipeManager().allRecipes);
			out.close();
		} catch (Exception e) {
			Log.e("User", "Problems saving user settings", e); 
		}
	}
	
	/**
	 * Set the recipe at location i to the one provided.
	 * @param recipe The new recipe
	 * @param i The location in the list
	 */
	public void setRecipeAtLocation(Recipe recipe, int i, Context ctx) {
		try {
			getRecipeManager().allRecipes.set(i, recipe);
			FileOutputStream fos = ctx.openFileOutput(PATH, Context.MODE_PRIVATE);
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(getRecipeManager().allRecipes);
			out.close();
		} catch (Exception e) {
			Log.e("User", "Problems saving user settings", e); 
		}
	}

	/**
	 * Return the index of the recipe in the list
	 * @param recipe The recipe to have the index returned
	 * @return the index of the recipe
	 */
	public int getRecipeIndex(Recipe recipe) {
		return getRecipeManager().allRecipes.indexOf(recipe);
	}
	
	/**
	 * Removes a recipe from the ones stored
	 * @param recipe The recipe to be removed
	 */
	public void removeRecipe(Recipe recipe) {
		if(getRecipeManager().allRecipes.contains(recipe)){
			getRecipeManager().allRecipes.remove(recipe);
		}
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
			if (recipe.getName().contains(searchString)) {
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
		
		for (int i = 0; i < getRecipeManager().allRecipes.size(); i++) {
			Recipe recipe = getRecipeManager().allRecipes.get(i);
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
		
		for (int i = 0; i < getRecipeManager().allRecipes.size(); i++) {
			Recipe recipe = getRecipeManager().allRecipes.get(i);
			if (recipe.getAuthor().equals(User.getUser().getUsername()))
				own.add(recipe);
		}
		
		return own;
	}
	
	/**
	 * @return List of user recipes
	 */
	public List<Recipe> getAllRecipes() {
		return allRecipes;
	}
}
