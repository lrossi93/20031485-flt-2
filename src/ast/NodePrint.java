package ast;

import visitor.IVisitor;

public class NodePrint extends NodeStm {
	private final NodeId id;
	
	public NodePrint(NodeId id) {
		this.id = id;
	}
	
	public NodeId getId() {
		return this.id;
	}
	
	public String toString() {
		return "p "+ id.toString(); //sistemare
	}

	@Override
	public boolean equals(Object o) {
		if(this == o ||
				this.id.equals(((NodePrint)o).getId()))
			return true;
		return false;
	}

	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
}
