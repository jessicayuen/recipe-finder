/**
 * Holds all the user information.
 */

package team13.cmput301.recipefinder;

public class User {
	//Singleton
	transient private static User user = null;
	
	private String username = "";
	private String email = "";
	
	protected User() {
		// Exists only to defeat instantiation
	}
	
	public static User getUser() {
		if (user == null) {
			user = new User();
		}
		return user;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
