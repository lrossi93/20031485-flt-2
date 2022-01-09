package ast;

import visitor.IVisitor;

public class NodeConv extends NodeExpr {

	//deve contenere un'espressione che indicherà, per la fase di generazione
	//del codice, che deve essere effettuato un cast da int a float del risultato
	//dell'espressione
	private NodeExpr expr;
	
	public NodeConv(NodeExpr nodeExpr) {
		this.expr = nodeExpr;
	}
	
	public NodeExpr getExpr() {
		return expr;
	}

	public void setExpr(NodeExpr nodeExpr) {
		this.expr = nodeExpr;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}

}
