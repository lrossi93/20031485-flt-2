package symbolTable;

import ast.LangType;

public class Attributes {
	LangType type;
	private char register;

	public Attributes(LangType type) {
		this.type = type;
		this.register = '\0';
	}
	
	public LangType getType() {
		return type;
	}

	public void setType(LangType type) {
		this.type = type;
	}
	
	public char getRegister() {
		return register;
	}

	public void setRegister(char register) {
		this.register = register;
	}

	public String toString() {
		return type+"";
	}
}
