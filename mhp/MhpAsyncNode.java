package mhp;

public class MhpAsyncNode extends MhpNode {
	public MhpNode s;
	public MhpAsyncNode(MhpNode s){
		super();
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
}