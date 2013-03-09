package team13.cmput301.recipefinder;

import java.util.ArrayList;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MyIngredientsActivity extends Activity {
	
    private ArrayList<String> listItems = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private EditText ingredientsEditText;
    private Button addButton, searchButton, deleteButton;
    private ListView myList;
    private boolean checkedItem = false;
    private String ingredient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ingredients);
        
        addButton = (Button) findViewById(R.id.addPH);
        deleteButton = (Button) findViewById(R.id.deletePH);
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
		        for(int i = 0; i < adapter.getCount(); ++i) {
		            myList.setItemChecked(i, false);
		        }
		        myList.setAdapter(adapter);
			}
		}
		
    });
    
    deleteButton.setOnClickListener(new View.OnClickListener() {			
		@SuppressLint("NewApi")
		@SuppressWarnings("deprecation")
		@Override
		public void onClick(View v) {
			deleteCheckedItems();
			myList.setAdapter(adapter);
		}
		
    });
    }


	public void addItem() {
        // Add the current string in the EditText to the ListView
        listItems.add(ingredient);
    }
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
	    for (int i = 0; i < count; i++) {
	        if (this.myList.isItemChecked(i)) {
	            listItems.remove(i);
	        }
	    }
	}
	
	
}
