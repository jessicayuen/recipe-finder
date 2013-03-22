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
	private String emailHost = "";
	private String emailSocketPort = "";
	private String emailPort = "";
	
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
}
