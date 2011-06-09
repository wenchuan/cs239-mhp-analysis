package mhp;

public class MhpFinishNode extends MhpNode {
	
	public MhpNode s;

	public MhpFinishNode(MhpNode s) {
		this.s = s;
	}
	
	public String toString() {
		String string = "Finish - (";
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
