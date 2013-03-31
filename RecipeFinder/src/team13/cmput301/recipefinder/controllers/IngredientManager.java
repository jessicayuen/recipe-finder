/**
 * Manages the list of ingredients including the saving and loading
 * of them from disk.
 * 
 * CMPUT301 W13 T13
 * @author Han (Jim) Wen, Jessica Yuen, Shen Wei Liao, Fangyu Li
 */

package team13.cmput301.recipefinder.controllers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import team13.cmput301.recipefinder.model.Ingredient;

import android.content.Context;
import android.util.Log;

public class IngredientManager {

	// Singleton
	transient private static IngredientManager ingredientManager = null;
	private static final String PATH = "ingredientLog.sav";

	private List<String> ingredientAutoFillList;
	private ArrayList<Ingredient> ingredients;

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
			ingredientManager.ingredientAutoFillList = new ArrayList<String>();
		}
		return ingredientManager;
	}

	/**
	 * Loads the user ingredients and stores the names into a list
	 * to be used for autofilltextview
	 * @param ctx Context
	 */
	@SuppressWarnings("unchecked")
	public void loadIngredients(Context ctx) {
		try {
			FileInputStream fis;
			ObjectInputStream in;

			getIngredientManager().ingredients = new ArrayList<Ingredient>();
			// read ingredients
			fis = ctx.openFileInput(PATH);
			in = new ObjectInputStream(fis);
			getIngredientManager().ingredients = (ArrayList<Ingredient>) in.readObject();
			
			for(Ingredient i : ingredients) {
				getIngredientManager().ingredientAutoFillList.add(i.getIngredient());
			}
			in.close();
		} catch (Exception e) {
			Log.e("IngredientManager", "Problems loading ingredients: " + e);
		}
	}

	/**
	 * Adds the ingredient into the end of the list.
	 * @param ingredient The ingredient to be added
	 */
	public void addNewIngredient(Ingredient ingredient) {
		getIngredientManager().ingredients.add(ingredient);
		getIngredientManager().ingredientAutoFillList.add(ingredient.getIngredient());
	}
	
	/** 
	 * Replace the ingredient at location i with the provided ingredient
	 * @param ingredient The ingredient to replace with
	 * @param i The position to be replaced.
	 */
	public void setIngredient(Ingredient ingredient, int i) {
		if (getIngredientManager().ingredients.size() < i) {
			try {
				throw new Exception("Setting ingredient at a invalid index");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			getIngredientManager().ingredients.set(i, ingredient);
		}
			
	}

	/**
	 * Remove ingredient at index i
	 * @param i The index where the ingredient is
	 */
	public void removeIngredient(int i) {
		if (getIngredientManager().ingredients.size() < i) {
			try {
				throw new Exception("Removing ingredient at a invalid index");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			getIngredientManager().ingredients.remove(i);
			getIngredientManager().ingredientAutoFillList.remove(i);
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
			out.writeObject(getIngredientManager().ingredients);
			out.close();
		} catch (Exception e) {
			Log.e("IngredientManager", "Problems saving ingredients", e); 
		}
	}
	
	/**
	 * @return The list of ingredients
	 */
	public List<Ingredient> getIngredientList() {
		return getIngredientManager().ingredients;
	}
	
	public List<String> getIngredientAutoFillList() {
		return getIngredientManager().ingredientAutoFillList;
	}
	
	/**
	 * @param ingredList Set the list of ingredients
	 */
	public void setIngredientList(ArrayList<Ingredient> ingredList) {
		getIngredientManager().ingredients = ingredList;
		getIngredientManager().ingredientAutoFillList = new ArrayList<String>();
		for(Ingredient i : this.ingredients) {
			getIngredientManager().ingredientAutoFillList.add(i.getIngredient());
		}
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
