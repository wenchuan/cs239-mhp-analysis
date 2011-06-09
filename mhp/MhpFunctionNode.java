package mhp;

public class MhpFunctionNode extends MhpNode {
	
	public String name;
	public int level;
	
	public MhpFunctionNode(String str) {
		name = str;
		level = 0;
	}
	
	public String toString() {
		String string = "FunctionCall - (";
		string += name;
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
