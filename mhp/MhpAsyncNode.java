package mhp;

public class MhpAsyncNode extends MhpNode {
	public MhpNode s;
	public MhpAsyncNode(MhpNode s){
		this.s = s;
	}
	
	public String toString() {
		String string = "Async - (";
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