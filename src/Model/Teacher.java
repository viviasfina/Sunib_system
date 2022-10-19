package Model;
import java.util.Vector;

public abstract class Teacher {
	private String id;
	private String name;
	private Vector<Classroom> classes;
	
	public Teacher(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public Vector<Classroom> getClasses() {
		return classes;
	}

	public void setClasses(Vector<Classroom> classes) {
		this.classes = classes;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	
	
}
