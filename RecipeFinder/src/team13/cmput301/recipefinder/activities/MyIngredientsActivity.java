/**
 * Allows user to keep track of their ingredients and their quantity;
 * including deleting ingredients, adding ingredients, and
 * increasing/decreasing quantities.
 * 
 * CMPUT301 W13 T13
 * @author Han (Jim) Wen, Jessica Yuen, Shen Wei Liao, Fangyu Li
 */
package team13.cmput301.recipefinder.activities;

import java.util.ArrayList;
import java.util.List;

import team13.cmput301.recipefinder.R;
import team13.cmput301.recipefinder.controllers.IngredientManager;
import team13.cmput301.recipefinder.model.Ingredient;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MyIngredientsActivity extends Activity {

	private IngredientManager ingredManager;
	private ArrayList<String> displayList, autoFillDisplay;
	private ArrayList<String> searchList;
	private AutoCompleteTextView addIngredients;;
	private ArrayAdapter<String> adapter, autoFillAdapter;
	private EditText quantityEditText;
	private ListView myList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_ingredients);

		/* load ingredients*/
		ingredManager = IngredientManager.getIngredientManager();
		ingredManager.loadIngredients(this);

		displayList = new ArrayList<String>();
		autoFillDisplay = new ArrayList<String>();
		myList = (ListView) findViewById(R.id.listOfIng);
		adapter = new ArrayAdapter<String>(this, 
				android.R.layout.simple_list_item_multiple_choice, displayList);
		addIngredients = (AutoCompleteTextView) findViewById(R.id.autoFillAddIng);
		quantityEditText = (EditText) findViewById(R.id.quantityItem);
		autoFillAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, 
				autoFillDisplay);

		List<Ingredient> ingredList = ingredManager.getIngredientList();
		/* Display the ingredients in the list view */
		for(int i = 0; i < ingredList.size(); i++){
			displayList.add(ingredList.get(i).toString());
			myList.setAdapter(adapter);
			autoFillDisplay.add(ingredList.get(i).getIngredient());

		}
		
		/* number of letters required to have drop list shown is set to 1*/
		addIngredients.setThreshold(1);
		addIngredients.setAdapter(autoFillAdapter);
		
	}

	/**
	 * Saves the ingredients and displays it in the list view 
	 * on add button click
	 * @param view The view where the add button is
	 */
	public void addClicked(View view) {
		String name = addIngredients.getText().toString();
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
				addIngredients.setText("");
				quantityEditText.setText("");
				return;
			}
		}

		/* Ingredient doesn't exist */
		Ingredient ingredient = new Ingredient(name, quantity);
		ingredManager.addNewIngredient(ingredient);
		addIngredients.setAdapter(autoFillAdapter);
		ingredManager.saveAllIngredients(this);
		Toast.makeText(this,"Ingredient Added!", Toast.LENGTH_SHORT).show();
		displayList.add(ingredient.toString());
		myList.setAdapter(adapter);
		autoFillDisplay.add(ingredient.getIngredient());
		autoFillAdapter.notifyDataSetChanged();
		addIngredients.setAdapter(autoFillAdapter);
		addIngredients.setText("");
		quantityEditText.setText("");
	}

	/**
	 * Deletes the selected ingredient from the list view
	 * @param view The view where the delete button is
	 */
	public void deleteClicked(View view) {
		deleteCheckedItems();
		myList.setAdapter(adapter);
		addIngredients.setAdapter(autoFillAdapter);
	}

	/**
	 * Listens for plus button click 
	 * @param view The view where the plus button is
	 */
	public void plusClicked(View view) {
		incrCheckedItems(1);
		myList.setAdapter(adapter);
	}

	/**
	 * Listens for minus button click
	 * @param view The view where the minus button is
	 */
	public void minusClicked(View view) {
		decrCheckedItems(1);
		myList.setAdapter(adapter);
		addIngredients.setAdapter(autoFillAdapter);
	}

	/**
	 * Shows a message dialog to allow the user to change 
	 * the quantity of the items selected
	 * @param view The view where the changeQuantity button is
	 */
	public void changeQuantity(View view) {
		boolean itemChecked = false;
		
		/* Check if there is any items selected - if not don't show dialog */
		for (int i = 0; i < this.myList.getAdapter().getCount(); i++) {
			if (this.myList.isItemChecked(i))  {
				itemChecked = true;
				break;
			}
		}
		if (itemChecked == false)
			return;

		/* Set an EditText view to get user input */
		final EditText input = new EditText(this);
		input.setInputType(InputType.TYPE_CLASS_NUMBER);

		/* Setup the alert dialog */
		AlertDialog alertDialog =
				new AlertDialog.Builder(MyIngredientsActivity.this).create();
		alertDialog.setTitle("Enter quantity: ");
		alertDialog.setView(input);
		alertDialog.setButton(Dialog.BUTTON_POSITIVE, "+", 
				new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				if(input.getText().toString().equals("")){
				Toast.makeText(getApplicationContext(), "You must specify a quantity!"
						, Toast.LENGTH_SHORT).show();
				}
				else{
				int quantity = Integer.parseInt(input.getText().toString());
				MyIngredientsActivity.this.incrCheckedItems(quantity);
				}
			}
		});

		alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "-", 
				new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				if(input.getText().toString().equals("")){
				Toast.makeText(getApplicationContext(), "You must specify a quantity!"
						, Toast.LENGTH_SHORT).show();
				}
				else{
				int quantity = Integer.parseInt(input.getText().toString());
				MyIngredientsActivity.this.decrCheckedItems(quantity);
				}
			}
		});

		alertDialog.show();
	}

	/**
	 * Listens for search button being clicked
	 * @param view The view where the search button is
	 */
	public void searchClicked(View view) {
		searchCheckedItems();
		if(searchList.isEmpty()){
		Toast.makeText(getApplicationContext(), "You must select an ingredient!"
				, Toast.LENGTH_SHORT).show();
		}
		Intent searchIntent = new Intent(this, SearchResultsActivity.class);
		searchIntent.putStringArrayListExtra("Ingredients", searchList);
		startActivity(searchIntent);
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
				autoFillDisplay.remove(i);
				adapter.notifyDataSetChanged();
				autoFillAdapter.notifyDataSetChanged();
			}
		}
	}
	/**
	 * Increments the items that were checked
	 * @param quantity The amount to increase the quantity by
	 */
	private void incrCheckedItems(int quantity){
		int count = this.myList.getAdapter().getCount();
		for (int i = 0; i < count; i++) {
			if (this.myList.isItemChecked(i)) {
				Ingredient ingred = ingredManager.getIngredientList().get(i);
				ingred.setQuantity(ingred.getQuantity() + quantity);
				displayList.set(i, ingred.getIngredient() + 
						" : " + ingred.getQuantity());
				ingredManager.setIngredient(ingred, i);
				ingredManager.saveAllIngredients(this);
				adapter.notifyDataSetChanged();
			}
		}
	}

	/**
	 * Decrements the items that were checked
	 * @param quantity The amount to decrease the quantity by
	 */
	private void decrCheckedItems(int quantity){
		int count = this.myList.getAdapter().getCount();
		for (int i = 0; i < count; i++) {
			if (this.myList.isItemChecked(i)) {
				Ingredient ingred = ingredManager.getIngredientList().get(i);
				if (ingred.getQuantity() - quantity > 0){
					ingred.setQuantity(ingred.getQuantity() - quantity);
					displayList.set(i, ingred.getIngredient() + 
							" : " + ingred.getQuantity());
					ingredManager.setIngredient(ingred, i);
					ingredManager.saveAllIngredients(this);
				} else {
					ingredManager.removeIngredient(i);
					ingredManager.saveAllIngredients(this);
					displayList.remove(i);
					autoFillDisplay.remove(i);
				}
				adapter.notifyDataSetChanged();
				autoFillAdapter.notifyDataSetChanged();
			}
		}
	}

	/**
	 * Search the items that were checked
	 */
	private void searchCheckedItems() {
		int count = this.myList.getAdapter().getCount();
		searchList = new ArrayList<String>();
		for (int i = 0; i < count; i++) {
			if (this.myList.isItemChecked(i)) {
				searchList.add(ingredManager.getIngredientList().
						get(i).getIngredient());
			}
		}
	}
}
