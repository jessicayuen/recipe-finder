package team13.cmput301.recipefinder.UI;

import team13.cmput301.recipefinder.R;
import android.app.Activity;
import android.content.Context;
import android.view.Window;

public class CustomTitleBar {
	
	Activity activity;
	
	public CustomTitleBar(Activity activity) {
		this.activity = activity;
	}
	
	public void setUpTitleBar() {
		activity.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.custom_title_bar);
	}
	
	
}
