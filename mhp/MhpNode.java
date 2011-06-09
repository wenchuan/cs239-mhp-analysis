package mhp;

public class MhpNode {
	public String toString() {
		return "MhpNode(Default toString)";
	}
	
	public MhpInfo accept(MhpInfoGenerator gen){
		return gen.visit(this);
	}
	
	public MhpNode accept(MhpVisitor vis, int level) {
		return vis.unfold(this, level);
	}
}
