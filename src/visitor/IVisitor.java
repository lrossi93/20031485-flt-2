package visitor;

import ast.*;

public interface IVisitor {
	public abstract void visit(NodePrg node);
	public abstract void visit(NodeAssign node);
	public abstract void visit(NodeBinOp node);
	public abstract void visit(NodeConst node);
	public abstract void visit(NodeDcl node);
	public abstract void visit(NodeDeref node);
	public abstract void visit(NodeId node);
	public abstract void visit(NodePrint node);
	public abstract void visit(NodeConv node);
}