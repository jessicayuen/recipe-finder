/**
 * Holds all the user information.
 */

package team13.cmput301.recipefinder;

public class User {
	private String username;
	private String email;
	
	/**
	 * Constructor for User
	 * @param username
	 * @param email
	 */
	public User(String username, String email) {
		this.username = username;
		this.email = email;
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
