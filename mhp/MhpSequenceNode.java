package mhp;

public class MhpSequenceNode extends MhpNode {
	public MhpNode s0;
	public MhpNode s1;

	public MhpSequenceNode(MhpNode fst, MhpNode snd) {
		this.s0 = fst;
		this.s1 = snd;
	}

	public String toString() {
		String string = "Seq - (";
		if (s0 != null)
			string += s0.toString();
		else
			string += " ";
		string += "; ";
		if (s1 != null)
			string += s1.toString();
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