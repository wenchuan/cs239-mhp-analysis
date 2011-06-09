package mhp;

public class MhpInfoGenerator {
	public MhpInfo visit(MhpNode n){
		return null;
	}

	public MhpInfo visit(MhpLabelNode n) {
		return new MhpInfo(n.l);
	}
	
	public MhpInfo visit(MhpAsyncNode n) {
		MhpInfo res = n.s.accept(this);
		res.O = res.L;
		return res;
	}
	
	public MhpInfo visit(MhpFinishNode n) {
		MhpInfo res = n.s.accept(this);
		res.O.clear();
		return res;
	}
	
	public MhpInfo visit(MhpLoopNode n) {
		MhpInfo res = n.s.accept(this);
		res.M.addAll(MhpInfo.cross(res.O, res.L));
		return res;
	}
	
	public MhpInfo visit(MhpSequenceNode n) {
		MhpInfo m0 = n.s0.accept(this);
		MhpInfo m1 = n.s1.accept(this);
		MhpInfo res = new MhpInfo();
		res.L.addAll(m0.L);
		res.L.addAll(m1.L);
		res.O.addAll(m0.O);
		res.O.addAll(m1.O);
		res.M.addAll(m0.M);
		res.M.addAll(m1.M);
		res.M.addAll(MhpInfo.cross(m0.O, m1.L));
		return res;
	}
	
	public MhpInfo visit(MhpIfelseNode n) {
		MhpInfo res = new MhpInfo();
		MhpInfo m0 = n.e.accept(this);
		MhpInfo m1 = n.ifnode.accept(this);
		MhpInfo m2 = n.elsenode.accept(this);
		res.L.addAll(m0.L);
		res.L.addAll(m1.L);
		res.L.addAll(m2.L);
		res.O.addAll(m0.O);
		res.O.addAll(m1.O);
		res.O.addAll(m2.O);
		res.M.addAll(m0.M);
		res.M.addAll(m1.M);
		res.M.addAll(m2.M);
		res.M.addAll(MhpInfo.cross(m0.O, m1.L));
		res.M.addAll(MhpInfo.cross(m0.O, m2.L));
		return res;
	}

	public void printMhp() {
		// TODO Auto-generated method stub
	}
}
