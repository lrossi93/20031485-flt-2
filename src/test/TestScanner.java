package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

import scanner.LexicalException;
import scanner.Scanner;
import token.Token;
import token.TokenType;

public class TestScanner {
	
	@Test
	public void testCorrectIntval(){
		String path ="src/test/data/testIntVal.txt";
		try {
			Scanner scanner = new Scanner(path);
			Token token = new Token(TokenType.INTVAL, 1, "12345");
			assertTrue(scanner.peekToken().equals(token));
			assertTrue(scanner.nextToken().equals(token));
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LexicalException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testWrongIntval(){
		String path ="src/test/data/testIntValWrong.txt";
		try {
			Scanner scanner = new Scanner(path);
			assertThrows(LexicalException.class, () -> scanner.peekToken());
		} catch (FileNotFoundException e) {
//			e.printStackTrace();	
		}
	}

		@Test
		public void testCorrectFloatdec(){
			String path ="src/test/data/testFloatDec.txt";
			try {
				Scanner scanner = new Scanner(path);
				Token token = new Token(TokenType.FLOATDEC, 1, "float");
				assertTrue(scanner.peekToken().equals(token));
				assertTrue(scanner.nextToken().equals(token));
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (LexicalException e) {
				e.printStackTrace();
			}
		}
		
		@Test
		public void testWrongFloatdec(){
			String path ="src/test/data/testFloatDecWrong.txt";
			try {
				Scanner scanner = new Scanner(path);
				assertNotEquals(scanner.peekToken(), new Token(TokenType.FLOATDEC, 1, "float"));
			} catch (FileNotFoundException e) {
//				e.printStackTrace();
			} catch (IOException e) {
//				e.printStackTrace();
			} catch (LexicalException e) {
//				e.printStackTrace();
			}
		}
	
		@Test
		public void testCorrectIntdec(){
			String path ="src/test/data/testIntDec.txt";
			try {
				Scanner scanner = new Scanner(path);
				Token token = new Token(TokenType.INTDEC, 1, "int");
				assertTrue(scanner.peekToken().equals(token));
				assertTrue(scanner.nextToken().equals(token));
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (LexicalException e) {
				e.printStackTrace();
			}
		}
		
		@Test
		public void testWrongIntDec(){
			String path ="src/test/data/testIntDecWrong.txt";
			try {
				Scanner scanner = new Scanner(path);
				assertNotEquals(scanner.peekToken(), new Token(TokenType.INTDEC, 1, "int"));
			} catch (FileNotFoundException e) {
//				e.printStackTrace();
			} catch (IOException e) {
//				e.printStackTrace();
			} catch (LexicalException e) {
//				e.printStackTrace();
			}
		}	
															
			
		
		@Test
		public void testCorrectPrint(){
			String path ="src/test/data/testPrint.txt";
			try {
				Scanner scanner = new Scanner(path);
				Token token = new Token(TokenType.PRINT, 1, "print");
				assertTrue(scanner.peekToken().equals(token));
				assertTrue(scanner.nextToken().equals(token));
				
			} catch (FileNotFoundException e) {
//				e.printStackTrace();
			} catch (IOException e) {
//				e.printStackTrace();
			} catch (LexicalException e) {
//				e.printStackTrace();
			}
		}
//		
		@Test
		public void testWrongPrint(){
			String path ="src/test/data/testPrintWrong.txt";
			try {
				Scanner scanner = new Scanner(path);
				assertNotEquals(scanner.peekToken(), new Token(TokenType.PRINT, 1, "print"));
			} catch (FileNotFoundException e) {
//				e.printStackTrace();
			} catch (IOException e) {
//				e.printStackTrace();
			} catch (LexicalException e) {
//				e.printStackTrace();
			}
		}

		@Test
		public void testCorrectId(){
			String path ="src/test/data/testId.txt";
			try {
				Scanner scanner = new Scanner(path);
				Token token = new Token(TokenType.ID, 1, "identificatore");
				assertTrue(scanner.peekToken().equals(token));
				assertTrue(scanner.nextToken().equals(token));
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (LexicalException e) {
				e.printStackTrace();
			}
		}
		
		@Test
		public void testWrongId(){
			String path ="src/test/data/testIdWrong.txt";
			try {
				Scanner scanner = new Scanner(path);
				assertNotEquals(scanner.peekToken().getType(), TokenType.ID);
			} catch (FileNotFoundException e) {
//				e.printStackTrace();
			} catch (IOException e) {
//				e.printStackTrace();
			} catch (LexicalException e) {
//				e.printStackTrace();
			}
		}

		@Test
		public void testCorrectFloatval(){
			String path ="src/test/data/testFloatVal.txt";
			try {
				Scanner scanner = new Scanner(path);
				Token token = new Token(TokenType.FLOATVAL, 1, "123.456");
				assertTrue(scanner.peekToken().equals(token));
				assertTrue(scanner.nextToken().equals(token));
				
			} catch (FileNotFoundException e) {
//				e.printStackTrace();
			} catch (IOException e) {
//				e.printStackTrace();
			} catch (LexicalException e) {
//				e.printStackTrace();
			}
		}
		
		@Test
		public void testWrongFloatval(){
			String path ="src/test/data/testFloatValWrong.txt";
			try {
				Scanner scanner = new Scanner(path);
				assertThrows(LexicalException.class, () -> scanner.peekToken());
			} catch (FileNotFoundException e) {
//				e.printStackTrace();
			}
		}
		
		@Test
		public void testCorrectAssign(){
			String path ="src/test/data/testAssign.txt";
			try {
				Scanner scanner = new Scanner(path);
				Token token = new Token(TokenType.ASSIGN, 1, "=");
				assertTrue(scanner.peekToken().equals(token));
				assertTrue(scanner.nextToken().equals(token));
				
			} catch (FileNotFoundException e) {
//				e.printStackTrace();
			} catch (IOException e) {
//				e.printStackTrace();
			} catch (LexicalException e) {
//				e.printStackTrace();
			}
		}
		
		@Test
		public void testWrongAssign(){
			String path ="src/test/data/testAssignWrong.txt";
			try {
				Scanner scanner = new Scanner(path);
				assertNotEquals(scanner.peekToken().getType(), TokenType.ASSIGN);
			} catch (FileNotFoundException e) {
//				e.printStackTrace();
			} catch (IOException e) {
//				e.printStackTrace();
			} catch (LexicalException e) {
//				e.printStackTrace();
			}
		}
		
		@Test
		public void testCorrectPlus(){
			String path ="src/test/data/testPlus.txt";
			try {
				Scanner scanner = new Scanner(path);
				Token token = new Token(TokenType.PLUS, 1, "+");
				assertTrue(scanner.peekToken().equals(token));
				assertTrue(scanner.nextToken().equals(token));
				
			} catch (FileNotFoundException e) {
//				e.printStackTrace();
			} catch (IOException e) {
//				e.printStackTrace();
			} catch (LexicalException e) {
//				e.printStackTrace();
			}
		}
		
		@Test
		public void testWrongPlus(){
			String path ="src/test/data/testPlusWrong.txt";
			try {
				Scanner scanner = new Scanner(path);
				assertNotEquals(scanner.peekToken().getType(), TokenType.PLUS);
			} catch (FileNotFoundException e) {
//				e.printStackTrace();
			} catch (IOException e) {
//				e.printStackTrace();
			} catch (LexicalException e) {
//				e.printStackTrace();
			}
		}
		
		@Test
		public void testCorrectMinus(){
			String path ="src/test/data/testMinus.txt";
			try {
				Scanner scanner = new Scanner(path);
				Token token = new Token(TokenType.MINUS, 1, "-");
				assertTrue(scanner.peekToken().equals(token));
				assertTrue(scanner.nextToken().equals(token));
				
			} catch (FileNotFoundException e) {
//				e.printStackTrace();
			} catch (IOException e) {
//				e.printStackTrace();
			} catch (LexicalException e) {
//				e.printStackTrace();
			}
		}
		
		@Test
		public void testWrongMinus(){
			String path ="src/test/data/testMinusWrong.txt";
			try {
				Scanner scanner = new Scanner(path);
				assertNotEquals(scanner.peekToken().getType(), TokenType.MINUS);
			} catch (FileNotFoundException e) {
//				e.printStackTrace();
			} catch (IOException e) {
//				e.printStackTrace();
			} catch (LexicalException e) {
//				e.printStackTrace();
			}
		}
	
		@Test
		public void testCorrectTimes(){
			String path ="src/test/data/testTimes.txt";
			try {
				Scanner scanner = new Scanner(path);
				Token token = new Token(TokenType.TIMES, 1, "*");
				assertTrue(scanner.peekToken().equals(token));
				assertTrue(scanner.nextToken().equals(token));
				
			} catch (FileNotFoundException e) {
//				e.printStackTrace();
			} catch (IOException e) {
//				e.printStackTrace();
			} catch (LexicalException e) {
//				e.printStackTrace();
			}
		}
		
		@Test
		public void testWrongTimes(){
			String path ="src/test/data/testTimesWrong.txt";
			try {
				Scanner scanner = new Scanner(path);
				assertNotEquals(scanner.peekToken().getType(), TokenType.TIMES);
			} catch (FileNotFoundException e) {
//				e.printStackTrace();
			} catch (IOException e) {
//				e.printStackTrace();
			} catch (LexicalException e) {
//				e.printStackTrace();
			}
		}

		@Test
		public void testCorrectDiv(){
			String path ="src/test/data/testDivide.txt";
			try {
				Scanner scanner = new Scanner(path);
				Token token = new Token(TokenType.DIV, 1, "/");
				assertTrue(scanner.peekToken().equals(token));
				assertTrue(scanner.nextToken().equals(token));
				
			} catch (FileNotFoundException e) {
		//		e.printStackTrace();
			} catch (IOException e) {
		//		e.printStackTrace();
			} catch (LexicalException e) {
		//		e.printStackTrace();
			}
		}
//		
		@Test
		public void testWrongDiv(){
			String path ="src/test/data/dov_wrong.txt";
			try {
				Scanner scanner = new Scanner(path);
				assertNotEquals(scanner.peekToken().getType(), TokenType.DIV);
			} catch (FileNotFoundException e) {
//				e.printStackTrace();
			} catch (IOException e) {
//				e.printStackTrace();
			} catch (LexicalException e) {
//				e.printStackTrace();
			}
		}

		@Test
		public void testCorrectSemi(){
			String path ="src/test/data/testSemi.txt";
			try {
				Scanner scanner = new Scanner(path);
				Token token = new Token(TokenType.SEMI, 1, ";");
				assertTrue(scanner.peekToken().equals(token));
				assertTrue(scanner.nextToken().equals(token));
				
			} catch (FileNotFoundException e) {
		//		e.printStackTrace();
			} catch (IOException e) {
		//		e.printStackTrace();
			} catch (LexicalException e) {
		//		e.printStackTrace();
			}
		}
		
		@Test
		public void testWrongSemi(){
			String path ="src/test/data/testWrongSemi.txt";
			try {
				Scanner scanner = new Scanner(path);
				assertNotEquals(scanner.peekToken().getType(), TokenType.SEMI);
			} catch (FileNotFoundException e) {
//				e.printStackTrace();
			} catch (IOException e) {
//				e.printStackTrace();
			} catch (LexicalException e) {
//				e.printStackTrace();
			}
		}

		@Test
		public void testCorrectEof(){
			String path ="src/test/data/testEOF.txt";
			try {
				Scanner scanner = new Scanner(path);
				Token token = new Token(TokenType.EOF, 1, null);
				assertTrue(scanner.peekToken().equals(token));
				assertTrue(scanner.nextToken().equals(token));
				
			} catch (FileNotFoundException e) {
		//		e.printStackTrace();
			} catch (IOException e) {
		//		e.printStackTrace();
			} catch (LexicalException e) {
		//		e.printStackTrace();
			}
		}
		
		@Test
		public void testWrongEof(){
			String path ="src/test/data/testEOFWrong.txt";
			try {
				Scanner scanner = new Scanner(path);
				assertNotEquals(scanner.peekToken().getType(), TokenType.EOF);
			} catch (FileNotFoundException e) {
//				e.printStackTrace();
			} catch (IOException e) {
//				e.printStackTrace();
			} catch (LexicalException e) {
//				e.printStackTrace();
			}
		}
		
		
}