/**
 * Allows user to keep track of their ingredients and their quantity;
 * including deleting ingredients, adding ingredients, and
 * increasing/decreasing quantities.
 * 
 * CMPUT301 W13 T13
 * @author Han (Jim) Wen, Jessica Yuen, Shen Wei Liao, Fangyu Li
 */
package team13.cmput301.recipefinder;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MyIngredientsActivity extends Activity {

	private IngredientManager ingredManager;
	private ArrayList<String> displayList;
	private ArrayAdapter<String> adapter;
	private EditText ingredientsEditText, quantityEditText;
	private ListView myList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_ingredients);

		/* load ingredients*/
		ingredManager = IngredientManager.getIngredientManager();
		ingredManager.loadIngredients(this);

		displayList = new ArrayList<String>();
		myList = (ListView) findViewById(R.id.listOfIng);
		adapter = new ArrayAdapter<String>(this, 
				android.R.layout.simple_list_item_multiple_choice, displayList);
		ingredientsEditText = (EditText) findViewById(R.id.ingredientItem);
		quantityEditText = (EditText) findViewById(R.id.quantityItem);

		List<Ingredient> ingredList = ingredManager.getIngredientList();
		/* Display the ingredients in the list view */
		for(int i = 0; i < ingredList.size(); i++){
			displayList.add(ingredList.get(i).toString());
			myList.setAdapter(adapter);
		}
	}

	/**
	 * Saves the ingredients and displays it in the list view 
	 * on add button click
	 * @param view The view where the add button is
	 */
	public void addClicked(View view) {
		String name = ingredientsEditText.getText().toString();
		float quantity = 1;

		/* Parse quantity */
		if (!quantityEditText.getText().toString().equals("")) 
			quantity = Float.parseFloat(quantityEditText.getText().toString());

		/* show a message if fields are not entered */
		if (name.equals("")) {
			Toast.makeText(this, "Enter a ingredient", Toast.LENGTH_SHORT).show();
			return;
		}

		/* Check if ingredient already exists */
		List<Ingredient> ingredList = ingredManager.getIngredientList();
		for (int i = 0; i < ingredList.size(); i++) {
			Ingredient ingred = ingredList.get(i);

			/* Ingredient already exists */
			if (name.equals(ingred.getIngredient())) {
				ingred.setQuantity(ingred.getQuantity() + 1);
				displayList.set(i, ingred.toString());
				adapter.notifyDataSetChanged();
				ingredManager.setIngredient(ingred, i);
				ingredManager.saveAllIngredients(this);
				Toast.makeText(this, "Ingredient Already Exists! " +
						"Quantity increased!", Toast.LENGTH_SHORT).show();
				ingredientsEditText.setText("");
				quantityEditText.setText("");
				return;
			}
		}

		/* Ingredient doesn't exist */
		Ingredient ingredient = new Ingredient(name, quantity);
		ingredManager.addNewIngredient(ingredient);
		ingredManager.saveAllIngredients(this);
		Toast.makeText(this,"Ingredient Added!", Toast.LENGTH_SHORT).show();
		displayList.add(ingredient.toString());
		myList.setAdapter(adapter);
		ingredientsEditText.setText("");
		quantityEditText.setText("");
	}

	/**
	 * Deletes the selected ingredient from the list view
	 * @param view The view where the delete button is
	 */
	public void deleteClicked(View view) {
		deleteCheckedItems();
		myList.setAdapter(adapter);
	}

	/**
	 * Listens for plus button click 
	 * @param view The view where the plus button is
	 */
	public void plusClicked(View view) {
		incrCheckedItems();
		myList.setAdapter(adapter);
	}

	/**
	 * Listens for minus button click
	 * @param view The view where the minus button is
	 */
	public void minusClicked(View view) {
		decrCheckedItems();
		myList.setAdapter(adapter);
	}

	/**
	 * Listens for search button being clicked
	 * @param view The view where the search button is
	 */
	public void searchClicked(View view) {
		searchCheckedItems();
		Intent myIntent = new Intent(
				MyIngredientsActivity.this, SearchResultsActivity.class);
		MyIngredientsActivity.this.startActivity(myIntent);
	}

	/**
	 * Returns to the previous activity on back button click
	 * and saves the state of the current ingredients
	 * @param view
	 */
	public void backClicked(View view) {
		finish();
	}

	/**
	 * Remove ingredients that were checked
	 */
	private void deleteCheckedItems() {
		int count = this.myList.getAdapter().getCount();
		for (int i = count - 1; i >= 0; i--) {
			if (this.myList.isItemChecked(i)) {
				ingredManager.removeIngredient(i);
				ingredManager.saveAllIngredients(this);
				displayList.remove(i);
				adapter.notifyDataSetChanged();
			}
		}
	}
	/**
	 * Increments the items that were checked by 1
	 */
	private void incrCheckedItems(){
		int count = this.myList.getAdapter().getCount();
		for (int i = count - 1; i >= 0; i--) {
			if (this.myList.isItemChecked(i)) {
				Ingredient ingred = ingredManager.getIngredientList().get(i);
				ingred.setQuantity(ingred.getQuantity() + 1);
				displayList.set(i, ingred.getIngredient() + 
						" : " + ingred.getQuantity());
				ingredManager.setIngredient(ingred, i);
				ingredManager.saveAllIngredients(this);
				adapter.notifyDataSetChanged();
			}
		}
	}

	/**
	 * Decrements the items that were checked by 1
	 */
	private void decrCheckedItems(){
		int count = this.myList.getAdapter().getCount();
		for (int i = count - 1; i >= 0; i--) {
			if (this.myList.isItemChecked(i)) {
				Ingredient ingred = ingredManager.getIngredientList().get(i);
				if (ingred.getQuantity() > 0){
					ingred.setQuantity(ingred.getQuantity() - 1);
				displayList.set(i, ingred.getIngredient() + 
						" : " + ingred.getQuantity());
				ingredManager.setIngredient(ingred, i);
				ingredManager.saveAllIngredients(this);
				adapter.notifyDataSetChanged();
				}
				if (ingred.getQuantity() <= 0){
					ingredManager.removeIngredient(i);
					ingredManager.saveAllIngredients(this);
					displayList.remove(i);
					adapter.notifyDataSetChanged();
				}
			}
		}
	}

	/**
	 * Search the items that were checked
	 */
	private void searchCheckedItems() {
		// TO BE IMPLEMENTED
	}
}