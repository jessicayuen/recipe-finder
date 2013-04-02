import org.junit.BeforeClass;
import org.junit.Test;

import team13.cmput301.recipefinder.R;
import team13.cmput301.recipefinder.activities.RecipeListActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.EditText;


public class RecipeListActivityTest extends ActivityInstrumentationTestCase2
<RecipeListActivity>{
	
	RecipeListActivity recipeListActivity;
	EditText searchText;
	Button searchButton, showAllButton;

	public RecipeListActivityTest() {
		super(RecipeListActivity.class);
	}
	
	@BeforeClass
	protected void setUp() throws Exception {
		super.setUp();
		recipeListActivity = getActivity();
		searchText = (EditText) recipeListActivity.findViewById(R.id.searchBar);
		searchButton = (Button) recipeListActivity.findViewById(R.id.localSearch);
		showAllButton = (Button) recipeListActivity.findViewById(R.id.viewAll);
	}
	
	protected void tearDown() throws Exception{
		super.tearDown();
	}

	@Test
	public void testLocaleSearch() {
		
		TouchUtils.tapView(this, searchText);		
		TouchUtils.clickView(this, searchButton);		
		assertNotNull(recipeListActivity);
	}
	
	@Test
	public void testShowAll() {
		TouchUtils.clickView(this, showAllButton);
		assertNotNull(recipeListActivity);
	}

}
