package ast;

import visitor.IVisitor;

//node representing constants in the AST
public class NodeConst extends NodeExpr {
	//fields
	private final LangType type;//int o float, stesso tipo di value
	private final String value;//valore
	
	//constructor
	public NodeConst(LangType type, String value) {
		this.type = type;
		this.value = value;
	}
	
	//methods
	public String getValue() {
		return this.value;
	}
	
	public LangType getType() {
		return this.type;
	}
	
	@Override
	public String toString() {
		return /*this.type+":"+*/this.getValue();
	}

	@Override
	public boolean equals(Object o) {
		if(this == o || 
			((this.value.equals(((NodeConst) o).getValue())) && this.type == ((NodeConst) o).getType()))
				return true;
		return false;
	}

	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
}
