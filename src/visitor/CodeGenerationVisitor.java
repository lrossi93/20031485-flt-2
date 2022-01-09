package visitor;
import symbolTable.Attributes;
import symbolTable.SymbolTable;

import java.util.Iterator;

import ast.NodeAssign;
import ast.NodeBinOp;
import ast.NodeConst;
import ast.NodeConv;
import ast.NodeDcl;
import ast.NodeDclStm;
import ast.NodeDeref;
import ast.NodeId;
import ast.NodePrg;
import ast.NodePrint;

public class CodeGenerationVisitor implements IVisitor{
	private StringBuffer code;
	private static char[] registers = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
	private static int registerIndex;
	
	public CodeGenerationVisitor() {
		this.code = new StringBuffer();
		registerIndex = 0;
	}
	
	private static char newRegister() throws IndexOutOfBoundsException {
		char newRegister;
//		System.out.println("register index: "+ registerIndex + "(before assignment)");
		if(registerIndex == 25) 
			throw new IndexOutOfBoundsException("ERROR: register index exceeds register number!\n");
		
		newRegister = registers[registerIndex];
//		System.out.println("\tregister chosen: reg["+registerIndex+"]="+ registers[registerIndex]);
		registerIndex++;
		return newRegister;

	}
	
	private void init() throws IndexOutOfBoundsException {
		Iterator<Attributes> iterator = SymbolTable.getTableIterator();
		while(iterator.hasNext())
			iterator.next().setRegister(newRegister());
	}
	
	@Override
	public void visit(NodePrg node) {
		init();
		for(NodeDclStm nodeDclStm : node.getDclStm())
			nodeDclStm.accept(this);
	}

	@Override
	public void visit(NodeAssign node) {
		node.getExpr().accept(this);
		code.append("s" + node.getId().getDefinition().getRegister() + " ");
		code.append("0 k ");
	}

	@Override
	public void visit(NodeBinOp node) {
		node.getLeft().accept(this);
		node.getRight().accept(this);
		
		switch(node.getOp()) {
			case PLUS:
				code.append("+ ");
				break;
				
			case MINUS:
				code.append("- ");
				break;
			
			case TIMES:
				code.append("* ");
				break;
			
			case DIVIDE:
				code.append("/ ");
				break;
			
			default:
				break;
		}
	}

	@Override
	public void visit(NodeConst node) {
		code.append(node.getValue() + " ");
	}

	@Override
	public void visit(NodeDcl node) {
		//nothing to do here
	}

	@Override
	public void visit(NodeDeref node) {
		code.append("l" + node.getId().getDefinition().getRegister() + " ");
	}

	@Override
	public void visit(NodeId node) {
		//nothing to do here
	}

	@Override
	public void visit(NodePrint node) {
		code.append("l" + node.getId().getDefinition().getRegister() + " ");
		code.append("p ");
		code.append("P ");
	}

	@Override
	public void visit(NodeConv node) {
		node.getExpr().accept(this);
		code.append("5 k ");
	}
	
	public String getCode() {
		return code.toString();
	}

}
