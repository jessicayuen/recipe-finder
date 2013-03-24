import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import team13.cmput301.recipefinder.MainActivity;
import team13.cmput301.recipefinder.Photo;
import team13.cmput301.recipefinder.Recipe;
import team13.cmput301.recipefinder.RecipeManager;
import android.test.ActivityInstrumentationTestCase2;


public class RecipeManagerTest 
	extends ActivityInstrumentationTestCase2<MainActivity> {

	Recipe recipe;
	RecipeManager rm;
	
	public RecipeManagerTest() {
		super(MainActivity.class);
	}

	@BeforeClass
	public void setUp() throws Exception{
		recipe = new Recipe("Name", "Description", "Author",
				new ArrayList<String>(), new ArrayList<String>(), 
				new ArrayList<Photo>());
		rm = RecipeManager.getRecipeManager();
	}
	
	@Test
	public void testAddRecipe(){
		// Adding to user recipe list
		rm.AddToUserRecipe(recipe, getActivity());
		assertEquals(rm.getUserRecipes().get(
				rm.getUserRecipes().size() - 1), recipe);
		
		// Adding to favorites recipe list
		rm.addToFavList(recipe, false);
		assertEquals(rm.getFaveRecipes().get(
				rm.getFaveRecipes().size() - 1), recipe);
	}
	
	@Test
	public void loadRecipeList() {
		rm.loadRecipes(getActivity());
		assertEquals(rm.getUserRecipes().get(
				rm.getUserRecipes().size() - 1), recipe);
	}
}