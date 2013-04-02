import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import android.test.ActivityInstrumentationTestCase2;

import team13.cmput301.recipefinder.activities.MainActivity;
import team13.cmput301.recipefinder.model.Photo;
import team13.cmput301.recipefinder.model.Recipe;
import team13.cmput301.recipefinder.resources.*;


public class SortingRecipeTest  
extends ActivityInstrumentationTestCase2<MainActivity> {

	private List<Recipe> testList;
	private Recipe recipe, recipe2;
	
	public SortingRecipeTest() {
		super(MainActivity.class);
	}

	@BeforeClass
	public void setUp() throws Exception {
		super.setUp();
		testList = new ArrayList<Recipe>();
		recipe = new Recipe("test1", "this is tests", "tester1",
				new ArrayList<String>(), new ArrayList<String>(),
				new ArrayList<Photo>(), 4);
		testList.add(recipe);
		recipe2 = new Recipe("test2", "this is test", "tester2",
				new ArrayList<String>(), new ArrayList<String>(),
				new ArrayList<Photo>(), 2);
		testList.add(recipe2);
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	@Test
	public void testsortByRatingTest() {

		Collections.sort(testList, new RatingCompare());

		assertEquals(testList.get(0), recipe2);
		assertEquals(testList.get(1), recipe);
	}
	
	@Test
	public void sortByNameTest() {
		Collections.sort(testList, new NameCompare());
		assertEquals(testList.get(0), recipe);
		assertEquals(testList.get(1), recipe2);
	}
	
	@Test
	public void sortByAuthorTest() {
		Collections.sort(testList, new AuthorCompare());
		assertEquals(testList.get(0), recipe2);
		assertEquals(testList.get(1), recipe);
	}

}
