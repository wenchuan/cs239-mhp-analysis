package mhp;

import java.util.*;

class Pair {
	public Integer fst;
	public Integer snd;
	public Pair(Integer fst, Integer snd) {
		this.fst = fst;
		this.snd = snd;
	}
}

public class MhpInfo {
	public Set<Pair> M;
	public Set<Integer> O;
	public Set<Integer> L;
	
	public MhpInfo() {
		M = new HashSet<Pair>();
		O = new HashSet<Integer>();
		L = new HashSet<Integer>();
	}
	
	public MhpInfo(int n){
		this();
		L.add(n);
	}
	
	public static Set<Pair> cross(Set<Integer> fst, Set<Integer> snd) {
		Set<Pair> res = new HashSet<Pair>();
		Iterator<Integer> i, j;
		for (i = fst.iterator(); i.hasNext(); )
			for (j = snd.iterator(); j.hasNext(); )
				res.add(new Pair(i.next(), j.next()));
		return res;
	}
}