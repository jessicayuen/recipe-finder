/**
 * User class holds all the user information.
 * 
 * CMPUT301 W13 T13
 * @author Han (Jim) Wen, Jessica Yuen, Shen Wei Liao, Fangyu Li
 */

package team13.cmput301.recipefinder;

public class User {
	//Singleton
	transient private static User user = null;
	
	private String username = "";
	private String email = "";
	private String emailPassword = "";
	
	protected User() {
		// Exists only to defeat instantiation
	}
	
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
}
