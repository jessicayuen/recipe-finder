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

		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, listItems);

		ingredientsEditText = (EditText) findViewById(R.id.ingredientItem);

		/**
		 * Initiates the addButton functionality
		 */
		addButton.setOnClickListener(new View.OnClickListener() {			
			@SuppressLint("NewApi")
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ingredient = ingredientsEditText.getText().toString();
				if (ingredient.matches("")) {
					/* show a message if fields not entered */
					int duration = Toast.LENGTH_SHORT;
					Context context = getApplicationContext();
					Toast.makeText(context,"Missing field ingredient", duration).show();
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

		deleteButton.setOnClickListener(new View.OnClickListener() {			
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				deleteCheckedItems();
				myList.setAdapter(adapter);
			}

		});
		backButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				finish();
			}
		});
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

	public void addItem() {
		// Add the current string in the EditText to the ListView
		listItems.add(ingredient);
	}
	// Checks if the current item already exists in the list of ingredients
	public boolean checkItem(boolean checkedItem){
		for(int index = 0; index < listItems.size(); index++){
			if(listItems.get(index).equals(ingredient)){
				checkedItem = true;
			}
			else{
				checkedItem = false;
			}
		}
		return checkedItem;
	}



	private void deleteCheckedItems() {

		int count = this.myList.getAdapter().getCount();
		for (int i = count - 1; i >= 0; i--) {
			if (this.myList.isItemChecked(i)) {
				listItems.remove(i);
				adapter.notifyDataSetChanged();
			}
		}

	}

	private void searchCheckedItems() {


	}


}