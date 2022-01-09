package ast;

import visitor.IVisitor;

public class NodeBinOp extends NodeExpr {//stesso tipo di left e right
	//può essere necessario introdurre una conversione se un nodo
	//ha tipo INT e l’altro FLOAT
	
	private final LangOp op;//operatore
	private NodeExpr left;//ast a sx dell'operatore
	private NodeExpr right;//ast a dx dell'operatore
	
	//constructor
	public NodeBinOp(LangOp op, NodeExpr left, NodeExpr right) {
		this.op = op;
		this.left = left;
		this.right = right;
	}
	
	//methods
	public LangOp getOp() {
		return op;
	}

	public NodeExpr getLeft() {
		return left;
	}

	public NodeExpr getRight() {
		return right;
	}
	
	public void setLeft(NodeExpr left) {
		this.left = left;
	}

	public void setRight(NodeExpr right) {
		this.right = right;
	}

	@Override
	public String toString() {
		return op + " "+this.left.toString()+" "+this.right.toString();
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o || 
				((this.left.equals(((NodeBinOp) o).getLeft()))
						&& (this.left.equals(((NodeBinOp) o).getLeft())) 
						&& (this.op == ((NodeBinOp) o).getOp())))
					return true;
		return false;
	}

	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
}
