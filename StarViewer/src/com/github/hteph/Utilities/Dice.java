package com.github.hteph.Utilities;

public class Dice {
	
	public static int d6(){
		int a =(int)(1+Math.random()*6);

		return a;
	}
	
	public static int _2d6(){
		
	return d6()+d6();	
	
	}
	
	public static int _3d6(){
		
	return d6()+d6()+d6();	
	
	}
	
	public static int d10(){
		int a =(int)(1+Math.random()*10);

		return a;
	}

}
