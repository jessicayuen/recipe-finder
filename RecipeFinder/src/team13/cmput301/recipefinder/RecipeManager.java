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

	private List<Recipe> faveRecipes, userRecipes, ownRecipes;
	private List<Recipe> searchModeUserRecipes, searchModeFaveRecipes;
	private List<Recipe> searchModeOwnRecipes;

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
			recipeManager.faveRecipes = new ArrayList<Recipe>();
			recipeManager.userRecipes = new ArrayList<Recipe>();
			recipeManager.ownRecipes = new ArrayList<Recipe>();
			recipeManager.searchModeFaveRecipes = new ArrayList<Recipe>();
			recipeManager.searchModeOwnRecipes = new ArrayList<Recipe>();
			recipeManager.searchModeUserRecipes = new ArrayList<Recipe>();
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
			
			recipeManager.ownRecipes = new ArrayList<Recipe>();
			recipeManager.faveRecipes = new ArrayList<Recipe>();
			// read user recipes
			fis = ctx.openFileInput(PATH);
			in = new ObjectInputStream(fis);
			userRecipes = (ArrayList<Recipe>) in.readObject();
			in.close();

			// read favorite recipes
			for (int i = 0; i < userRecipes.size(); i++) {
				Recipe recipe = userRecipes.get(i);
				if (recipe.isFave())
					faveRecipes.add(recipe);
				if(recipe.getAuthor().trim().compareTo(User.getUser()
						.getUsername().trim()) == 0){
					ownRecipes.add(recipe);
				}
			}
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
			userRecipes.add(recipe);
			if(recipe.getAuthor().trim().compareTo(User.getUser()
					.getUsername().trim()) == 0){
				ownRecipes.add(recipe);
			}
			FileOutputStream fos = ctx.openFileOutput(PATH, Context.MODE_PRIVATE);
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(userRecipes);
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
			userRecipes = recipes;
			FileOutputStream fos = ctx.openFileOutput(PATH, Context.MODE_PRIVATE);
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(userRecipes);
			out.close();
		} catch (Exception e) {
			Log.e("User", "Problems saving user settings", e); 
		}
	}

	// TODO is this method even used...
	/**
	 * Add recipe to the favorite list.
	 * @param recipe
	 */
	public void addRecipeToFave(Recipe recipe) {
		if(userRecipes.contains(recipe)) {
			faveRecipes.add(recipe);
		}
	}

	/**
	 * @return List of favorite recipes
	 */
	public List<Recipe> getFaveRecipes() {
		return faveRecipes;
	}

	/**
	 * @return List of user recipes
	 */
	public List<Recipe> getUserRecipes() {
		return userRecipes;
	}

	/**
	 * @return List of user's own recipes
	 */
	public List<Recipe> getOwnRecipes() {
		return ownRecipes;
	}
	
	public List<Recipe> getSearchModeOwnRecipes() {
		return searchModeOwnRecipes;
	}
	
	public List<Recipe> getSearchModeFaveRecipes() {
		return searchModeFaveRecipes;
	}
	
	public List<Recipe> getSearchModeUserRecipes() {
		return searchModeUserRecipes;
	}

	/**
	 * updates the all lists of recipes when called
	 */
	public void removeFromAllLists(Recipe recipe) {
		if(userRecipes.contains(recipe)){
			userRecipes.remove(recipe);
		}
		if(ownRecipes.contains(recipe)){
			ownRecipes.remove(recipe);
		}
		if(faveRecipes.contains(recipe)){
			faveRecipes.remove(recipe);
		}
		if(searchModeFaveRecipes.contains(recipe)){
			searchModeFaveRecipes.remove(recipe);
		}
		if(searchModeUserRecipes.contains(recipe)){
			searchModeUserRecipes.remove(recipe);
		}
		if(searchModeOwnRecipes.contains(recipe)){
			searchModeOwnRecipes.remove(recipe);
		}
	}

	/**
	 * add recipes to favorite list
	 * @param recipe
	 */
	public void addToFavList(Recipe recipe, boolean searchMode) {
		
		if(searchMode){
			int searchModeAllIndex = searchModeUserRecipes.indexOf(recipe);
			if(!searchModeFaveRecipes.contains(recipe)){
				if(searchModeOwnRecipes.contains(recipe)){
					int searchModeOwnIndex = searchModeOwnRecipes.indexOf(recipe);
					recipe.setFave(true);
					searchModeOwnRecipes.set(searchModeOwnIndex, recipe);
				}
				recipe.setFave(true);
				searchModeFaveRecipes.add(recipe);
				searchModeUserRecipes.set(searchModeAllIndex, recipe);
			}
			recipe.setFave(false);
		}
		int allIndex = userRecipes.indexOf(recipe);
		if(!faveRecipes.contains(recipe)){
			if(ownRecipes.contains(recipe)){
				int ownIndex = ownRecipes.indexOf(recipe);
				recipe.setFave(true);
				ownRecipes.set(ownIndex, recipe);
			}
			recipe.setFave(true);
			faveRecipes.add(recipe);
			userRecipes.set(allIndex, recipe);
		}
	}
	
	/**
	 * remove recipes from favorite list
	 * @param recipe
	 */
	public void removeFromFavList(Recipe recipe, boolean searchMode) {
		if(searchMode){
			int searchModeAllIndex = searchModeUserRecipes.indexOf(recipe);
			if(searchModeFaveRecipes.contains(recipe)){
				searchModeFaveRecipes.remove(recipe);
				if(searchModeOwnRecipes.contains(recipe)){
					int searchModeOwnIndex = searchModeOwnRecipes.indexOf(recipe);
					recipe.setFave(false);
					searchModeOwnRecipes.set(searchModeOwnIndex, recipe);
				}
				recipe.setFave(false);
				searchModeUserRecipes.set(searchModeAllIndex, recipe);
			}
			recipe.setFave(true);
		}
		int index = userRecipes.indexOf(recipe);
		if(faveRecipes.contains(recipe)){
			faveRecipes.remove(recipe);
			if(ownRecipes.contains(recipe)){
				int ownIndex = ownRecipes.indexOf(recipe);
				recipe.setFave(false);
				ownRecipes.set(ownIndex, recipe);
			}
			recipe.setFave(false);
			userRecipes.set(index, recipe);			
		}
	}
	
	public void findRecipesWithKeyWord(String key) {
		searchModeFaveRecipes = new ArrayList<Recipe>();
		searchModeOwnRecipes = new ArrayList<Recipe>();
		searchModeUserRecipes = new ArrayList<Recipe>();
		
		for(Recipe recipe : userRecipes) {
			if(recipe.getName().toUpperCase().contains(key.toUpperCase())){
				searchModeUserRecipes.add(recipe);
			}
		}
		for(Recipe recipe : faveRecipes) {
			if(recipe.getName().toUpperCase().contains(key.toUpperCase())){
				searchModeFaveRecipes.add(recipe);
			}
		}
		for(Recipe recipe : ownRecipes) {
			if(recipe.getName().toUpperCase().contains(key.toUpperCase())){
				searchModeOwnRecipes.add(recipe);
			}
		}
	}
}
