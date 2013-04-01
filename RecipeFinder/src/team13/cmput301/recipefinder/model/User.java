/**
 * User class holds all the user information.
 * 
 * CMPUT301 W13 T13
 * @author Han (Jim) Wen, Jessica Yuen, Shen Wei Liao, Fangyu Li
 */

package team13.cmput301.recipefinder.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	//Singleton
	transient private static User user = null;

	private static final String SETTINGS_PATH = "usersettings.sav";

	private String username = "";
	private String hasUsedApp = "0";

	/**
	 * DO NOT USE
	 * Constructor - Exists to only defeat instantiation. 
	 * Use getUser() to retrieve singleton User
	 */
	protected User() {}

	/**
	 * Retrieves the singleton User. Initializes it if it hasn't
	 * been initialized previous to call.
	 * @return
	 */
	public static User getUser() {
		if (user == null) {
			user = new User();
		}
		return user;
	}

	/**
	 * Stores the user settings into a file.
	 * @param ctx Context
	 */
	public void saveUserSettings(Context ctx) {
		try {
			List<String> userSettings = new ArrayList<String>();

			userSettings.add(user.getUsername());
			userSettings.add(user.getHasUsedApp());

			FileOutputStream fos = ctx.openFileOutput(SETTINGS_PATH, 
					Context.MODE_PRIVATE);
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(userSettings);
			out.close();

		} catch (Exception e) {
			Log.e("User", "Problems saving user settings", e); 
		}
	}

	@SuppressWarnings("unchecked")
	/**
	 * Retrieves the user settings on file.
	 * @param ctx
	 */
	public void loadUserSettings(Context ctx) {
		try {
			FileInputStream fis;
			ObjectInputStream in;
			List<String> userSettings;

			fis = ctx.openFileInput(SETTINGS_PATH);
			in = new ObjectInputStream(fis);
			userSettings = (ArrayList<String>) in.readObject();
			in.close();

			if (userSettings.size() > 1) {
				user.setUsername(userSettings.get(0));
				user.setHasUsedApp(userSettings.get(1));
			}

		} catch (Exception e) {
			Log.e("User", "Problems loading user settings", e); 
		}
	}

	/**
	 * 
	 * @return user name
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * sets the username to the provided parameter
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Checks whether the user has used Recipe Finder before
	 * @return > 0 if user has used app before, 0 otherwise
	 */
	public String getHasUsedApp() {
		return hasUsedApp;
	}

	/**
	 * Sets whether the user has used Recipe Finder before
	 * @param hasUsedApp whether the user has used Recipe Finder before
	 */
	public void setHasUsedApp(String hasUsedApp) {
		this.hasUsedApp = hasUsedApp;
	}
	
	public String toString() {
		return "Username: " + username + " Has used app: " + hasUsedApp;
	}
}
