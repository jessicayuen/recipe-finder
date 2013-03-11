import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import team13.cmput301.recipefinder.CreateRecipeActivity;
import team13.cmput301.recipefinder.Photo;
import team13.cmput301.recipefinder.Recipe;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;


public class CreateRecipeActivityTest extends ActivityInstrumentationTestCase2
<CreateRecipeActivity>{


	CreateRecipeActivity createRecipeTest;
	EditText addNameTest, addDesrTest, addIngredTest, addInstrTest;
	Button ingredButtonTest, instrButtonTest, ingredListButtonTest;
	Button instrListButtonTest, galleryButtonTest, createRecipeButtonTest;
	Button exitButtonTest;
	Gallery galleryTest;
	Recipe recipe;
	List<Recipe> recipeListTest;
	List<String> testInstrList, testIngredList;

	private static final String TEMP_PATH = "temp.sav";

	public CreateRecipeActivityTest(){
		super(CreateRecipeActivity.class);
	}

	@BeforeClass
	protected void setUp() throws Exception {
		super.setUp();
		createRecipeTest = getActivity();
		
		testInstrList = new ArrayList<String>();
		testInstrList.add("test1");
		
		testIngredList = new ArrayList<String>();
		testIngredList.add("test2");
		
		recipe =  new Recipe("testrecipe", "thisisatest", "mrtest",
				testIngredList, testInstrList, new ArrayList<Photo>());		

		addNameTest = (EditText) createRecipeTest.findViewById(
				team13.cmput301.recipefinder.R.id.addName);
		addDesrTest = (EditText) createRecipeTest.findViewById(
				team13.cmput301.recipefinder.R.id.addDescription);
		addIngredTest = (EditText) createRecipeTest.findViewById(
				team13.cmput301.recipefinder.R.id.addIngredients);
		addInstrTest = (EditText) createRecipeTest.findViewById(
				team13.cmput301.recipefinder.R.id.addInstructions);
		ingredButtonTest = (Button) createRecipeTest.findViewById(
				team13.cmput301.recipefinder.R.id.addIngredientsButn);
		instrButtonTest = (Button) createRecipeTest.findViewById(
				team13.cmput301.recipefinder.R.id.addInstructionsButn);
		ingredListButtonTest = (Button) createRecipeTest.findViewById(
				team13.cmput301.recipefinder.R.id.ingredientListButton);
		instrListButtonTest = (Button) createRecipeTest.findViewById(
				team13.cmput301.recipefinder.R.id.instructionListButton);
		galleryButtonTest = (Button) createRecipeTest.findViewById(
				team13.cmput301.recipefinder.R.id.addPicturesButn);
		galleryTest = (Gallery) createRecipeTest.findViewById(
				team13.cmput301.recipefinder.R.id.gallery);
		createRecipeButtonTest = (Button) createRecipeTest.findViewById(
				team13.cmput301.recipefinder.R.id.createButton);
		exitButtonTest = (Button) createRecipeTest.findViewById(
				team13.cmput301.recipefinder.R.id.exitButton);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void checkViewCreation() {
		assertNotNull(getActivity());
		assertNotNull(addNameTest);
		assertNotNull(addDesrTest);
		assertNotNull(addInstrTest);
		assertNotNull(addIngredTest);
		assertNotNull(ingredButtonTest);
		assertNotNull(instrButtonTest);
		assertNotNull(instrListButtonTest);
		assertNotNull(ingredListButtonTest);
		assertNotNull(galleryButtonTest);
		assertNotNull(galleryTest);
		assertNotNull(createRecipeButtonTest);
		assertNotNull(exitButtonTest);
	}
	
	@Test
	public void clearedTextSpace() {
		
		TouchUtils.tapView(this, addIngredTest);
		sendKeys("T E S T");
		
		TouchUtils.clickView(this, ingredButtonTest);
		assertEquals(addIngredTest.length(), 0);
		
		TouchUtils.tapView(this, addInstrTest);
		sendKeys("T E S T");
		
		TouchUtils.clickView(this, instrButtonTest);
		assertEquals(addInstrTest.length(), 0);
	}

	
	@Test
	public void  testRecipeCreation() {
		
		List<String> tempInstrList = new ArrayList<String>();
		List<String> tempIngredList = new ArrayList<String>();
		
		TouchUtils.tapView(this, addNameTest);
		sendKeys("T E S T R E C I P E");
		
		TouchUtils.tapView(this, addDesrTest);
		sendKeys("T H I S I S A T E S T");
		
		TouchUtils.tapView(this, addIngredTest);
		sendKeys("T E S T 1");
		tempIngredList.add(addIngredTest.getText().toString());
		
		TouchUtils.clickView(this, ingredButtonTest);
		assertEquals(addIngredTest.length(), 0);
		
		TouchUtils.tapView(this, addInstrTest);
		sendKeys("T E S T 2");
		tempInstrList.add(addInstrTest.getText().toString());
		
		TouchUtils.clickView(this, instrButtonTest);
		assertEquals(addInstrTest.length(), 0);
		
		TouchUtils.clickView(this, createRecipeButtonTest);

		assertEquals(recipe, new Recipe(addNameTest.getText().toString(),
				addDesrTest.getText().toString(), "mrtest", tempIngredList, tempInstrList,
				new ArrayList<Photo>()));
	}
	
	
}
