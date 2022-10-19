package Model;
import java.util.Vector;

public class Classroom {

	private String classCode;
	private String className;
	
	private Vector<Student> students = new Vector<>();
	private Vector<Teacher> teachers = new Vector<>();
	
	public Classroom(String classCode, String className) {
		this.classCode = classCode;
		this.className = className;
	}
	
	public String getClassCode() {
		return classCode;
	}
	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}

	public Vector<Student> getStudents() {
		return students;
	}
	public void setStudents(Vector<Student> students) {
		this.students = students;
	}
	public Vector<Teacher> getTeachers() {
		return teachers;
	}
	public void setTeachers(Vector<Teacher> teachers) {
		this.teachers = teachers;
	}
	
	
}
