package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.FileNotFoundException;
import org.junit.jupiter.api.Test;
import ast.NodePrg;
import parser.Parser;
import parser.SyntaxException;
import symbolTable.SymbolTable;
import visitor.CodeGenerationVisitor;
import visitor.TypeCheckingVisitor;

public class TestCodeGenerationVisitor {
	@Test
	public void testCodeGenerationVisitorCorrect(){
		try {
			NodePrg tree = astInit("src/test/data/testCodeGenerationCorrect.txt");
			CodeGenerationVisitor visitor = new CodeGenerationVisitor();
			try {
				visitor.visit(tree);
//				System.out.println(visitor.getCode());
//				System.out.println(SymbolTable.toStr());
				assertEquals(visitor.getCode(), "1.0 6 5 k / sb 0 k lb p P 1 6 / 5 k sa 0 k la p P ");
			}
			catch(IndexOutOfBoundsException e) {
				fail("IndexOutOfBoundsException\n");
			}
		} catch (FileNotFoundException | SyntaxException e) {
			if(e instanceof FileNotFoundException)
				fail("FileNotFoundException!");
			else
				fail("SyntaxException!");
		}
	}
	
	@Test
	public void testCodeGenerationVisitorWrong(){
		try {
			NodePrg tree = astInit("src/test/data/testCodeGenerationWrong.txt");
			CodeGenerationVisitor visitor = new CodeGenerationVisitor();
			IndexOutOfBoundsException ioobe = assertThrows(IndexOutOfBoundsException.class, ()-> visitor.visit(tree), "ERROR: register index exceeds register number!\n");
			assertEquals(ioobe.getMessage(), "ERROR: register index exceeds register number!\n");
		} catch (FileNotFoundException | SyntaxException e) {
			if(e instanceof FileNotFoundException)
				fail("FileNotFoundException!");
			else
				fail("SyntaxException!");
		}
	}
	
	private NodePrg astInit(String path) throws FileNotFoundException, SyntaxException {
		Parser parser = new Parser(path);
		NodePrg tree = (NodePrg) parser.parse();
		SymbolTable.init();
		TypeCheckingVisitor visitor = new TypeCheckingVisitor();
		visitor.visit(tree);
		return tree;
	}
}