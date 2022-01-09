package token;

public class Token {

	private int line;
	private TokenType type;
	private String value;
	
	public Token(TokenType type, int line, String value) {
		this.type = type;
		this.line = line;
		this.value = value;
	}

    // Getters per i campi
	public int getLine() {
		return this.line;
	}
	
	public TokenType getType() {
		return this.type;
	}
	
	public String getValue() {
		//significativo solo per i token che hanno un valore (int e float)
		/*if(this.value != null) 
			return this.value;
		return "none";*/
		return this.value;
	}

	public boolean equals(Object o) {
		if(this == o)
			return true;
		if(!(o instanceof Token))
			return false;
		
		Token that = (Token) o;
		if(this.value == null || that.value == null)
			return (this.line == that.line 
					&& this.type.equals(that.type));
		else		
			return (this.line == that.line 
					&& this.value.equals(that.value) 
					&& this.type.equals(that.type));
	}

	public String toString() {
		String string = "<" + type + ", ";
		if(this.getValue() != null)
			string += "val: "+value+", ";
		string += "line: " + line + ">";
		return string;
	}
}
