import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import team13.cmput301.recipefinder.MainActivity;
import team13.cmput301.recipefinder.Photo;
import team13.cmput301.recipefinder.Recipe;
import team13.cmput301.recipefinder.RecipeManager;
import android.test.ActivityInstrumentationTestCase2;


public class RecipeManagerTest extends ActivityInstrumentationTestCase2<MainActivity>{

	Recipe recipe;
	
	public RecipeManagerTest() {
		super(MainActivity.class);
	}

	@Before
	public void setUp() throws Exception{
		recipe = new Recipe("Name", "Description", "Author",
				new ArrayList<String>(), new ArrayList<String>(), new ArrayList<Photo>());
	}
	
	@Test
	public void testAddRecipe(){
		RecipeManager rm = RecipeManager.getRecipeManager();
		rm.AddToUserRecipe(recipe, getActivity());

		assertEquals(rm.getUserRecipes().get(rm.getUserRecipes().size() - 1), recipe);
	}

}