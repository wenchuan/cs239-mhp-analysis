package mhp;

import java.util.*;

class Pair {
	public Integer fst;
	public Integer snd;
	
	public boolean equals(Object p) {
		return this.fst == ((Pair)p).fst && this.snd == ((Pair)p).snd;
	}
	
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
		Integer temp = 0;
		for (i = fst.iterator(); i.hasNext(); ) {
			temp = i.next();
			for (j = snd.iterator(); j.hasNext(); )
				res.add(new Pair(temp, j.next()));
		}
		return res;
	}
	
	public String toString() {
		String string = "";
		string += "M: ";
		for (Iterator<Pair> it = M.iterator(); it.hasNext();) {
			Pair p = it.next();
			string += "(" + p.fst.toString() + ", " + p.snd.toString() + ") ";
		}
		string += "\nO: ";
		for (Iterator<Integer> it = O.iterator(); it.hasNext();) {
			string += it.next().toString() + ", ";
		}
		string += "\nL ";
		for (Iterator<Integer> it = L.iterator(); it.hasNext();) {
			string += it.next().toString() + ", ";
		}
		return string;
	}
}