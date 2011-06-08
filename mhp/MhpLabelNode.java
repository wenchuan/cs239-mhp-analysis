package mhp;

public class MhpLabelNode extends MhpNode {
	public int l;
	public MhpLabelNode(int l){
		super();
		this.l = l;
		L.add(l);
	}
	public String toString() {
		return "L" + l;
	}
}
