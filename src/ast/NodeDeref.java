package ast;

import visitor.IVisitor;

public class NodeDeref extends NodeExpr {
	//stesso tipo di NodeId id
	private final NodeId id;
	
	public NodeDeref(NodeId id) {
		this.id = id;
	}
	
	public NodeId getId() {
		return this.id;
	}
	
	@Override
	public String toString() {
		return id.toString();
	}

	@Override
	public boolean equals(Object o) {
		if(this == o ||
				this.id.equals(((NodeDeref)o).getId()))
			return true;
		return false;
	}

	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}

}
