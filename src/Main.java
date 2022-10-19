import java.util.*;

import Controller.ClassController;
import Controller.UserController;
import Model.Classroom;
import Model.LaboratoryAssistant;
import Model.Lecturer;
import Model.Student;
import Model.Teacher;
import Model.Template;

public class Main {

	Scanner scan = new Scanner(System.in);
	Vector<Student> users = new Vector<>();
	Vector<Teacher> teachers = new Vector<>();
	Vector<Classroom> classes = new Vector<>();
	
	UserController uc = UserController.getInstance();
	ClassController cc = ClassController.getInstance();
	Template t = Template.getInstance();

	public Main() {
		// TODO Auto-generated constructor stub
		addStudents();
		menu1();
	}
		
	private void addStudents() {
		Vector<Teacher> labs = new Vector<>();
		Vector<Teacher> lects = new Vector<>();
		Vector<Teacher> lects2 = new Vector<>();
		Vector<Student> regs = new Vector<>();
		
		Student s2 = new Student("Niko", "2440074211", 20, "Male", "nikotes");
		s2.setTA(true);
		Student s1 = new Student("Joni", "243003211", 20, "Female", "Jonites");
		Student s3 = new Student("Eka", "2430083211", 19 , "Male", "Ektl");
		Student s4 = new Student("Petrik", "2503222132", 16, "Female", "Petrikstar");
		Student s5 = new Student("Lili", "2432322123", 22, "Female", "Lilifood");
		
		users.add(s1);
		users.add(s2);
		users.add(s3);
		users.add(s4);
		users.add(s5);
		
		regs.add(s1);
		regs.add(s3);
		regs.add(s4);
		regs.add(s5);

		LaboratoryAssistant a1 = new LaboratoryAssistant("NK", "Niko", "2440074211");
		LaboratoryAssistant a2 = new LaboratoryAssistant("MT", "Maprika", "2430063213");

		Lecturer l1 = new Lecturer("D6667", "Juni", "juni123");
		Lecturer l2 = new Lecturer("D1234", "Surya", "surya123");
		
		teachers.add(a1);
		teachers.add(a2);
		teachers.add(l1);
		teachers.add(l2);
		
		labs.add(a1);
		labs.add(a2);
		
		lects.add(l1);
		lects2.add(l2);
		
		Classroom c1 = new Classroom("BN01", "OOP");
		c1.setStudents(regs);
		c1.setTeachers(labs);
		
		Classroom c2 = new Classroom("BH01", "HCI");
		c2.setStudents(regs);
		c2.setTeachers(labs);
		
		Classroom c3 = new Classroom("LH01", "HCI");
		c3.setStudents(users);
		c3.setTeachers(lects);
		
		Classroom c4 = new Classroom("LF01", "OOP");
		c4.setStudents(users);

		c4.setTeachers(lects2);
		
		classes.add(c1);
		classes.add(c2);
		classes.add(c3);
		classes.add(c4);
	}
	
	
	private void menu1() {
		boolean isRun1 = true;
		while(isRun1) {
			t.clr();
			System.out.println(" Ayam Sunib");
			System.out.println(" ============");
			System.out.println(" 1. Login");
			System.out.println(" 2. Exit");
			int ch = t.scanMenu(1, 2);
			switch(ch) {
				case 1:
					t.clr();
					login();
					break;
				case 2:
					System.exit(0);
					break;
			}
		}
	}
	
	
	private void studentPage(Student s) {
		boolean isRun2 = true;
		
		while(isRun2) {
			t.clr();
			System.out.println(" Welcome, " + s.getName());
			System.out.println(" ============");
			System.out.println(" 1. Profile");
			System.out.println(" 2. Class List");
			System.out.println(" 3. Exit");
			int ch = t.scanMenu(1, 3);
			switch(ch) {
				case 1:
					t.clr();
					uc.profilePage(s);
					t.tmpl();
					break;
				case 2:
					t.clr();
					cc.classList(s,classes);
					break;
				case 3:
					t.clr();
					isRun2 = false;
					break;
			}
		}
	}
	
	private void studentLecPage(Student s) {
		boolean isRun3 = true;
		Teacher labAssistant = uc.convertStudentToTeacher(s, teachers);
		while(isRun3) {
			t.clr();
			System.out.println(" Welcome, " + s.getName());
			System.out.println(" ============");
			System.out.println(" 1. Profile");
			System.out.println(" 2. Class List");
			System.out.println(" 3. Teaching List");
			System.out.println(" 4. Exit");
			int ch = t.scanMenu(1, 4);
			switch(ch) {
				case 1:
					t.clr();
					uc.profilePage(s);
					t.tmpl();
					break;
				case 2:
					t.clr();
					cc.classList(s, classes);
					break;
				case 3:
					t.clr();
					cc.teachingList(labAssistant, classes);
					break;
				case 4:
					t.clr();
					isRun3 = false;
					break;
			}
		}
	}
	
	private void lecturerPage(Teacher teacher) {
		boolean isRun4 = true;
		while(isRun4) {
			t.clr();
			System.out.println(" Welcome, " + teacher.getName());
			System.out.println(" ============");
			System.out.println(" 1. Teaching List");
			System.out.println(" 2. Exit");
			int ch = t.scanMenu(1, 2);
			switch(ch) {
				case 1:
					t.clr();
					cc.teachingList(teacher, classes);
					break;
				case 2:
					t.clr();
					isRun4 = false;
					break;
			}
		}
	}
	
	private void homePage(Object obj) {

		if (obj.getClass().getSimpleName().equals("Student")) {
			
			if (!((Student)obj).isTA()) {
				studentPage((Student)obj);
			}
			else {
				studentLecPage((Student)obj);
			}
		}
		else {
			lecturerPage((Teacher)obj);
		}

	}
	
	private void adminPage() {
		boolean isRun5 = true;
		while(isRun5) {
			t.clr();
			System.out.println(" Welcome, Admin");
			System.out.println(" ============");
			System.out.println(" 1. Manage Class");
			System.out.println(" 2. Manage Teacher");
			System.out.println(" 3. Manage Student");
			System.out.println(" 4. Exit");
			int ch = t.scanMenu(1, 4);
			switch(ch) {
				case 1:
					t.clr();
					classes = cc.manageClass(classes,teachers,users);
					break;
				case 2:
					t.clr();
					teachers = uc.manageTeacher(teachers, users);
					users = uc.synchronizeAssistantData(teachers, users);
					break;
				case 3:
					t.clr();
					users = uc.manageUsers(users);
					break;
				case 4:
					isRun5 = false;
					break;
			}
		}
	}
	
	
	private void login() {
		String nim, password;
		System.out.print(" Input your NIM : ");
		nim = scan.nextLine();
		System.out.print(" Input your Password : ");
		password = scan.nextLine();
		
		if (nim.equals("admin") && password.equals("admin")) {
			adminPage();
			return;
		}
		
		Object obj = uc.checkUser(teachers,users,nim, password);
		if (obj == null) {
			System.out.println(" User not Found...");
			scan.nextLine();
		}
		else {
			homePage(obj);
		}
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Main();
	}

}
