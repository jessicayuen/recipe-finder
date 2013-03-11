import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import team13.cmput301.recipefinder.ElasticSearchHelper;
import team13.cmput301.recipefinder.Recipe;
import team13.cmput301.recipefinder.Photo;

public class ElasticSearchHelperTest {

	Recipe recipe;
	ElasticSearchHelper esh;

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
		//esh.insertRecipe(recipe)
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testGetRecipe() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testSearchRecipes() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testSearchsearchRecipes() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testUpdateRecipes() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testDeleteRecipe() {
		fail("Not yet implemented"); // TODO
	}

}
