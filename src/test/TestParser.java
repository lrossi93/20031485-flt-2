package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import parser.Parser;
import parser.SyntaxException;

public class TestParser {
	@Test
	public void testParserCorrect(){
		String path ="src/test/data/testParserCorrect.txt";
		try {
			Parser parser = new Parser(path);
			try {
				assertEquals(parser.parse().toString(), "int a = a 5 float b = b + a 3.2 p b ");
			} catch (SyntaxException e) {
				fail("SyntaxException! " + e.getMessage());
			}
			
		} catch (FileNotFoundException e) {
			fail("FileNotFoundException! "+ e.getMessage());
		}
	}
	
	@Test
	public void testParserWrong(){
		String path ="src/test/data/testParserWrong.txt";
		try {
			Parser parser = new Parser(path);
			SyntaxException e = assertThrows(SyntaxException.class, ()->parser.parse(), "SyntaxException@line:1\nUnexpected token: <PLUS, val: +, line: 1>");
			assertEquals(e.getMessage(), "SyntaxException@line:1\nUnexpected token: <PLUS, val: +, line: 1>");
		} catch (FileNotFoundException e) {
			fail("FileNotFoundException! "+ e.getMessage());
		}
	}
}
