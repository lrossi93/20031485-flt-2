package ast;

import symbolTable.Attributes;
import visitor.IVisitor;

public class NodeId extends NodeAST {
	private final String id;
	private Attributes definition;
	//private String lexval;
	
	public NodeId(String id) {
		super();
		this.id = id;
		this.definition = null;
	}
	
	public NodeId(String id, TypeDescriptor typeDescriptor) {
		this.definition = null;
		this.id = id;
	}
	
	public NodeId(String id, Attributes attrs) {
		this.definition = attrs;
		this.id = id;
	}
	
	public Attributes getDefinition() {
		return definition;
	}

	public void setDefinition(Attributes definition) {
		this.definition = definition;
	}

	@Override
	public String toString() {
		return this.getId();
		//return "<"+this.getId()+","+this.getLexval()+">";
	}

	@Override
	public boolean equals(Object o) {
		if(((o instanceof NodeId) && this.getId().equals(((NodeId)o).getId())) || this == o)
			return true;
		return false;
	}

	public String getId() {
		return this.id;
	}

	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
}
