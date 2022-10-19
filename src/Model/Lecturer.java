package Model;
public class Lecturer extends Teacher{
	
	private String password;

	public Lecturer(String id, String name, String password) {
		super(id, name);
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
