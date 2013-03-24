/**
 * Manages the list of ingredients including the saving and loading
 * of them from disk.
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
import android.util.Log;

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
	public static IngredientManager getIngredientManager() {
		if (ingredientManager == null) {
			ingredientManager = new IngredientManager();
			ingredientManager.ingredients = new ArrayList<Ingredient>();
			ingredientManager.ingredientNames = new ArrayList<String>();
		}
		return ingredientManager;
	}

	/**
	 * Loads the user ingredients and stores the names into a list
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

	/**
	 * Adds the ingredient into the list.
	 * @param ingredient The ingredient to be added
	 */
	public void addNewIngredient(Ingredient ingredient) {
		int index = getIngredientIndex(ingredient);
		if(index >= 0) {
			ingredients.get(index).increaseQuantity(ingredient.getQuantity());
		} else {
			if(ingredient.getQuantity() == 0){
				ingredient.setQuantity(1);
			}
			ingredients.add(ingredient);
			ingredientNames.add(ingredient.getIngredient());
		}
	}
	
	/**
	 * Remove ingredients from the ingredient list.
	 * @param ingredient The ingredient to remove
	 */
	public void removeIngredients(Ingredient ingredient) {
		int index = getIngredientIndex(ingredient);
		if(index >= 0) {
			Ingredient ingred = ingredients.get(index);
			ingred.decreseQuantity(ingredient.getQuantity());
			if(ingred.getQuantity() < 1){
				ingredients.remove(ingred);
				ingredientNames.remove(index);
			}			
		}
	}
	
	/**
	 * Save all ingredients into a file.
	 * @param ctx Context
	 */
	public void saveAllIngredients(Context ctx) {
		try {
			FileOutputStream fos = 
					ctx.openFileOutput(PATH, Context.MODE_PRIVATE);
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(ingredients);
			out.close();
		} catch (Exception e) {
			Log.e("User", "Problems saving user settings", e); 
		}
	}
	
	/**
	 * @return The list of ingredients
	 */
	public List<Ingredient> getIngredientList() {
		return ingredients;
	}
	
	/**
	 * @return The list of ingredients by name
	 */
	public List<String> getIngredientNamesList() {
		return ingredientNames;
	}
	
	/**
	 * @param ingredList Set the list of ingredients
	 */
	public void setIngredientList(List<Ingredient> ingredList) {
		this.ingredients = ingredList;
	}
	
	/**
	 * @param ingredNameList Set the list of ingredients by name
	 */
	public void setIngredNameList(List<String> ingredNameList) {
		this.ingredientNames = ingredNameList;
	}
	
	/**
	 * Get the index of the ingredient in the list.
	 * @param ingredient The ingredient to find the index of
	 * @return The index
	 */
	public int getIngredientIndex(Ingredient ingredient) {
		int index = 0;
		for(Ingredient ingred : ingredients) {
			if(ingredient.getIngredient().toLowerCase().
					startsWith(ingred.getIngredient().toLowerCase()) ||
					ingred.getIngredient().toLowerCase().
					startsWith(ingredient.getIngredient().toLowerCase())){
				return index;
			}
			index++;
		}
		return -1;
	}
}
