package mhp;

public class MhpIfelseNode extends MhpNode {
	public MhpNode e, ifnode, elsenode;
	
	public MhpIfelseNode(MhpNode s, MhpNode s1, MhpNode s2){
		e = s;
		ifnode = s1;
		elsenode = s2;
	}
	public String toString() {
		String string = "If (";
		string += e.toString();
		string += ") then (";
		if (ifnode != null)
			string += ifnode.toString();
		else
			string += " ";
		string += ") else (";
		if (elsenode != null)
			string += elsenode.toString();
		else
			string += " ";
		string += ")";
		return string;
	}
	
	public MhpInfo accept(MhpInfoGenerator gen){
		return gen.visit(this);
	}
	
	public MhpNode accept(MhpVisitor vis, int level) {
		return vis.unfold(this, level);
	}

}