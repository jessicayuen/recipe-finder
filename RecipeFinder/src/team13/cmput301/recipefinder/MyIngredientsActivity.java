/**
 * Activity that allows user to keep track of their ingredients,
 * including addition, deletion and searching with selected ingredients.
 * 
 * CMPUT301 W13 T13
 * @author Han (Jim) Wen, Jessica Yuen, Shen Wei Liao, Fangyu Li
 */

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

	private ArrayList<String> listItems = new ArrayList<String>();
	private ArrayAdapter<String> adapter;
	private EditText ingredientsEditText;
	private Button addButton, searchButton, deleteButton, backButton;
	private ListView myList;
	private boolean checkedItem = false;
	private String ingredient;

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
				android.R.layout.simple_list_item_multiple_choice, listItems);

		ingredientsEditText = (EditText) findViewById(R.id.ingredientItem);

		/**
		 * Initiates the addButton functionality
		 */
		addButton.setOnClickListener(new View.OnClickListener() {			
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				ingredient = ingredientsEditText.getText().toString();
				if (ingredient.matches("")) {
					/* show a message if fields not entered */
					int duration = Toast.LENGTH_SHORT;
					Context context = getApplicationContext();
					Toast.makeText(context,"Enter a ingredient", duration).show();
				}
				if (checkItem(checkedItem) == true){
					int duration = Toast.LENGTH_SHORT;
					Context context = getApplicationContext();
					Toast.makeText(context,"Ingredient already exists", duration).show();
				}

				if (!ingredient.matches("") && checkItem(checkedItem) == false){
					addItem();
					myList.setAdapter(adapter);
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
				Intent myIntent = new Intent(MyIngredientsActivity.this, SearchResultsActivity.class);
				MyIngredientsActivity.this.startActivity(myIntent);
			}

		});
	}

	/**
	 * Add a item to the list of ingredients
	 */
	public void addItem() {
		listItems.add(ingredient);
	}
	
	/**
	 * Check if the current item already exists in the list of ingredients
	 * @param checkedItem Item to be checked
	 * @return
	 */
	public boolean checkItem(boolean checkedItem){
		for(int index = 0; index < listItems.size(); index++){
			if(listItems.get(index).equals(ingredient)){
				checkedItem = true;
			} else {
				checkedItem = false;
			}
		}
		return checkedItem;
	}

	/**
	 * Remove ingredients that were checked
	 */
	private void deleteCheckedItems() {

		int count = this.myList.getAdapter().getCount();
		for (int i = count - 1; i >= 0; i--) {
			if (this.myList.isItemChecked(i)) {
				listItems.remove(i);
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