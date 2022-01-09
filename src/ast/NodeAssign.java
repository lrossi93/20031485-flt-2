package ast;

import visitor.IVisitor;

public class NodeAssign extends NodeStm {
	private final NodeId id;//nodo contenente la VARIABILE
	private NodeExpr expr;//ast contenente l'espressione assegnata
	
	public NodeAssign(NodeId id, NodeExpr expr) {
		this.id = id;
		this.expr = expr;
	}
	
	public NodeId getId() {
		return this.id;
	}
	
	public NodeExpr getExpr() {
		return this.expr;
	}
	
	public void setExpr(NodeExpr nodeExpr) {
		this.expr = nodeExpr;
	}
	
	@Override
	public String toString() {
		return "= " +this.id.toString()+" "+this.expr.toString();
	}
	
	@Override
	public boolean equals(Object o) {
		if(((o instanceof NodeAssign) 
				&& this.getId().equals(((NodeAssign)o).getId()) 
				&& this.getExpr().equals(((NodeAssign)o).getExpr())) 
			|| this == o)
			return true;
		return false;
	}

	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
}
