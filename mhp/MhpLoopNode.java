package mhp;

public class MhpLoopNode extends MhpNode {
	
	public MhpNode s;

	public MhpLoopNode(MhpNode s) {
		this.s = s;
	}

	public String toString() {
		String string = "Loop - (";
		if (s != null)
			string += s.toString();
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
