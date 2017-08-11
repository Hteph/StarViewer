package com.github.hteph.ObjectsOfAllSorts;

public class AmosphericGases {


	private String name;
	private int percentageInAtmo;



	public AmosphericGases(String name, int percentageInAtmo) {

		this.name = name;
		this.percentageInAtmo = percentageInAtmo;
	}
//Getters and Setters-----------------------------------------------------------------------------------------


	public String getName() {
		return name;
	}
	public int getPercentageInAtmo() {
		return percentageInAtmo;
	}


	public void setPercentageInAtmo(int percentageInAtmo) {
		this.percentageInAtmo = percentageInAtmo;
	}


	@Override
	public String toString() {
		String atmoString= name + " = " + percentageInAtmo + " %";
		return atmoString;
	}


}
