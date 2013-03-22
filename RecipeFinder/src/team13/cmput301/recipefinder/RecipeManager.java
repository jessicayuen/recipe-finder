/**
 * Singleton that manages all the user's recipes, including their own
 * and ones that were downloaded. Also keeps track of favourite recipes.
 * 
 * CMPUT301 W13 T13
 * @author Han (Jim) Wen, Jessica Yuen, Shen Wei Liao, Fangyu Li
 */

package team13.cmput301.recipefinder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

public class RecipeManager {
	// Singleton
	transient private static RecipeManager recipeManager = null;

	private static final String PATH = "recipelog.sav";

	private List<Recipe> faveRecipes;
	private List<Recipe> userRecipes;
	private List<Recipe> ownRecipes;

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

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
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
			FileOutputStream fos = ctx.openFileOutput(PATH, Context.MODE_PRIVATE);
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(userRecipes);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	}

	/**
	 * add recipes from favorite list
	 * @param recipe
	 */
	public void addToFavList(Recipe recipe) {
		Recipe temp = recipe;
		int allIndex = userRecipes.indexOf(recipe);
		if(!faveRecipes.contains(recipe)){
			temp.setFave(true);
			if(ownRecipes.contains(recipe)){
				int ownIndex = ownRecipes.indexOf(recipe);
				ownRecipes.set(ownIndex, temp);
			}
			faveRecipes.add(recipe);
			userRecipes.set(allIndex, temp);	

			// TODO change it so that own list gets updated too
		}
	}

	/**
	 * remove recipes from favorite list
	 * @param recipe
	 */
	public void removeFromFavList(Recipe recipe) {
		Recipe temp = recipe;
		int index = userRecipes.indexOf(recipe);
		if(faveRecipes.contains(recipe)){
			temp.setFave(false);
			if(ownRecipes.contains(recipe)){
				int ownIndex = ownRecipes.indexOf(recipe);
				ownRecipes.set(ownIndex, temp);
			}
			faveRecipes.remove(recipe);
			userRecipes.set(index, temp);			
		}
	}
}
