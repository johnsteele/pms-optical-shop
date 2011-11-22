package greenwood.patients.model;

/**
 * Retains a patient's web access information.
 * 
 * @author John Steele
 */
public class WebInfo {

	private String email = "";
	private String username = "";
	private String password = "";
	
	public WebInfo () {}
	
	public WebInfo (String email, String user, String pass) {
		this.email = email;
		this.username = user;
		this.password = pass;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
