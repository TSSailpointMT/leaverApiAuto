package leaver.datamodels;

public class Identity {

	private String userName;
	private String password;
	private String email;
	private String firstName;
	private String lastName;
	private boolean iiqDisabled;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public boolean getIiqDisabled() {
		return iiqDisabled;
	}
	public void setIiqDisabled(boolean iiqDisabled) {
		this.iiqDisabled = iiqDisabled;
	}
	
	public Identity(String userName, String password, String email, String firstName, String lastName,
			boolean iiqDisabled) {
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.iiqDisabled = iiqDisabled;
	}
	
	
	
}
