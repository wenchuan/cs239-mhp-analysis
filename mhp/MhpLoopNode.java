package mhp;

public class MhpLoopNode extends MhpNode {
	
	public MhpNode s;

	public MhpLoopNode(MhpNode s) {
		super();
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
}
