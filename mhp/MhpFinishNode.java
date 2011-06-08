package mhp;

public class MhpFinishNode extends MhpNode {
	
	public MhpNode s;

	public MhpFinishNode(MhpNode s) {
		super();
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
}
