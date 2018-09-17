package com.thinkgem.jeesite.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Test {

	static boolean is;
	public static void main(String[] args) {
		Set<Obj> objset = new HashSet<Obj>();
		List<Obj> objlist = new ArrayList<Obj>();
		for(int i = 0; i < 3; i ++){
			Obj o = new Obj(i, i);
			objlist.add(o);
			objset.add(o);
		}
		objlist.get(2).setJ(20);
		objlist.get(1).setJ(10);
		objset.add(objlist.get(2));
		objset.add(objlist.get(1));
		System.out.println(objset.size());
	}
	
	
}

class Obj {
	int i = 0;
	int j = 0;
	
	public Obj(int i, int j) {
		super();
		this.i = i;
		this.j = j;
	}
	public int getI() {
		return i;
	}
	public int getJ() {
		return j;
	}
	public void setI(int i) {
		this.i = i;
	}
	public void setJ(int j) {
		this.j = j;
	}
	
	
}