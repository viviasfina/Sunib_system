package Controller;
import java.util.*;

import javax.xml.transform.Templates;

import Model.Classroom;
import Model.LaboratoryAssistant;
import Model.Lecturer;
import Model.Student;
import Model.Teacher;
import Model.Template;

public class UserController {

	private static UserController uc = null;
	Scanner scan = new Scanner(System.in);
	Template t = Template.getInstance();
	private UserController() {}
	
	public static UserController getInstance() {
		// TODO Auto-generated constructor stub
		
		if (uc == null)
			uc = new UserController();
		
		return uc;
	}
	
	public static Object checkUser(Vector<Teacher> teachers, Vector<Student> users, String nim, String password) {
		
		if (nim.charAt(0) == '2') {
			for (Student student : users) {
				if (student.getNim().equals(nim) &&
						student.getPassword().equals(password)
						) {
					return student;
				}
			}
		}
		else {
			for (Object teacher : teachers) {
				if (teacher.getClass().getSimpleName().equals("Lecturer")) {
					String id = ((Lecturer)teacher).getId();
					String pass = ((Lecturer)teacher).getPassword();
					if (id.equals(nim) && pass.equals(password)) {
						
						return teacher;
					}
				}
			}
		}

		
		return null;
	}
	
	public void profilePage(Student s) {
		System.out.println("=========================================");
		System.out.println(" Name : " + s.getName());
		System.out.println(" NIM : " + s.getNim());
		System.out.println(" Age : " + s.getAge());
		System.out.println(" Gender : " + s.getGender());
		System.out.println("=========================================");
	}
	
	public Teacher convertStudentToTeacher(Student s, Vector<Teacher> t) {
		Teacher tmp = null;
		for (Teacher teacher : t) {
			if (teacher.getClass().getSimpleName().equals("LaboratoryAssistant")) {
				if (((LaboratoryAssistant)teacher).getNIM().equals(s.getNim())){
					tmp = teacher;
				}
			}
		}
		
		return tmp;
	}
	
	public Vector<Teacher> manageTeacher(Vector<Teacher> teachers, Vector<Student> students){
		int idx = 1;
		
		Vector<String> teacherIdList = new Vector<>();
		for (Teacher ts : teachers) {
			teacherIdList.add(ts.getId());
			
			System.out.println(" " 
			+ idx + ". Name : " 
			+ ts.getId() 
			+ " - " 
			+ ts.getName());
			
			idx++;
		}
		
		String ans = "";

		while(!ans.equals("Add") && !ans.equals("Update") && !ans.equals("Delete")) {
			System.out.print(" What do you want to do ? [ Add | Update | Delete ] : ");
			ans = scan.nextLine();
		}
		
		t.clr();
		switch(ans) {
		case "Add":
			teachers = addTeacher(teachers, students);
			break;
		case "Update":
			teachers = updateTeacher(teachers);
			break;
		case "Delete":
			teachers = deleteTeacher(teachers);
			break;
		}
		
		
		return teachers;
	}
	
	private Vector<Teacher> deleteTeacher(Vector<Teacher> teachers){
		int idx = 1;
		for (Teacher teacher : teachers) {
			System.out.println(" " + idx + ". " + teacher.getId() + " - " + teacher.getName());
			idx++;
		}
		
		int ch = -1;
		while(ch < 0 || ch > teachers.size()) {
			System.out.print(" Which of the teachers do you want to delete [ 1 - "+ teachers.size()+ " ] : ");
			ch = scan.nextInt();
			scan.nextLine();
		}
		
		
		
		teachers.remove(ch-1);
		
		return teachers;
	}
	
	private Vector<Teacher> updateTeacher(Vector<Teacher> teachers){
		int idx = 1;
		for (Teacher teacher : teachers) {
			System.out.println(" " + idx + ". " + teacher.getId() + " - " + teacher.getName());
			idx++;
		}
		
		int ch = -1;
		while(ch < 0 || ch > teachers.size()) {
			System.out.print(" Which of the teachers do you want to update [ 1 - "+ teachers.size()+ " ] : ");
			ch = scan.nextInt();
			scan.nextLine();
		}
		
		String name = "";	
		while(name.length() < 3 || name.length() > 25) {
			System.out.print(" Enter lecturer's name [ 3 - 25 char(s) ] : ");
			name = scan.nextLine();
		}
		
		teachers.get(ch-1).setName(name);

		return teachers;
	}
	
	private Vector<Teacher> addTeacher(Vector<Teacher> teachers, Vector<Student> students) {
		
		String ch = "";
		while(!ch.equals("Lecturer") && !ch.equals("Lab Assistant")) {
			System.out.print(" What is the lecturer type? [ Lecturer | Lab Assistant ] : ");
			ch = scan.nextLine();
		}
		
		switch(ch) {
			case "Lecturer":
				teachers.add(addNewLecturer(teachers));
				break;
			case "Lab Assistant":
				teachers.add(addNewLabAssistant(teachers, students)); 
				break;
		}
		
		return teachers;
	}
	
	public Lecturer addNewLecturer(Vector<Teacher> teachers) {
		String name = "";
		String id = "";
		
		while(name.length() < 3 || name.length() > 25) {
			System.out.print(" Enter lecturer's name [ 3 - 25 char(s) ] : ");
			name = scan.nextLine();
		}
		
		while (true) {
			System.out.print(" Enter Lecturer's Id [DXXX] : ");
			id = scan.nextLine();
			if (id.length() == 5 && id.charAt(0) == 'D') {
				if (Character.isDigit(id.charAt(1))
						&& Character.isDigit(id.charAt(2))
						&& Character.isDigit(id.charAt(3))
						&& Character.isDigit(id.charAt(4))
						) {
					break;
				}
			}
		}
		
		String password = "";
		while(password.length() < 3 || password.length() > 20) {
			System.out.print(" Enter the lecturer's Password [ 3 - 20 char(s)] : ");
			password = scan.nextLine();
		}

		
		Lecturer lec = new Lecturer(id, name, password);
		return lec;
	}
	
	public Vector<Student> synchronizeAssistantData(Vector<Teacher> teachers, Vector<Student> students){
			
		for (Student student : students) {
			
			for (Teacher ts : teachers) {
				if (ts.getClass().getSimpleName().equals("LaboratoryAssistant")) {
					if (((LaboratoryAssistant)ts).getNIM().equals(student.getNim())) {
						student.setTA(true);
					}
				}
					
			}
		
		}
		
		return students;
	}
	
	private LaboratoryAssistant addNewLabAssistant(Vector<Teacher> teachers, Vector<Student> students) {
		LaboratoryAssistant tmp = null;
		int idx = 1;
		Vector<String> teacherIdList = new Vector<>();
		Vector<String> studentNimList = new Vector<>();
		for (Teacher ts : teachers) {
			teacherIdList.add(ts.getId());
		}
		
		for (Student st : students) {
			if (!st.isTA()) {
				System.out.println(" " + idx + ". " + st.getNim() + " - " + st.getName());
				studentNimList.add(st.getNim());
				idx++;
			}
		}
		
		idx--;
		int ch = -1;
		while(ch < 0 || ch > idx) {
			System.out.print(" Which of the student do you want to add into the lab assistant? : ");
			try {
				ch = scan.nextInt();
				scan.nextLine();
			} catch (Exception e) {
				// TODO: handle exception
				ch = -2;
				scan.nextLine();
			}	
		}
		
		Student st = getStudentByNim(students, studentNimList.get(ch-1));
		String nim = st.getNim();
		String name = st.getName();
		
		String id = "";
		while(id.length() != 2) {
			System.out.print(" Input the Id [ 2 char(s) ] : ");
			id = scan.nextLine();
		}
		
		tmp = new LaboratoryAssistant(id, name, nim);
		
		return tmp;
	}
	
	public Vector<Student> manageUsers(Vector<Student> students){
		int idx = 1;
		
		Vector<String> studentNimList = new Vector<>();
		for (Student st : students) {
			studentNimList.add(st.getNim());
			
			System.out.println(" " 
			+ idx + ". Name : " 
			+ st.getNim() 
			+ " - " 
			+ st.getName());
			
			idx++;
		}
		
		String ans = "";

		while(!ans.equals("Add") && !ans.equals("Update") && !ans.equals("Delete")) {
			System.out.print(" What do you want to do ? [ Add | Update | Delete ] : ");
			ans = scan.nextLine();
		}
		
		t.clr();
		switch(ans) {
		case "Add":
			students.add(addStudent(students));
			break;
		case "Update":
			students = updateStudent(students);
			break;
		case "Delete":
			students = deleteStudents(students);
			break;
		}
		
		return students;
	}
	
	private Vector<Student> deleteStudents(Vector<Student> students){
int idx = 1;
		
		Vector<String> studentNimList = new Vector<>();
		for (Student st : students) {
			studentNimList.add(st.getNim());
			
			System.out.println(" " 
			+ idx + ". Name : " 
			+ st.getNim() 
			+ " - " 
			+ st.getName());
			
			idx++;
		}
		
		int ch = -1;
		while(ch < 0 || ch > students.size()) {
			System.out.print(" Which of the students do you want to delete [ 1 - "+ students.size()+ " ] : ");
			ch = scan.nextInt();
			scan.nextLine();
		}
		
		students.remove(ch-1);
		return students;
	}
	
	private Vector<Student> updateStudent(Vector<Student> students){
		int idx = 1;
		
		Vector<String> studentNimList = new Vector<>();
		for (Student st : students) {
			studentNimList.add(st.getNim());
			
			System.out.println(" " 
			+ idx + ". Name : " 
			+ st.getNim() 
			+ " - " 
			+ st.getName());
			
			idx++;
		}
		
		int ch = -1;
		while(ch < 0 || ch > students.size()) {
			System.out.print(" Which of the students do you want to update [ 1 - "+ students.size()+ " ] : ");
			ch = scan.nextInt();
			scan.nextLine();
		}
		
		String name = "";
		Integer age = 0;
		String gender = "";
		
		while(name.length() < 3 || name.length() > 25) { 
			System.out.print(" Enter student's Name [ 3 - 25 char(s) ] : ");
			name = scan.nextLine();
		}	
		
		while(!gender.equals("Female") && !gender.equals("Male")) {
			System.out.print(" Enter student's gender [ Male | Female ] : ");
			gender = scan.nextLine();
		}
		
		while(age <= 17) {
			System.out.print(" Enter student's age [ > 17 ] : ");
			try {
				age = scan.nextInt();
				scan.nextLine();
			} catch (Exception e) {
				// TODO: handle exception
				age = -1;
				scan.nextLine();
			}
		}
		
		String password = "";
		while(password.length() < 5 || password.length()> 20) {
			System.out.print(" Enter password [ 5 - 20 char(s) ] : ");
			password = scan.nextLine();
		}
		
		Student temp = getStudentByNim(students, studentNimList.get(ch-1));
		temp.setName(name);
		temp.setAge(age);
		temp.setPassword(password);
		temp.setGender(gender);
		
		students.set(ch-1, temp);
		
		return students;
	}
	
	Student addStudent(Vector<Student> students) {
		Student temp = null;
		String name = "";
		Integer age = 0;
		String gender = "";
		String nim = "";
		
		while(name.length() < 3 || name.length() > 25) { 
			System.out.print(" Enter student's Name [ 3 - 25 char(s) ] : ");
			name = scan.nextLine();
		}	
		
		while(!gender.equals("Female") && !gender.equals("Male")) {
			System.out.print(" Enter student's gender [ Male | Female ] : ");
			gender = scan.nextLine();
		}
		
		while(age <= 17) {
			System.out.print(" Enter student's age [ > 17 ] : ");
			try {
				age = scan.nextInt();
				scan.nextLine();
			} catch (Exception e) {
				// TODO: handle exception
				age = -1;
				scan.nextLine();
			}
		}
		
		String password = "";
		while(password.length() < 5 || password.length()> 20) {
			System.out.print(" Enter password [ 5 - 20 char(s) ] : ");
			password = scan.nextLine();
		}
		
		int min = 10000000;
		int max = 99999999;
		
		Integer random = min + (int)(Math.random() * ((max - min) + 1));
		
		Date date = new Date();
		String year = Integer.toString(date.getYear()-100+4);
		nim = year + random.toString();
		
		temp = new Student(name, nim, age, gender, password);
		
		return temp;
	}
	
	public Student getStudentByNim(Vector<Student> students, String NIM) {
		for (Student student : students) {
			if (student.getNim().equals(NIM)) {
				return student;
			}
		}
		
		return null;
	}
	
	public Teacher getTeacherById(Vector<Teacher> teachers, String id) {
		for (Teacher teacher : teachers) {
			if (teacher.getId().equals(id))
					return teacher;
		}
		
		return null;
	}


}
