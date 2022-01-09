package scanner;

public class LexicalException extends Exception {
	public LexicalException(String msg) {
		//super(msg);
		System.err.println(msg);
	}
}
