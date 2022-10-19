package Model;

public class Student {

	private String name;
	private String nim;
	private Integer age;
	private String gender;
	private String password;
	private boolean isTA;
	
	public Student(String name, String nim, Integer age, String gender, String password) {
		this.name = name;
		this.nim = nim;
		this.age = age;
		this.gender = gender;
		this.password = password;
		this.isTA = false;
	}
	
	public boolean isTA() {
		return isTA;
	}

	public void setTA(boolean isTA) {
		this.isTA = isTA;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNim() {
		return nim;
	}
	public void setNim(String nim) {
		this.nim = nim;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
