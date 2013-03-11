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
	public void writeToUserRecipeLog(Recipe recipe, Context ctx) {
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
	public void writeToUserRecipeLog(List<Recipe> recipes, Context ctx) {
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
