import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import android.test.ActivityInstrumentationTestCase2;

import team13.cmput301.recipefinder.activities.MainActivity;
import team13.cmput301.recipefinder.activities.RecipeListActivity;
import team13.cmput301.recipefinder.model.*;


public class RecipeTest extends ActivityInstrumentationTestCase2
<MainActivity>{

	public RecipeTest() {
		super(MainActivity.class);
		// TODO Auto-generated constructor stub
	}

	@Test
	public void testRecipeConstructor() {
		Recipe recipe = new Recipe("Name", "Description", "Author",
				new ArrayList<String>(), new ArrayList<String>(), new ArrayList<Photo>());
		
		assertEquals("Name", recipe.getName());
		assertEquals("Description", recipe.getDescription());
		assertEquals("Author", recipe.getAuthor());
		assertEquals(0, recipe.getIngredients().size());
		assertEquals(0, recipe.getInstructions().size());
		assertEquals(0, recipe.getPhotos().size());
	}

}
