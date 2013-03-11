import static org.junit.Assert.*;

import org.junit.Test;

import team13.cmput301.recipefinder.CreateRecipeActivity;

import android.test.ActivityInstrumentationTestCase2;


public class CreateRecipeActivityTest extends ActivityInstrumentationTestCase2
<CreateRecipeActivity>{

	
	CreateRecipeActivity createRecipeTest;
	private static final String TEMP_PATH = "temp.sav";
	
	public CreateRecipeActivityTest(){
		super(CreateRecipeActivity.class);
	}

	
	protected void setUp() throws Exception {
		super.setUp();
		createRecipeTest = getActivity();
		createRecipeTest.getFileStreamPath(TEMP_PATH).delete();
	}
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
