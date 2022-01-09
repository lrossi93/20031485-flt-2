package visitor;

import ast.*;
import symbolTable.Attributes;
import symbolTable.SymbolTable;

public class TypeCheckingVisitor implements IVisitor{
	StringBuilder log = new StringBuilder();
	
	@Override
	public void visit(NodePrg node) {
		SymbolTable.init();
		for(NodeAST ast : node.getDclStm()) {
			ast.accept(this);
		}
		for(NodeDclStm n : node.getDclStm()) {
			if(n.getTypeDescriptor() == TypeDescriptor.ERROR)
				node.setTypeDescriptor(TypeDescriptor.ERROR);
		}
		if(node.getTypeDescriptor() == null)
			node.setTypeDescriptor(TypeDescriptor.VOID);
		if(node.getTypeDescriptor() == TypeDescriptor.ERROR)
			log.append("\n\nERROR: "+node.toString()+"\nError in nodePrg!");
	}
	
	/*visita id e expr. 
	 * Poi controlla compatibilità e, se necessario, rimpiazza expr con converti(expr). 
	 * Deve assegnare Error a resType di node se l’espressione aveva resType uguale a Error 
	 * oppure se i tipi di identificatore e espressione non erano compatibili. 
	 * Se tutto va bene assegna VOID a resType.*/
	@Override
	public void visit(NodeAssign node) {
//		print("visit(nodeAssign)");
		//visita nodeId
		node.getId().accept(this);
		//visita nodeExpr
		node.getExpr().accept(this);
		//controlla compatibilità e, se necessario, rimpiazza expr con converti(expr)
		if(compatible(node.getId().getTypeDescriptor(), node.getExpr().getTypeDescriptor())) {
			if(node.getId().getTypeDescriptor() == node.getExpr().getTypeDescriptor())
				node.setExpr(convert(node.getExpr()));
			node.setTypeDescriptor(TypeDescriptor.VOID);
		}
		else {
			node.setTypeDescriptor(TypeDescriptor.ERROR);
			log.append("\n\nERROR: "+ node.toString() + "\nInvalid assignment!");
		}
	}

	/*visita exprLeft e exprRight. Poi
	(a) se uno delle sottoespressioni ha resType uguale a Error assegna Error a resType del nodo corrente
	(b) se i tipi delle espressioni sono uguali assegna il loro tipo al nodo corrente, altrimenti
	(c) introduce la conversione di tipo (come per l’assegnamento).*/
	@Override
	public void visit(NodeBinOp node) {
//		print("visit(nodeBinOp)");
		node.getLeft().accept(this);
		node.getRight().accept(this);
		//se uno delle sottoespressioni ha resType uguale a Error assegna Error a resType del nodo corrente
		if(node.getLeft().getTypeDescriptor() == TypeDescriptor.ERROR
				|| node.getRight().getTypeDescriptor() == TypeDescriptor.ERROR) {
			node.setTypeDescriptor(TypeDescriptor.ERROR);
			log.append("\n\nERROR: " + node.toString()+"\nType mismatch!");
		}
		
		//se i tipi delle espressioni sono uguali assegna il loro tipo al nodo corrente, altrimenti
		else {
			if(node.getLeft().getTypeDescriptor() == node.getRight().getTypeDescriptor()) {
				node.setTypeDescriptor(node.getLeft().getTypeDescriptor());
			}
			//introduce la conversione di tipo (come per l’assegnamento)
			//quindi converto il nodo di destra in float (solo int -> float, mai float -> int)
			else {
				if(node.getLeft().getTypeDescriptor() == TypeDescriptor.INT)
					node.setLeft(convert(node.getLeft()));
				else if(node.getRight().getTypeDescriptor() == TypeDescriptor.INT)
					node.setRight(convert(node.getRight()));
				
				node.setTypeDescriptor(TypeDescriptor.FLOAT);
			}
		}
	}
	
	@Override
	public void visit(NodeConv node) {
//		print("visit(nodeConv)");
	}
	
	@Override
	public void visit(NodeConst node) {
//		print("visit(nodeConst)");
		if(node.getType() == LangType.INT) {
			node.setTypeDescriptor(TypeDescriptor.INT);
		}
		else if(node.getType() == LangType.FLOAT) {
			node.setTypeDescriptor(TypeDescriptor.FLOAT);
		}
		//prossimo else superfluo, non dovrebbe mai andarci altrimenti avrebbe un syntax error
		else {
			node.setTypeDescriptor(TypeDescriptor.ERROR);
			log.append("\n\nERROR: "+node.toString()+"\nInvalid const type!");
		}
	}

	/*Sia id il campo di tipo NodeId di node: se il nome di
	id è già definito nella Symbol Table assegna Error a resType (typeDescriptor) di node.
	Altrimenti crea un oggetto attr (istanza di Attributes) contenente un campo
	con il suo tipo e inserisci nome associato a attr nella Symbol Table.*/
	@Override
	public void visit(NodeDcl node) {
//		print("visit(nodeDcl)");
		if(!SymbolTable.enter(node.getId().getId(), new Attributes(node.getType()))) {
			node.setTypeDescriptor(TypeDescriptor.ERROR);
			log.append("\n\nERROR: "+node.toString()+"\nInvalid declaration: "+node.getId().getId()+" already defined in ST!");
		}
		else {
			node.setTypeDescriptor(TypeDescriptor.VOID);
		}
	}

	/*Sia nome il nome contenuto in node. Se nome non è
	definito nella Symbol Table assegna Error a resType di node. Altrimenti
	assegna attr.tipo a node.resType e attr a node.definition.*/
	@Override
	public void visit(NodeId node) {
//		print("visit(nodeId)");
		//se il nome non è definito nella ST
		Attributes attrs;
		if((attrs = SymbolTable.lookup(node.getId())) == null) {
			node.setTypeDescriptor(TypeDescriptor.ERROR);
			log.append("\n\nERROR: "+node.toString()+"\nIdentifier not in ST!"); //precisare
		}
		//se il nome è definito nella ST ha degli attributi, già inseriti
		//nella ST sotto il nome del nodo id (node.getId())
		//quindi li recupero e li assegno al TypeDescriptor di node
		//e di nodeId
		else {
			if(attrs.getType() == LangType.INT)
				node.setTypeDescriptor(TypeDescriptor.INT);
			else if(attrs.getType() == LangType.FLOAT)
				node.setTypeDescriptor(TypeDescriptor.FLOAT);
			node.setDefinition(attrs);
		}
	}
	
	@Override
	public void visit(NodeDeref node) {
//		print("visit(nodeDeref)");		
		node.getId().accept(this);
		node.setTypeDescriptor(node.getId().getTypeDescriptor());
		if(node.getId().getTypeDescriptor() == TypeDescriptor.ERROR)
			log.append("\n\nERROR: "+node.toString()+"\nVariable never defined in ST!");
	}

	/*Visita del campo id il nome contenuto in node. Se
	nome non è definito nella Symbol Table assegna Error a resType di node.
	Altrimenti assegna Void a resType di node.*/
	@Override
	public void visit(NodePrint node) {
//		print("visit(nodePrint)");
		node.getId().accept(this);
		if(node.getId().getTypeDescriptor() == TypeDescriptor.ERROR) {
			node.setTypeDescriptor(TypeDescriptor.ERROR);
			log.append("\n\nERROR: "+node.toString()+"\nTrying to print an undeclared variable!");
		}
		else {
			node.setTypeDescriptor(TypeDescriptor.VOID);
		}
	}
	
	private boolean compatible(TypeDescriptor t1, TypeDescriptor t2) {
		if(((t1 != TypeDescriptor.ERROR && t2 != TypeDescriptor.ERROR) && (t1 == t2))
				||(t1 == TypeDescriptor.FLOAT && t2 == TypeDescriptor.INT))
			return true;
		else
			return false;
	}
	
	/*se resType di node è Float ritorna node altrimenti ritorna un NodeConv 
	 * che ha resType uguale a Float e contiene node.*/
	private NodeExpr convert(NodeExpr node) {
		if(node.getTypeDescriptor() == TypeDescriptor.FLOAT)
			return node;
		else {
			NodeConv nodeConv = new NodeConv(node);
			nodeConv.setTypeDescriptor(TypeDescriptor.FLOAT);
			return nodeConv;
		}
	}
	public void print(String s) {
		System.out.println(s);
	}
	
	public String log() {
		return log.toString();
	}
}