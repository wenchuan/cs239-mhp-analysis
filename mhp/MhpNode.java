package mhp;

import java.util.ArrayList;

public class MhpNode {
	protected ArrayList<Integer[]> M;		// May Happen in Parallel informations
	protected ArrayList<Integer> O;		// Labels that can still be running after
	protected ArrayList<Integer> L;		// Labels that can be running during the time
	
	public MhpNode() {
		M = new ArrayList<Integer[]>();
		O = new ArrayList<Integer>();
		L = new ArrayList<Integer>();		
	}
	
	public String toString() {
		return "MhpNode(Default toString)";
	}
}
