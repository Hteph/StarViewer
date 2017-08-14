package com.github.hteph.Generators;

import com.github.hteph.ObjectsOfAllSorts.Star;

public class testMass {

	public static void main(String[] args) {
			int Y=0;
			int T=0;
			int M=0;
			int K =0;
			int G =0;
			int F=0;
			int A=0;
			int B=0;
			int O=0;	
		for (int i=0; i<1000; i++) {
			
			Star star = (Star) StarGenerator.Generator();
			
			char test =star.getClassification().charAt(0);
			
			
			switch (test) {
			case 'Y':
			Y++;
			break;
			case 'T':
			T++;
			break;
			case 'M':
			M++;
			break;
			case 'K':
			K++;
			break;
			case 'F':
			F++;
			break;
			case 'A':
			A++;
			break;
			case 'B':
			B++;
			break;
			case 'O':
			O++;
			break;
			}
			
		}
		
		System.out.println(" "+Y+" "+T+" "+M+" "+K+" "+F+" "+A+" "+B+" "+O);

	}

	
}
