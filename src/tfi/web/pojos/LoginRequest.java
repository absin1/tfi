package tfi.web.pojos;

/**
 * This api is triggered by web app to login.
 * 
 * @param email
 *            String email of user.
 * @param password
 *            String password of user.
 * @return return User Profile as json Object. *
 * 
 * 
 */
public class LoginRequest extends TfiRequest {
	private String email;
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
