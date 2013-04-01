import org.junit.BeforeClass;
import org.junit.Test;

import team13.cmput301.recipefinder.activities.*;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.EditText;


public class FirstTimeUserTest extends ActivityInstrumentationTestCase2
<FirstTimeUserActivity>{


	FirstTimeUserActivity firstTimeUserActivity;
	EditText addUserNameTest, addEmailTest, addEmailPasswordTest, addInstrTest;
	EditText addHostTest, addPortTest, addSPortTest;
    Button nextButtonTest;

	private static final String TEMP_PATH = "temp.sav";

	public FirstTimeUserTest(){
		super(FirstTimeUserActivity.class);
	}

	@BeforeClass
	protected void setUp() throws Exception {
		super.setUp();
		firstTimeUserActivity = getActivity();
		
		addUserNameTest = (EditText) firstTimeUserActivity.findViewById(
				team13.cmput301.recipefinder.R.id.username_input);
		nextButtonTest = (Button) firstTimeUserActivity.findViewById(
				team13.cmput301.recipefinder.R.id.next);
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void checkViewCreation() {
		assertNotNull(getActivity());
		assertNotNull(addUserNameTest);
		assertNotNull(nextButtonTest);
	}
	
	@Test
	public void userNameInputText() {
		
		TouchUtils.tapView(this, addUserNameTest);
		sendKeys("T E S T");
		
	}

	
	@Test
	public void  nextButtonClicked() {
		TouchUtils.clickView(this, nextButtonTest);
		assertEquals(addUserNameTest.length(), 0);
		
	}
}