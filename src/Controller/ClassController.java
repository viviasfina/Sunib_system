package Controller;
import java.util.Scanner;

import Model.Classroom;
import Model.Student;
import Model.Teacher;
import Model.Template;

import java.util.*;

public class ClassController {

	private static ClassController cc = null;
	Scanner scan = new Scanner(System.in);
	Template t = Template.getInstance();
	
	UserController uc = UserController.getInstance();
	
	private ClassController() {}
	
	public static ClassController getInstance() {
		// TODO Auto-generated constructor stub
		
		if (cc == null)
			cc = new ClassController();
		
		
		return cc;
	}
	
	public void classList(Student s, Vector<Classroom> classes) {
		
		boolean flag = false;
		int idx = 1;
		Vector<String> listClassCode = new Vector<>();
		
		for (Classroom classroom : classes) {
			for (Student stud : classroom.getStudents()) {
				if (stud.getName().equals(s.getName())) {
					System.out.println(" ===================================");
					System.out.println(" [" + idx + "]");
					listClassCode.add(classroom.getClassCode());
					System.out.println(" Class Code : " + classroom.getClassCode());
					System.out.println(" Class : " + classroom.getClassName());
					flag = true;
					idx++;
				}
			}
		}
		
		if (!flag) {
			System.out.println(" You haven't been asigned to any class yet");
			t.tmpl();
		}
		else {
			System.out.println(" ===================================");
			idx--;
			System.out.print(" Which of the class that you want to check [ 1 - " + idx + " ]");
			int ch = t.scanMenu(1, idx);
			checkClassDetail(listClassCode.get(ch-1), classes);
		}
	}


	
	private void checkClassDetail(String classCode, Vector<Classroom> classes) {
		for (Classroom classroom : classes) {
			if (classroom.getClassCode().equals(classCode)) {
				// show classroom detail
				t.clr();
				System.out.println(" Class : " + classCode + " - " + classroom.getClassName());
				System.out.println("");
				System.out.println(" Teachers");
				System.out.println(" ================");
				for (Teacher t : classroom.getTeachers()) {
					System.out.println(" ID : " + t.getId());
					System.out.println(" Name : " + t.getName());
				}
				System.out.println("");
				System.out.println(" ================");
				System.out.println(" People");
				System.out.println(" =================");
				for (Student s : classroom.getStudents()) {
					System.out.println(" NIM : " + s.getNim());
					System.out.println(" Name : " + s.getName());
					System.out.println(" ======================================");
				}
				t.tmpl();
			}
		}
	}
	
	public void teachingList(Teacher teach, Vector<Classroom> classes) {
		
		boolean flag = false;
		int idx = 1;
		Vector<String> listClassCode = new Vector<>();
		
		for (Classroom classroom : classes) {
			for (Teacher tmp : classroom.getTeachers()) {
				if (tmp.getId().equals(teach.getId())) {
					System.out.println(" ===================================");
					System.out.println(" [" + idx + "]");
					listClassCode.add(classroom.getClassCode());
					System.out.println(" Class Code : " + classroom.getClassCode());
					System.out.println(" Class : " + classroom.getClassName());
					flag = true;
					idx++;
				}
			}
		}
		
		if (!flag) {
			System.out.println(" You haven't been asigned to teach any class yet");
			t.tmpl();
		}
		else {
			System.out.println(" ===================================");
			idx--;
			System.out.print(" Which of the class that you want to check [ 1 - " + idx + " ]");
			int ch = t.scanMenu(1, idx);
			checkClassDetail(listClassCode.get(ch-1), classes);
		}
	}
	
	
	public Vector<Classroom> manageClass(Vector<Classroom> classes,
			Vector<Teacher> teachers,
			Vector<Student> students
			) {
		int idx = 1;
		
		Vector<String> classCodeList = new Vector<>();
		for (Classroom classroom : classes) {
			classCodeList.add(classroom.getClassCode());
			System.out.println(" " 
			+ idx + ". Class : " 
			+ classroom.getClassCode() 
			+ " - " 
			+ classroom.getClassName());
			idx++;
		}
		
		String ans = "";

		while(!ans.equals("Add") && !ans.equals("Update") && !ans.equals("Delete")) {
			System.out.print(" What do you want to do ? [ Add | Update | Delete ] : ");
			ans = scan.nextLine();
		}
		
		switch(ans) {
			case "Add":
				classes.add(addClass(teachers,students,classCodeList));
				break;
			case "Update":
				classes = updateClass(teachers,students,classes);
				break;
			case "Delete":
				classes = deleteClass(classes);
				break;
		}
		
		return classes;
	};
	
	private Vector<Classroom> deleteClass(Vector<Classroom> classes){
		int id = 1;
		for (Classroom c : classes) {
			System.out.println(" " + id + ". Class : " + c.getClassCode() + " " + c.getClassName());
			id++;
		}
		id--;
		System.out.print(" Choose a class to be deleted [ 1 - "+ id+ " ]");
		int ch = t.scanMenu(1, id);
		
		classes.remove(ch-1);
		
		return classes;
	}
	
	private Vector<Classroom> updateClass(Vector<Teacher> teachers, Vector<Student> students, Vector<Classroom> classes){
		
		int id = 1;
		for (Classroom c : classes) {
			System.out.println(" " + id + ". Class : " + c.getClassCode() + " " + c.getClassName());
			id++;
		}
		id--;
		System.out.print(" Choose a class to be updated [ 1 - "+ id+ " ]");
		int ch = t.scanMenu(1, id);
		
		String ch1 = "Add", ch2 = "add";
		
		while(true) {
			System.out.print(" What will you do with the students ? [ Add | Delete | Skip ] : ");
			ch1 = scan.nextLine();
			
			if (ch1.equals("Add") || ch1.equals("Delete") || ch1.equals("Skip")) {
				break;
			}
		}

		
		switch(ch1) {
			case "Add":
				
				while(true) {
					int num = 1;
					
					System.out.println(" Available Student in the Class");
					System.out.println(" ======================================");
					for (Student st : classes.get(ch-1).getStudents()) {
						System.out.println(" " + num + " " + st.getNim() + " " + st.getName());
						num++;
					}
					num--;
					System.out.println("");
					System.out.println(" Student available to be added");
					System.out.println(" ======================================");
					num = 1;
					Vector<String> nimList = new Vector<>();
					for (Student s : students) {
						if (!classes.get(ch-1).getStudents().contains(s)) {
							System.out.println(" " + num + " " + s.getNim() + " " + s.getName());
							nimList.add(s.getNim());
							num++;
						}
					}
					
					if (nimList.isEmpty()) {
						System.out.println(" No data to be added....");
						t.tmpl();
						break;
					}
					num--;
					
					int choice = -1;
					while(choice < 0 || choice > num) {
						System.out.print(" Enter the student's number to be added [ '0' to exit ] [ 1 - "+ num+ " ] : ");
						choice = scan.nextInt();
						scan.nextLine();
					}
					
					if (choice == 0) {
						break;
					}
					
					classes.get(ch-1).getStudents().add(uc.getStudentByNim(students, nimList.get(choice-1)));
				}
				
				break;
			case "Delete":

				while(true) {
					int num = 1;
					for (Student st : classes.get(ch-1).getStudents()) {
						System.out.println(" " + num + " " + st.getNim() + " " + st.getName());
						num++;
					}
					num--;
					
					int choice = -1;
					while(choice < 0 || choice > num) {
						System.out.print(" Enter the student's number to be deleted [ '0' to exit ] [ 1 - "+ num+ " ] : ");
						choice = scan.nextInt();
						scan.nextLine();
					}
					
					if (choice == 0) {
						break;
					}
					
					classes.get(ch-1).getStudents().remove(choice-1);
				}
				
				break;
		}
		
		while(true) {
			System.out.print(" What will you do with the teachers ? [ Add | Delete | Exit ] : ");
			ch2 = scan.nextLine();
			
			if (!ch2.equals("Add") || !ch2.equals("Delete") || ch2.equals("Exit")) {
				break;
			}
		}
		
		switch(ch2) {
			case "Add":
				
				while(true) {
					int num = 1;
					
					System.out.println(" Available Teacher in the Class");
					System.out.println(" ======================================");
					for (Teacher te : classes.get(ch-1).getTeachers()) {
						System.out.println(" " + num + " " + te.getId() + " " + te.getName());
						num++;
					}
					num--;
					System.out.println("");
					System.out.println(" Teacher available to be added");
					System.out.println(" ======================================");
					num = 1;
					Vector<String> idList = new Vector<>();
					for (Teacher te : teachers) {
						if (!classes.get(ch-1).getTeachers().contains(te)) {
							System.out.println(" " + num + " " +te.getId() + " " + te.getName());
							idList.add(te.getId());
							num++;
						}
					}
					
					if (idList.isEmpty()) {
						System.out.println(" No data to be added....");
						t.tmpl();
						break;
					}
					num--;
					
					int choice = -1;
					while(choice < 0 || choice > num) {
						System.out.print(" Enter the teacher's number to be added [ '0' to exit ] [ 1 - "+ num+ " ] : ");
						choice = scan.nextInt();
						scan.nextLine();
					}
					
					if (choice == 0) {
						break;
					}
					
					classes.get(ch-1).getTeachers().add(uc.getTeacherById(teachers, idList.get(choice-1)));
				}
				break;
			case "Delete":
				
				while(true) {
					int num = 1;
					for (Teacher st : classes.get(ch-1).getTeachers()) {
						System.out.println(" " + num + " " + st.getId() + " " + st.getName());
						num++;
					}
					num--;
					
					int choice = -1;
					while(choice < 0 || choice > num) {
						System.out.print(" Enter the teacher's number to be deleted [ '0' to exit ] [ 1 - "+ num+ " ] : ");
						choice = scan.nextInt();
						scan.nextLine();
					}
					
					if (choice == 0) {
						break;
					}
					
					classes.get(ch-1).getTeachers().remove(choice-1);
				}
				break;
		}
		
		return classes;
	}
	
	private Classroom addClass(Vector<Teacher> teachers, Vector<Student> students, Vector<String> codeList) {
		String code = "", name = "";
		int flag = 0;
		
		boolean validCode = false;
		while(!validCode) {
			System.out.print(" Enter the class Code : ");
			code = scan.nextLine();
			
			if (code.length() == 4) {
				if (!codeList.contains(code) 
						&& Character.isAlphabetic(code.charAt(0))
						&& Character.isAlphabetic(code.charAt(1))		
						&&	Character.isDigit(code.charAt(2))
						&& Character.isDigit(code.charAt(3))) {
					validCode = true;
				}
			}
		}
		
		System.out.print(" Enter the Class Name : ");
		name = scan.nextLine();
		
		Classroom tmp = new Classroom(code, name);
		
		
		Vector<String> teacherId = new Vector<>();
		int id = 1;
		
		for (Teacher teach : teachers) {
			teacherId.add(teach.getId());
			System.out.println(" " + id + " " + teach.getId() + " " + teach.getName());
			id++;
		}
		id--;
		Vector<Teacher> teacherTempVector = new Vector<>();
		
		while(true) {
			int ch = -1;
			while(ch < 0 || ch > id) {
				System.out.print(" Enter the teacher's number [ '0' to exit ] [ 1 - "+ id+ " ] : ");
				ch = scan.nextInt();
				scan.nextLine();
			}
			
			if (ch == 0 ) {
				break;
			}
			teacherTempVector.add(teachers.get(ch-1));
			flag++;
			
//			System.out.println(ch == 0 && flag != 0);
		}
		
		tmp.setTeachers(teacherTempVector);
		
		id = 1;
		
		Vector<String> studentId = new Vector<>();
		
		for (Student s : students) {
			studentId.add(s.getNim());
			System.out.println(" " + id + " " + s.getNim() + " " + s.getName());
			id++;
		}
		id--;
		
		Vector<Student> studentTempVector = new Vector<>();
		
		while(true) {
			int ch = -1;
			while(ch < 0 || ch > id) {
				System.out.print(" Enter the teacher's number [ '0' to exit ] [ 1 - "+ id+ " ] : ");
				ch = scan.nextInt();
				scan.nextLine();
			}
			
			if (ch == 0 && flag != 0) {
				break;
			}
			flag++;
			
			studentTempVector.add(students.get(ch-1));
		}
		tmp.setStudents(studentTempVector);
		
		return tmp; 
	}

}
