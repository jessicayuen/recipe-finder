package team13.cmput301.recipefinder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

public class IngredientManager {

	// Singleton
	transient private static IngredientManager ingredientManager = null;

	private static final String PATH = "ingredientLog.sav";

	private List<Ingredient> ingredients;
	private List<String> ingredientNames;

	/**
	 * DO NOT USE
	 * Constructor - exists only to defeat instantiation 
	 */
	protected IngredientManager() {}

	/**
	 * Returns the singleton ingredient manager
	 */
	public static IngredientManager getRecipeManager() {
		if (ingredientManager == null) {
			ingredientManager = new IngredientManager();
			ingredientManager.ingredients = new ArrayList<Ingredient>();
			ingredientManager.ingredientNames = new ArrayList<String>();
		}
		return ingredientManager;
	}

	/**
	 * Loads the user ingredients and storing the names into a list
	 * to be used for autofilltextview
	 * @param ctx Context
	 */
	@SuppressWarnings("unchecked")
	public void loadRecipes(Context ctx) {
		try {
			FileInputStream fis;
			ObjectInputStream in;

			ingredientNames = new ArrayList<String>();
			// read user ingredients
			fis = ctx.openFileInput(PATH);
			in = new ObjectInputStream(fis);
			ingredients = (ArrayList<Ingredient>) in.readObject();
			in.close();

			// read favorite recipes
			for (Ingredient ingredient : ingredients) {
				ingredientNames.add(ingredient.getIngredient());
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void addNewIngredient(Ingredient ingredient) {
		int index = ingredients.indexOf(ingredient);
		if(index >= 0) {
			ingredients.get(index).increaseQuantity(ingredient.getQuantity());
		}
	}
}
