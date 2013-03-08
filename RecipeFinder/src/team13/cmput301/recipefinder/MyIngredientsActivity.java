package team13.cmput301.recipefinder;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Menu;
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
    private Button addButton, searchButton;
    private ListView myList;
    private boolean checkedItem;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ingredients);
        
        addButton = (Button) findViewById(R.id.addPH);
		searchButton = (Button) findViewById(R.id.searchPH);
		myList = (ListView) findViewById(R.id.listOfIng);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, listItems);
        myList.setAdapter(adapter);

        ingredientsEditText = (EditText) findViewById(R.id.ingredientItem);
 
    
    addButton.setOnClickListener(new View.OnClickListener() {			
		@SuppressWarnings("deprecation")
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String ingredient = ingredientsEditText.getText().toString();
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
			
			else{
				listItems.add(ingredient);
				addItem();
				myList.setAdapter(adapter);
			}
		}
		
    });
    
    
    }


	private void setListAdapter(ArrayAdapter<String> adapter) {
		// TODO Auto-generated method stub
		
	}

	public void addItem() {
        // Add the current string in the EditText to the ListView
        adapter.add(ingredientsEditText.getText().toString());
    }
	public boolean checkItem(boolean checkedItem){
		
		return checkedItem;
		
	}
	
	
}
