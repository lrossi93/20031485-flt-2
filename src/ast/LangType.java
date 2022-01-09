package ast;

public enum LangType {
	INT("int"),
	FLOAT("float");
	
	public final String label;
	
	private LangType(String label) {
		this.label = label;
	}
	
	public String toString() {
		return this.label;
	}
}
