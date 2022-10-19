package Model;
import java.util.*;

public class Template {

	private Template() {}
	private static Template t = null;
	
	Scanner scan = new Scanner(System.in);
	
	public static Template getInstance() {
		// TODO Auto-generated constructor stub
		if (t == null)
			t = new Template();
		
		return t;
	}
	
	public void tmpl() {
		System.out.println(" Press Enter to Continue...");
		scan.nextLine();
	}
	
	public int scanMenu(int start, int end) {
		int ch = 0;
		while(ch  < start || ch > end) {
			try {
				System.out.print(" : ");
				ch = scan.nextInt();
				scan.nextLine();
			} catch (Exception e) {
				// TODO: handle exception
				ch = 0;
				scan.nextLine();
			}
		}
		
		return ch;
	}
	
	public void clr() {
		for(int i = 0; i<30; i++)
				System.out.println("");
	}
}
