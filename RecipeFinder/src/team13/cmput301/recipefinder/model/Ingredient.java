package team13.cmput301.recipefinder.model;

import java.io.Serializable;

/**
 * Holds the data for an Ingredient - the name and their quantity.
 * 
 * CMPUT301 W13 T13
 * @author Han (Jim) Wen, Jessica Yuen, Shen Wei Liao, Fangyu Li
 */
public class Ingredient implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String ingredientName;
	private float quantity;
	
	public Ingredient(String ingredient, float quantity) {
		this.ingredientName = ingredient;
		this.quantity = quantity;
	}
	 
	
	/** 
	 * @param ingredient to be set
	 */
	public void setIngredient(String ingredient) {
		this.ingredientName =  ingredient;
	}
	
	/**
	 * return the ingredient name
	 * @return
	 */
	public String getIngredient() {
		return ingredientName;
	}
	
	/**
	 * sets the quatity of the recipe as specified
	 * @param quantity
	 */
	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}
	
	/**
	 * 
	 * @return quantity of the recipe
	 */
	public float getQuantity() {
		return quantity;
	}
	
	/**
	 * decrease the quantity of the ingredient, if the quantity provided is 0
	 * the decrement the ingredient quantity by 1
	 * @param quantity
	 */
	public void decreseQuantity(float quantity) {
		if(quantity == 0){
			this.quantity--;
		} else {
			this.quantity -= quantity;
		}
	}
	
	/**
	 * increment the quantity of the ingredient, if quantity is zero
	 * then the ingredient is incremented once
	 * @param quantity
	 */
	public void increaseQuantity(float quantity) {
		if(quantity == 0){
			this.quantity++;
		} else {
			this.quantity += quantity;
		}
	}
	

	 /**
	  * returns the string of the recipe
	  */
	public String toString() {
		return new String(ingredientName + " : " + Float.toString(quantity));
	}
}
