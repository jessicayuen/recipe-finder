import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.junit.BeforeClass;
import org.junit.Test;

import android.test.ActivityInstrumentationTestCase2;
import team13.cmput301.recipefinder.model.*;
import team13.cmput301.recipefinder.activities.MainActivity;
import team13.cmput301.recipefinder.activities.RecipeListActivity;
import team13.cmput301.recipefinder.elasticsearch.*;

public class ElasticSearchHelperTest extends ActivityInstrumentationTestCase2
<MainActivity> {

	team13.cmput301.recipefinder.model.Recipe recipe;
	ElasticSearchHelper esh;

	public ElasticSearchHelperTest() {
		super(MainActivity.class);
		// TODO Auto-generated constructor stub
	}
	
	@BeforeClass
	public void setUpBeforeClass() throws Exception {
		recipe = new Recipe("Name", "Description", "Author",
				new ArrayList<String>(), new ArrayList<String>(),
				new ArrayList<Photo>());
		esh = ElasticSearchHelper.getElasticSearchHelper();
	}

	@Test
	public void testGetElasticSearchHelper() {
		// Verify we can instantiate the singleton
		assertNotNull(ElasticSearchHelper.getElasticSearchHelper());
		// Verify that it is indeed a singleton
		assertEquals(esh, ElasticSearchHelper.getElasticSearchHelper());
		assertEquals(ElasticSearchHelper.getElasticSearchHelper(),
				ElasticSearchHelper.getElasticSearchHelper());
	}

	@Test
	public void testInsertRecipe() {
		esh.insertRecipe(recipe);
		try {
			assertEquals(esh.getRecipe(recipe.getId()), recipe);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}

	@Test
	public void testSearchRecipes() {
		// 
		ArrayList<Recipe> searchList = esh.searchRecipes(recipe.getAuthor());
		assertTrue("Search by author", searchList.contains(recipe));
		
		searchList = esh.searchRecipes(recipe.getDescription());
		assertTrue("Search by description", searchList.contains(recipe));
		
		searchList = esh.searchRecipes(recipe.getName());
		assertTrue("Search by name", searchList.contains(recipe));
		
		searchList = esh.searchRecipes("*");
		assertTrue("Wildcard query", searchList.contains(recipe));
	}

	@Test
	public void testUpdateRecipeRating() {
	    
		try {
			esh.updateRecipeRating(recipe.getId(), recipe.getRating() + 1);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
