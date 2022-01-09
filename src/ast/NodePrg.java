package ast;

import java.util.ArrayList;

import visitor.IVisitor;

public class NodePrg extends NodeAST {
	private ArrayList<NodeDclStm> dclStm;

	public NodePrg() {
		this.dclStm = null;
	}
	
	public NodePrg(ArrayList<NodeDclStm> dclStm) {
		this.dclStm = new ArrayList<NodeDclStm>();
		this.dclStm.addAll(dclStm);
	}
	
	//methods
	public ArrayList<NodeDclStm> getDclStm(){
		return this.dclStm;
	}
	
	@Override
	public String toString() {
		int i = 0;
		String toString = "";
		while(i < this.dclStm.size()) {
			toString += this.dclStm.get(i).toString() + " ";
			i++;
		}
		return toString;
	}

	@Override
	public boolean equals(Object o) {
		if(this == o ||
				this.dclStm.equals(((NodePrg)o).getDclStm()))
			return true;
		return false;
	}

	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
}
