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
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;

public class User {
	//Singleton
	transient private static User user = null;

	private static final String SETTINGS_PATH = "usersettings.sav";

	private String username = "";
	private String email = "";
	private String emailPassword = "";
	private String emailHost = "";
	private String emailSocketPort = "";
	private String emailPort = "";
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
			userSettings.add(user.getEmail());
			userSettings.add(user.getEmailPassword());
			userSettings.add(user.getEmailHost());
			userSettings.add(user.getEmailSocketPort());
			userSettings.add(user.getEmailPort());
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

			if (userSettings.size() > 5) {
				user.setUsername(userSettings.get(0));
				user.setEmail(userSettings.get(1));
				user.setEmailPassword(userSettings.get(2));
				user.setEmailHost(userSettings.get(3));
				user.setEmailSocketPort(userSettings.get(4));
				user.setEmailPort(userSettings.get(5));
				user.setHasUsedApp(userSettings.get(6));
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
	 * 
	 * @return users email password
	 */
	public String getEmailPassword() {
		return emailPassword;
	}

	/**
	 * sets the users email password as given parameter
	 * @param emailPassword
	 */
	public void setEmailPassword(String emailPassword) {
		this.emailPassword = emailPassword;
	}

	/**
	 * 
	 * @return users email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * sets users email as provided parameter
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}


	/**
	 * Get the host of the user's email server
	 * @return host of the user's email server
	 */
	public String getEmailHost() {
		return emailHost;
	}

	/**
	 * Set the host of the user's email server
	 * @param emailHost host of the user's email server
	 */

	public void setEmailHost(String emailHost) {
		this.emailHost = emailHost;
	}

	/**
	 * Get the socket port of the user's email server
	 * @return the socket port of the user's email server
	 */

	public String getEmailSocketPort() {
		return emailSocketPort;
	}

	/**
	 * Set the socket port of the user's email server
	 * @param emailSocketPort the socket port of the user's email server
	 */

	public void setEmailSocketPort(String emailSocketPort) {
		this.emailSocketPort = emailSocketPort;
	}

	/**
	 * Get the port of the user's email server
	 * @return the port of the user's email server
	 */

	public String getEmailPort() {
		return emailPort;
	}

	/**
	 * Set the port of the user's email server
	 * @param emailPort the port of the user's email server
	 */

	public void setEmailPort(String emailPort) {
		this.emailPort = emailPort;
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
}
