package test;

import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import ast.NodeAST;
import parser.Parser;
import parser.SyntaxException;
//import symbolTable.SymbolTable;
import visitor.TypeCheckingVisitor;



public class TestTypeCheckingVisitor {
	@Test
	public void testTypeCheckingVisitorCorrect(){
		String path ="src/test/data/testTypeCheckingCorrect.txt";
		try {
			Parser parser = new Parser(path);
			try {
				NodeAST nodePrg = parser.parse();
				//System.out.println(nodePrg.toString());
				TypeCheckingVisitor tc = new TypeCheckingVisitor();
				nodePrg.accept(tc);
				//System.out.println(SymbolTable.toStr());
				//System.out.println(tc.log());
				assertEquals(tc.log(), "");
			} catch (SyntaxException e) {
				fail("SyntaxException!");
			}
			
		} catch (FileNotFoundException e) {
			fail("FileNotFoundException!");
		}
	}
	
	@Test
	public void testTypeCheckingWrong() {
		String path ="src/test/data/testTypeCheckingWrong.txt";
		try {
			Parser parser = new Parser(path);
			try {
				NodeAST nodePrg = parser.parse();
				//System.out.println(nodePrg.toString());
				TypeCheckingVisitor tc = new TypeCheckingVisitor();
				nodePrg.accept(tc);
				//System.out.println(SymbolTable.toStr());
				//System.out.println(tc.log());
				assertEquals(tc.log(), "\n" + 
						"\n" + 
						"ERROR: = c 1.02\n" + 
						"Invalid assignment!\n" + 
						"\n" + 
						"ERROR: int a float b int c = a null = b + null 3.2 = c 1.02 p b \n" + 
						"Error in nodePrg!");
			} catch (SyntaxException e) {
				fail("SyntaxException!");
			}
			
		} catch (FileNotFoundException e) {
			fail("FileNotFoundException!");
		}
	}
}
