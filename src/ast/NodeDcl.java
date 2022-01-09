package ast;

import visitor.IVisitor;

public class NodeDcl extends NodeDclStm {
	private final LangType type;//int o float
	private final NodeId id;//nodo contenente il nome della variabile
	
	//constructor
	public NodeDcl(LangType type, NodeId id) {
		this.type = type;
		this.id = id;
	}
	
	//methods
	public NodeId getId() {
		return id;
	}

	public LangType getType() {
		return type;
	}
	
	@Override
	public String toString() {
		return this.type+" "+this.id.toString();
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o || 
				((this.type == ((NodeDcl) o).getType()))
						&& (this.id.equals(((NodeDcl) o).getId())))
					return true;
		return false;
	}

	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
}
