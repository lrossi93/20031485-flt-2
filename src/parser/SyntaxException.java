package parser;

public class SyntaxException extends Exception{
	public SyntaxException(String msg) {
		super(msg);
//		System.out.println("\n\t[EXCEPTION]: "+msg+"\n");
	}
}
