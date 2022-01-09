package scanner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.util.Arrays;
//import java.util.HashMap;
import java.util.List;

import token.*;

public class Scanner {
	private String fileName;
	final char EOF = (char) -1; // int 65535
	private int line;
	//da utilizzare localmente alla peekToken
	private int currentLine;
	private PushbackReader buffer;
	//private String log;
	//private Token token;
	private static final int MIN_DIGITS = 1;
	private static final int MAX_DIGITS = 5;

	private List<Character> skipChars = Arrays.asList(' ', '\n', '\t', '\r', EOF);
	private List<Character> letters = Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z');
	private List<Character> numbers = Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
	private List<Character> operators = Arrays.asList('+', '-', '*', '/', '=', ';');
	// HashMap caratteri TokenType per associazione fra '+', '-', '*', '/', '=', ';'
	// e TokenType, mi serve in nextToken();
//	private HashMap<Character, TokenType> hashChar;
//	
//	// HashMap stringhe TokenType per associazione fra parole chiave "print",
//	// "float", "int" e TokenType
//	private HashMap<String, TokenType> hashTokens;

	public Scanner(String fileName) throws FileNotFoundException {
		this.setFileName(fileName);
		this.buffer = new PushbackReader(new FileReader(fileName), 100);//aggiunta dimensione del buffer
		line = 1;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Token peekToken() throws IOException, LexicalException{
		//copio il buffer, così posso trusciarci dentro
		//PushbackReader bufferClone = buffer;//new PushbackReader(new FileReader(fileName));
		Character c = null;
		String tokenString = "";
		//riga di partenza, cioè l'ultima riga a cui ero rimasto nello scan
		currentLine = line;
		
		//se il carattere è legale
		if(numbers.contains(peekChar()) || letters.contains(peekChar()) || operators.contains(peekChar()) || skipChars.contains(peekChar())) {
			
			while(skipChars.contains(peekChar())) {
				c = readChar();
				if(c == '\n') currentLine++;
				if(c == EOF) {
					buffer.unread(c);
					return new Token(TokenType.EOF, currentLine, null);
				}
			}
			c = readChar();
			tokenString += c;
			
			//se c è un operatore
			if(operators.contains(c)) {
				//rimetto sul buffer il carattere che ho letto
				buffer.unread(c);
				//a seconda dell'operatore letto, restituisco il token appropriato
				switch(c) {
					case '+':
						return new Token(TokenType.PLUS, currentLine, tokenString);
					case '-':
						return new Token(TokenType.MINUS, currentLine, tokenString);
					case '*':
						return new Token(TokenType.TIMES, currentLine, tokenString);
					case '/':
						return new Token(TokenType.DIV, currentLine, tokenString);
					case '=':
						return new Token(TokenType.ASSIGN, currentLine, tokenString);
					case ';':
						return new Token(TokenType.SEMI, currentLine, tokenString);
				}
			}
			
			//se c è una lettera mi aspetto altre lettere
			else if(letters.contains(c)) {
				while(letters.contains(peekChar())) {
					c = readChar();
					tokenString += c;
				}
				//ho letto tutte le lettere finchè non è capitato un altro simbolo
				//restituisco un token a seconda di cosa ho letto
				buffer.unread(tokenString.toCharArray());
				switch(tokenString) {
					case "int":	
						return new Token(TokenType.INTDEC, currentLine, tokenString);
					case "float":
						return new Token(TokenType.FLOATDEC, currentLine, tokenString);
					case "print":	
						return new Token(TokenType.PRINT, currentLine, tokenString);
					default:
						return new Token(TokenType.ID, currentLine, tokenString);
				}
			}
			
			//se ho letto un numero voglio leggere un int o un float
			else if(numbers.contains(c)) {
				//flag per segnalare una lettura di '.' già avvenuta
				boolean dotSeen = false;
				int digits = 0;
				//finchè leggo numeri o '.'
				while(numbers.contains(peekChar()) || peekChar() == '.') {
					//se vedo un '.'
					if(peekChar() == '.') {
						if(dotSeen)
							throw new LexicalException("LexicalException@line:"+currentLine+"\n'.' read twice in float value!");
						//se non ho ancora incontrato '.' e lo incontro ora
						else {
							c = readChar();
							tokenString += c;
							dotSeen = true;
						}
					}
					//se non vedo un '.' avrò un numero
					else {
						c = readChar();
						tokenString += c;
						if(dotSeen)
							digits++;
					}
				}
				//copio il vecchio buffer e ritorno un token
				buffer.unread(tokenString.toCharArray());
				if(dotSeen) {
					if(digits <= MAX_DIGITS && digits >= MIN_DIGITS)
						return new Token(TokenType.FLOATVAL, currentLine, tokenString);
					else
						throw new LexicalException("LexicalException@line:"+currentLine+"\n"+digits+" digits after '.' (must be between 1 and 5)!");
				}
				else
					return new Token(TokenType.INTVAL, currentLine, tokenString);
			}
			else
				throw new LexicalException("LexicalException@line:"+currentLine+"\nIllicit character!");
		}
		else
			throw new LexicalException("LexicalException@line:"+currentLine+"\nIllicit character!");
		return null;
	}
	
	//legge caratteri dallo stream, restituisce un token e lo consuma
	//consuma i caratteri dell'input sufficienti ad ottenere il prossimo token
	//chiamando due volte nextToken otterrò due token diversi
	public Token nextToken() throws IOException, LexicalException {
		switch(peekToken().getType()) {
			case FLOATDEC:
			case INTDEC:
			case ID:
			case PRINT:
				return scanId();
			case ASSIGN:
			case PLUS:
			case MINUS:
			case TIMES:
			case DIV:
			case SEMI:
				return scanOp();
			case INTVAL:
			case FLOATVAL:
				return scanNumber();
			case EOF:
				return scanEOF();
		}
		return null;
	}
	
	private Token scanEOF() throws IOException, LexicalException {
		readSkipChars();
		if(readChar() == EOF) {
			return new Token(TokenType.EOF, line, "EOF");
		}
		throw new LexicalException("LexicalException@line:"+line+"\nExpected EOF!");
	}
	
	private Token scanOp() throws IOException, LexicalException {
		//consumo gli eventuali skipChars ed incremento line
		readSkipChars();
		switch(readChar()) {
			case '+':
				return new Token(TokenType.PLUS, currentLine, "+");
			case '-':
				return new Token(TokenType.MINUS, currentLine, "-");
			case '*':
				return new Token(TokenType.TIMES, currentLine, "*");
			case '/':
				return new Token(TokenType.DIV, currentLine, "/");
			case '=':
				return new Token(TokenType.ASSIGN, currentLine, "=");
			case ';':
				return new Token(TokenType.SEMI, currentLine, ";"); 
		}
		throw new LexicalException("LexicalException@line:"+line+"\nExpected operator!");
	}
	
	private Token scanNumber() throws IOException, LexicalException{
		String string = "";
		readSkipChars();
		while(numbers.contains(peekChar())) {
			string += readChar();
		}
		if(peekChar() != '.')
			return new Token(TokenType.INTVAL, line, string);
		//if a point is scanned, initiate FLOAT scan
		string += readChar();
		int nC = 0;
		while(numbers.contains(peekChar())) {
			nC++;
			string += readChar();
		}
		//if I read too many/few characters after the point, 
		//a lexical exception is thrown
		if((nC <= MAX_DIGITS) && (nC >= MIN_DIGITS))
			return new Token(TokenType.FLOATVAL, line, string);
		else
			throw new LexicalException("LexicalException@line:"+line+"\n"+nC+" digits (must be between 1 and 5)!");
	}
	
	public Token scanId() throws IOException, LexicalException{
		String string = "";
		readSkipChars();
		//read at least one character
		while(letters.contains(peekChar())){
			string += readChar();
		}
		if(string.equals("int")) {
			return new Token(TokenType.INTDEC, line, string);
		}
		else if(string.equals("float")) {
			return new Token(TokenType.FLOATDEC, line, string);
		}
		else if(string.equals("print")) {
			return new Token(TokenType.PRINT, line, string);
		}
		else {
			return new Token(TokenType.ID, line, string);
		}
	}
	
	private char readChar() throws IOException {
		char c = ((char) this.buffer.read());
		if(c == '\n') 
			line++;
		return c;
	}

	private char peekChar() throws IOException {
		char c = (char) buffer.read();
		buffer.unread(c);
		return c;
	}
	
	public void printBuffer() {
		System.out.println(buffer);
	}
	
	public void print(String s) {
		System.out.println(s);
	}
	
	public void readSkipChars() throws IOException {
		while(skipChars.contains(peekChar()) && peekChar() != EOF) {
			readChar();
		}
	}
	
	public int getLine() {
		return this.line;
	}
}
