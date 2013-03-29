import org.junit.BeforeClass;
import org.junit.Test;

import team13.cmput301.recipefinder.activities.*;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.EditText;


public class FirstTimeUserTest extends ActivityInstrumentationTestCase2
<FirstTimeUserActivity>{


	FirstTimeUserActivity FTUA;
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
		FTUA = getActivity();
		
		addUserNameTest = (EditText) FTUA.findViewById(
				team13.cmput301.recipefinder.R.id.username_input);
		addEmailTest = (EditText) FTUA.findViewById(
				team13.cmput301.recipefinder.R.id.email_input);
		addEmailPasswordTest = (EditText) FTUA.findViewById(
				team13.cmput301.recipefinder.R.id.email_password_input);
		addHostTest = (EditText) FTUA.findViewById(
				team13.cmput301.recipefinder.R.id.host_input);
		addPortTest = (EditText) FTUA.findViewById(
				team13.cmput301.recipefinder.R.id.port_input);
		addSPortTest = (EditText) FTUA.findViewById(
				team13.cmput301.recipefinder.R.id.sport_input);
		nextButtonTest = (Button) FTUA.findViewById(
				team13.cmput301.recipefinder.R.id.next);
	
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void checkViewCreation() {
		assertNotNull(getActivity());
		assertNotNull(addUserNameTest);
		assertNotNull(addEmailTest);
		assertNotNull(addEmailPasswordTest);
		assertNotNull(addHostTest);
		assertNotNull(addPortTest);
		assertNotNull(addSPortTest);
		assertNotNull(nextButtonTest);
	}
	
	@Test
	public void clearedTextSpace() {
		
		TouchUtils.tapView(this, addUserNameTest);
		sendKeys("T E S T");
		
		TouchUtils.clickView(this, nextButtonTest);
		assertEquals(addUserNameTest.length(), 0);
		
		TouchUtils.tapView(this, addEmailTest);
		sendKeys("T E S T");
		
	}

	
	@Test
	public void  testRecipeCreation() {
		
	}
}