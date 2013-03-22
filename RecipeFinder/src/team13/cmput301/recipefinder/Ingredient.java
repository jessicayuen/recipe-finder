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
	
	public void decreseQuantity(float quantity) {
		this.quantity -= quantity;
	}
	
	public void increaseQuantity(float quantity) {
		this.quantity += quantity;
	}
}
