package mhp;

public class MhpFunctionNode extends MhpNode {
	
	public String name;
	
	//TODO thing carefully about how to handle this info
	public MhpFunctionNode(String str) {
		name = str;
	}
	public String toString() {
		String string = "FunctionCall - (";
		string += name;
		string += ")";
		return string;
	}
}
