package mhp;

public class MhpLabelNode extends MhpNode {
	public int l;
	public MhpLabelNode(int l){
		this.l = l;
	}
	public String toString() {
		return "L" + l;
	}
	
	public MhpInfo accept(MhpInfoGenerator gen){
		return gen.visit(this);
	}
	
	public MhpNode accept(MhpVisitor vis, int level) {
		return vis.unfold(this, level);
	}

}