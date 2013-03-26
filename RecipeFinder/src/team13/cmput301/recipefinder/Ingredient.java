/**
 * Holds the data for an Ingredient - the name and their quantity.
 * 
 * CMPUT301 W13 T13
 * @author Han (Jim) Wen, Jessica Yuen, Shen Wei Liao, Fangyu Li
 */

package team13.cmput301.recipefinder;

public class Ingredient {

	private String ingredientName;
	private float quantity;
	
	public Ingredient(String ingredient, float quantity) {
		this.ingredientName = ingredient;
		this.quantity = quantity;
	}
	
	public void setIngredient(String ingredient) {
		this.ingredientName =  ingredient;
	}
	
	public String getIngredient() {
		return ingredientName;
	}
	
	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}
	
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
}
