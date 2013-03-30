import org.junit.BeforeClass;
import org.junit.Test;

import team13.cmput301.recipefinder.activities.*;
import team13.cmput301.recipefinder.model.*;
import team13.cmput301.recipefinder.controllers.*;
import android.test.ActivityInstrumentationTestCase2;


public class IngredientsManagerTest 
	extends ActivityInstrumentationTestCase2<MainActivity> {

	Ingredient ingredient;
	Float Quantity;
	IngredientManager Im;
	
	public IngredientsManagerTest() {
		super(MainActivity.class);
	}

	@BeforeClass
	public void setUp() throws Exception{
		
		ingredient = new Ingredient("IngredientName", Quantity);
		Im = IngredientManager.getIngredientManager();
	}
	
	@Test
	public void testAddIngredient(){
		// Adding to user Ingredient list
		Im.addNewIngredient(ingredient);
		assertEquals(Im.getIngredientList().get(
				Im.getIngredientList().size() - 1), ingredient);
	}
	
	@Test
	public void testLoadIngredients() {
		Im.loadIngredients(getActivity());
		assertEquals(Im.getIngredientList().get(
				Im.getIngredientList().size() - 1), ingredient);
	}
	
	public void testsaveIngredients() {
		Im.saveAllIngredients(getActivity());
		assertEquals(Im.getIngredientList().get(Im.getIngredientList().size() - 1),
				ingredient);
	}
	public void DeleteIngredients() {
		Im.removeIngredient(0);
		assertEquals(Im.getIngredientList().get(Im.getIngredientList().size() - 1),
				ingredient);
	}
	
}