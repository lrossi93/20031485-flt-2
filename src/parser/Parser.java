package parser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import ast.LangOp;
import ast.LangType;
import ast.NodeAST;
import ast.NodeAssign;
import ast.NodeBinOp;
import ast.NodeConst;
import ast.NodeDcl;
import ast.NodeDclStm;
import ast.NodeDeref;
import ast.NodeExpr;
import ast.NodeId;
import ast.NodePrg;
import ast.NodePrint;
import ast.NodeStm;
import scanner.LexicalException;
import scanner.Scanner;
import token.Token;
import token.TokenType;

public class Parser {
	private Scanner scanner;
	
	public Parser(String filename) throws FileNotFoundException {
		this.scanner = new Scanner(filename);
	}
	
	public NodeAST parse() throws SyntaxException {
		try{
			return parsePrg();
		}
		catch(SyntaxException e) {
			throw new SyntaxException(e.getMessage());
		}
	}
	
	//Prg -> DSs $
	private NodePrg parsePrg() throws SyntaxException{
		try{
			Token token = scanner.peekToken();
			//print("parsePrg");
			switch(token.getType()) {
				case FLOATDEC:
				case INTDEC:
				case ID:
				case PRINT:
//					NodePrg nodePrg1 = new NodePrg(parseDSs());
//					match(TokenType.EOF);
//					return nodePrg1;
				case EOF:
					NodePrg nodePrg = new NodePrg(parseDSs());
					match(TokenType.EOF);
					return nodePrg;
				default:
					panic(token);
			}
		}
		catch(IOException | LexicalException | SyntaxException e) {
			throw new SyntaxException(e.getMessage());
		}
		return null;//da togliere, messo solo per non avere errore
	}
	
	//DSs -> Dcl DSs | Stm DSs | eps
	private ArrayList<NodeDclStm> parseDSs() throws SyntaxException {
		try {
			Token token = scanner.peekToken();
			//print("parseDSs");
			switch(token.getType()) {
				case FLOATDEC:
				case INTDEC:
					NodeDcl nodeDcl = parseDcl();
					ArrayList<NodeDclStm> nodeDS1 = parseDSs();
					nodeDS1.add(0, nodeDcl);
					return nodeDS1;
				case ID:
				case PRINT:
					NodeStm nodeStm = parseStm();
					ArrayList<NodeDclStm> nodeDS2 = parseDSs();
					if(nodeStm != null)
						nodeDS2.add(0, nodeStm);
					return nodeDS2;
				case EOF:
					return new ArrayList<NodeDclStm>();
				default:
					panic(token);
			}
		}
		catch(IOException | LexicalException | SyntaxException e) {
			throw new SyntaxException(e.getMessage());
		}
		return null;
	}
	
	//Dcl -> tyFloat id; | tyInt id;
	private NodeDcl parseDcl() throws SyntaxException {
		try{
			Token token = scanner.peekToken();
			//print("parseDcl");	
			switch(token.getType()) {
				case FLOATDEC:
					match(TokenType.FLOATDEC);
					NodeId nodeId1 = new NodeId(match(TokenType.ID));
					match(TokenType.SEMI);
					return new NodeDcl(LangType.FLOAT, nodeId1);
				case INTDEC:
					match(TokenType.INTDEC);
					NodeId nodeId2 = new NodeId(match(TokenType.ID));
					match(TokenType.SEMI);
					return new NodeDcl(LangType.INT, nodeId2);//nodeDcl2;
				default:
					panic(token);
			}
		}
		catch(IOException | LexicalException | SyntaxException e) {
			throw new SyntaxException(e.getMessage());
		}
		return null;
	}
	
	//Stm -> print id; | id = Exp;
	private NodeStm parseStm() throws SyntaxException {
		try {
			Token token = scanner.peekToken();
			//print("parseStm");
			switch(token.getType()) {
				case PRINT:
					match(TokenType.PRINT);
					NodeStm nodePrint = new NodePrint(new NodeId(match(TokenType.ID)));
					match(TokenType.SEMI);
					return nodePrint;
				case ID:
					NodeId nodeId = new NodeId(match(TokenType.ID));
					match(TokenType.ASSIGN);
					NodeAssign nodeAssign = new NodeAssign(nodeId, parseExp());
					match(TokenType.SEMI);
					return nodeAssign;
				default:
					panic(token);
			}
		}
		catch(IOException | LexicalException | SyntaxException e) {
			throw new SyntaxException(e.getMessage());
		}
		return null;
	}
	
	//Exp -> Tr ExpP
	private NodeExpr parseExp() throws SyntaxException {
		try {
			Token token = scanner.peekToken();
			switch(token.getType()) {
				case INTVAL:
				case FLOATVAL:
				case ID:
					NodeExpr nodeTr = parseTr();
					NodeExpr nodeExpP = parseExpP(nodeTr);
					return nodeExpP;
				default:
					panic(token);
			}
		}
		catch(IOException | LexicalException | SyntaxException e) {
			throw new SyntaxException(e.getMessage());
		}
		return null;
	}
	
	//Tr -> Val TrP
	private NodeExpr parseTr() throws SyntaxException {
		try {
			Token token = scanner.peekToken();
			switch(token.getType()) {
				case INTVAL:
				case FLOATVAL:
				case ID:
					NodeExpr nodeVal = parseVal();
					return parseTrP(nodeVal);
				default:
					panic(token);
			}
		}
		catch(IOException | LexicalException | SyntaxException e) {
			throw new SyntaxException(e.getMessage());
		}
		return null;
	}
	
	//ExpP -> + Exp | - Exp | eps
	private NodeExpr parseExpP(NodeExpr leftBranch) throws SyntaxException {
		try {
			Token token = scanner.peekToken();
			switch(token.getType()) {
				case PLUS:
					match(TokenType.PLUS);
					return new NodeBinOp(LangOp.PLUS, leftBranch, parseExp());
				case MINUS:
					match(TokenType.MINUS);
					return new NodeBinOp(LangOp.MINUS, leftBranch, parseExp());
				case SEMI:
					//parse eps
					return leftBranch;
				default:
					panic(token);
			}
		}
		catch(IOException | LexicalException | SyntaxException e) {
			throw new SyntaxException(e.getMessage());
		}
		return null;
	}
	
	//Val -> int | float | id
	private NodeExpr parseVal() throws SyntaxException {
		try {
			Token token = scanner.peekToken();
			switch(token.getType()) {
				case INTVAL:
					return new NodeConst(LangType.INT, match(TokenType.INTVAL));
				case FLOATVAL:
					return new NodeConst(LangType.FLOAT, match(TokenType.FLOATVAL));
				case ID:
					return new NodeDeref(new NodeId(match(TokenType.ID)));
				default:
					panic(token);
			}
		}
		catch(IOException | LexicalException | SyntaxException e) {
			throw new SyntaxException(e.getMessage());
		}
		return null;
	}
	
	//TrP -> * Tr | / Tr | eps
	private NodeExpr parseTrP(NodeExpr leftBranch) throws SyntaxException {
		try {
			Token token = scanner.peekToken();
			switch(token.getType()) {
				case TIMES:
					match(TokenType.TIMES);
					return new NodeBinOp(LangOp.TIMES, leftBranch, parseTr());
				case DIV:
					match(TokenType.DIV);
					return new NodeBinOp(LangOp.DIVIDE, leftBranch, parseTr());
				case PLUS:
				case MINUS:
				case SEMI:
					//parse eps
					return leftBranch;
				default:
					panic(token);
			}
		}
		catch(IOException | LexicalException | SyntaxException e) {
			throw new SyntaxException(e.getMessage());
		}
		return null;
	}
	
	private String match(TokenType tokenType) throws SyntaxException, IOException, LexicalException {
		Token token = scanner.peekToken();
		if(tokenType == token.getType()) {
			Token nextToken = scanner.nextToken();
			//print(nextToken.toString());
			return nextToken.getValue();//ritorna una stringa solo se il token è un INTVAL o FLOATVAL
		}
		//else
			//panic(token);
		return null;
	}
	
	public void print(String s) {
		System.out.println(s);
	}
	
	private void panic(Token token) throws SyntaxException {
		throw new SyntaxException("SyntaxException@line:"+token.getLine()+"\nUnexpected token: "+token.toString());
	}
}