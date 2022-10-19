package Model;
public class LaboratoryAssistant extends Teacher{
	
	private String NIM;
	
	public LaboratoryAssistant(String id, String name, String NIM) {
		super(id, name);
		this.NIM = NIM;
		// TODO Auto-generated constructor stub
	}

	public String getNIM() {
		return NIM;
	}

	public void setNIM(String nIM) {
		NIM = nIM;
	}
	
	
}
