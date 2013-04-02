
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;

import team13.cmput301.recipefinder.R;
import team13.cmput301.recipefinder.activities.CreateRecipeActivity;
import team13.cmput301.recipefinder.activities.MyIngredientsActivity;
import team13.cmput301.recipefinder.model.Ingredient;
import team13.cmput301.recipefinder.model.Photo;
import team13.cmput301.recipefinder.model.Recipe;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;


public class MyIngredientsActivityTest extends ActivityInstrumentationTestCase2
<MyIngredientsActivity>{
	
	public MyIngredientsActivityTest(){
		super(MyIngredientsActivity.class);
	}
	
	
	MyIngredientsActivity createIngredientsTest;
	EditText addIngredientTest, addQuantityTest;
	Button addButtonTest, changeQuantityTest, plusQuantityTest, minusQuantityTest, deleteIngredientTest;
	Button searchIngredientsTest;
	Ingredient ingred;
	List<Ingredient> IngredientListTest;
	
	@BeforeClass
	protected void setUp() throws Exception {
		super.setUp();
		createIngredientsTest = getActivity();
		addIngredientTest = (EditText) createIngredientsTest.findViewById(R.id.autoFillAddIng);
		addQuantityTest = (EditText) createIngredientsTest.findViewById(R.id.quantityItem);
		addButtonTest = (Button) createIngredientsTest.findViewById(R.id.add);
		changeQuantityTest = (Button) createIngredientsTest.findViewById(R.id.changeQuantity);
		plusQuantityTest = (Button) createIngredientsTest.findViewById(R.id.plus);
		minusQuantityTest = (Button) createIngredientsTest.findViewById(R.id.minus);
		deleteIngredientTest = (Button) createIngredientsTest.findViewById(R.id.delete);
		searchIngredientsTest = (Button) createIngredientsTest.findViewById(R.id.search);
	}
	
	protected void tearDown() throws Exception{
		super.tearDown();
	}
	
	public void testAddIngredientButton() {
		
		TouchUtils.tapView(this, addIngredientTest);	
		TouchUtils.tapView(this, addQuantityTest);
		TouchUtils.clickView(this, addButtonTest);		
		assertNotNull(createIngredientsTest);
	}
	
	public void testAddIngredientButton2() {
		
		TouchUtils.tapView(this, addIngredientTest);	
		TouchUtils.clickView(this, addButtonTest);		
		assertNotNull(createIngredientsTest);
	}
	
	public void testAddIngredientButton3() {
		
		TouchUtils.clickView(this, addButtonTest);		
		assertNotNull(createIngredientsTest);
	}
	
	public void testMinusClicked(){
		TouchUtils.clickView(this, minusQuantityTest);
	}
	
	public void testPlusClicked(){
		
		TouchUtils.clickView(this, plusQuantityTest);
	}
	public void testQuantityChangeClicked(){
		TouchUtils.clickView(this, changeQuantityTest);
	}

}
