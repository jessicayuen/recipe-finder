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
	private ArrayList<String> listofingredients = new ArrayList<String>();
	private ArrayList<Float> listofquantity = new ArrayList<Float>();
	private ArrayAdapter<String> adapter;
	private EditText ingredientsEditText, quantityEditText;
	private Button addButton, searchButton, deleteButton, backButton, plusButton, minusButton;
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
		plusButton = (Button) findViewById(R.id.plusPH);
		minusButton = (Button) findViewById(R.id.minusPH);
		myList = (ListView) findViewById(R.id.listOfIng);
		
		
		adapter = new ArrayAdapter<String>(this, 
				android.R.layout.simple_list_item_multiple_choice, listofItems);

		ingredientsEditText = (EditText) findViewById(R.id.ingredientItem);
		quantityEditText = (EditText) findViewById(R.id.quantityItem);
		
		IngredientManager.getIngredientManager().loadRecipes(getApplicationContext());
		for(int index = 0; index < IngredientManager.getIngredientManager().getIngredientList().size(); index++){
			String newIngredient = IngredientManager.getIngredientManager().getIngredientList().get(index).getIngredient();
			Float newQuantity = IngredientManager.getIngredientManager().getIngredientList().get(index).getQuantity();
			listofingredients.add(newIngredient);
			listofquantity.add(newQuantity);
			listofItems.add(newIngredient + " : " + newQuantity);
			myList.setAdapter(adapter);
			
		}
	
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
				if (checkItem(checkedItem)){
						Float newQuantity;
						
						for(int index = 0; index < listofingredients.size(); index++)
							if(ingName.matches(listofingredients.get(index))){
								quantity = listofquantity.get(index);
								newQuantity = listofquantity.get(index) + 1;
								listofquantity.set(index, newQuantity);
								listofItems.set(index, ingName + " : " + newQuantity.toString());
								adapter.notifyDataSetChanged();
							}
						
						ingredient = new Ingredient(ingName, quantity);
						IngredientManager.getIngredientManager().addNewIngredient(ingredient);
						
						int duration = Toast.LENGTH_SHORT;
						Context context = getApplicationContext();
						Toast.makeText(context,"Ingredient Already Exists! Quantity increased!", duration).show();
				
				}
				if (!ingName.matches("") && checkItem(checkedItem) == false){
					if(ingQuantity.matches("")){
						ingQuantity = "1";
						quantity = Float.parseFloat(ingQuantity);
						ingredient = new Ingredient(ingName, quantity);
						IngredientManager.getIngredientManager().addNewIngredient(ingredient);
						int duration = Toast.LENGTH_SHORT;
						Context context = getApplicationContext();
						Toast.makeText(context,"Ingredient Added!", duration).show();
						listofingredients.add(ingName);
						listofquantity.add(quantity);
						addItems();
				    	ingredientsEditText.setText("");
						quantityEditText.setText("");
					}
					else{
						quantity = Float.parseFloat(ingQuantity);
						ingredient = new Ingredient(ingName, quantity);
						IngredientManager.getIngredientManager().addNewIngredient(ingredient);
						int duration = Toast.LENGTH_SHORT;
						Context context = getApplicationContext();
						Toast.makeText(context,"Ingredient Added!", duration).show();
						listofingredients.add(ingName);
						listofquantity.add(quantity);
						addItems();
						ingredientsEditText.setText("");
						quantityEditText.setText("");
					}
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
				
				IngredientManager.getIngredientManager().saveAllIngredients(getApplicationContext());
				
				finish();
			}
		});
		
		plusButton.setOnClickListener(new View.OnClickListener() {			
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				incrCheckedItems();
				myList.setAdapter(adapter);
			}

		});
		
		minusButton.setOnClickListener(new View.OnClickListener() {			
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				decrCheckedItems();
				myList.setAdapter(adapter);
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
		for(int index = 0; index < listofingredients.size(); index++){
			if(listofingredients.get(index).equals(ingName)){
				checkedItem = true;
			} else {
				checkedItem = false;
			}
		}
		return checkedItem;
	}
	
	
	private void addItems(){
		listofItems.add(ingName + " : " + quantity);
		myList.setAdapter(adapter);
		
	}

	/**
	 * Remove ingredients that were checked
	 */
	private void deleteCheckedItems() {

		int count = this.myList.getAdapter().getCount();
		for (int i = count - 1; i >= 0; i--) {
			if (this.myList.isItemChecked(i)) {
				String currentIng = listofingredients.get(i);
				Float currentQuantity = listofquantity.get(i);
				listofItems.remove(i);
				listofingredients.remove(i);
				listofquantity.remove(i);
				adapter.notifyDataSetChanged();
				ingredient = new Ingredient(currentIng, currentQuantity);
				IngredientManager.getIngredientManager().removeAllIngredient(ingredient);
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
				String currentIng;
				Float newQuantity;
				currentIng = listofingredients.get(i);
				newQuantity = listofquantity.get(i) + 1 ;
				listofquantity.set(i, newQuantity);
				listofItems.set(i, currentIng + " : " + newQuantity.toString());
				
				ingredient = new Ingredient(currentIng, newQuantity - 1);
				IngredientManager.getIngredientManager().addNewIngredient(ingredient);
				
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
				String currentIng;
				Float newQuantity;
				currentIng = listofingredients.get(i);
				newQuantity = listofquantity.get(i) - 1;
				
			if(!(newQuantity <= 0)){
				listofquantity.set(i, newQuantity);
				listofItems.set(i, currentIng + " : " + newQuantity.toString());
				
				ingredient = new Ingredient(currentIng, newQuantity + 1);
				IngredientManager.getIngredientManager().removeIngredient(ingredient);
	
				adapter.notifyDataSetChanged();
				
				}
				else{
					listofItems.remove(i);
					listofingredients.remove(i);
					listofquantity.remove(i);
					adapter.notifyDataSetChanged();
					
					ingredient = new Ingredient(currentIng, newQuantity + 1);
					IngredientManager.getIngredientManager().removeAllIngredient(ingredient);
					
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