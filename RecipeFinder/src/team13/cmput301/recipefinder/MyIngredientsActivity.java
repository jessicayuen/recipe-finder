 // Activity that allows user to keep track of their ingredients,

package team13.cmput301.recipefinder;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MyIngredientsActivity extends Activity {

	private ArrayList<String> listofItems = new ArrayList<String>();
	private ArrayAdapter<String> adapter;
	private EditText ingredientsEditText, quantityEditText;
	private Button addButton, searchButton, deleteButton, backButton;
	private ListView myList;
	private boolean checkedItem = false;
	private Ingredient ingredient;
	private String ingName, ingQuantity;
	private float quantity;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_ingredients);

		addButton = (Button) findViewById(R.id.addPH);
		deleteButton = (Button) findViewById(R.id.deletePH);
		backButton = (Button) findViewById(R.id.backButton);
		searchButton = (Button) findViewById(R.id.searchPH);
		myList = (ListView) findViewById(R.id.listOfIng);
		
		

		adapter = new ArrayAdapter<String>(this, 
				android.R.layout.simple_list_item_multiple_choice, listofItems);

		ingredientsEditText = (EditText) findViewById(R.id.ingredientItem);
		quantityEditText = (EditText) findViewById(R.id.quantityItem);
	
		/**
		 * Initiates the addButton functionality
		 */
		addButton.setOnClickListener(new View.OnClickListener() {			
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				
				ingName = ingredientsEditText.getText().toString();
				ingQuantity = quantityEditText.getText().toString();
				
				if (ingName.matches("")) {
					/* show a message if fields not entered */
					int duration = Toast.LENGTH_SHORT;
					Context context = getApplicationContext();
					Toast.makeText(context,"Enter a ingredient", duration).show();
				}
				if (!ingName.matches("")){
					addItems();
			    	ingredientsEditText.setText("");
					quantityEditText.setText("");
				}
				
			}

		});

		/**
		 * Listens for delete button click
		 */
		deleteButton.setOnClickListener(new View.OnClickListener() {			
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				deleteCheckedItems();
				myList.setAdapter(adapter);
			}

		});
		
		/**
		 * Exits the activity on Back button click
		 */
		backButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				finish();
			}
		});
		
		/**
		 * Listens for search button click
		 */
		searchButton.setOnClickListener(new View.OnClickListener() {			
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				searchCheckedItems();
				Intent myIntent = new Intent(
						MyIngredientsActivity.this, SearchResultsActivity.class);
				MyIngredientsActivity.this.startActivity(myIntent);
			}

		});
	}
	
	/**
	 * Check if the current item already exists in the list of ingredients
	 * @param checkedItem Item to be checked
	 * @return
	 */
	public boolean checkItem(boolean checkedItem){
		for(int index = 0; index < listofItems.size(); index++){
			if(listofItems.get(index).equals(ingredient)){
				checkedItem = true;
			} else {
				checkedItem = false;
			}
		}
		return checkedItem;
	}
	
	private void addItems(){
		listofItems.add(ingName + " : " + ingQuantity);
		myList.setAdapter(adapter);
		
		ingredient.setIngredient(ingName);
		quantity = Float.parseFloat(ingQuantity);
		ingredient.setQuantity(quantity);
		
		
	}

	/**
	 * Remove ingredients that were checked
	 */
	private void deleteCheckedItems() {

		int count = this.myList.getAdapter().getCount();
		for (int i = count - 1; i >= 0; i--) {
			if (this.myList.isItemChecked(i)) {
				listofItems.remove(i);
				adapter.notifyDataSetChanged();
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