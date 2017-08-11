package com.github.hteph.ObjectsOfAllSorts;

public abstract class StellarObject {
	
	protected String name;
	protected String description;
	protected double mass;


	
	
	
	//Constructor -------------------------------------------------------------

	public StellarObject(String name, String description, double mass) {
		super();
		this.name = name;
		this.description = description;
		this.mass = mass;
	}
	
	


	public String getName() {
	
		return name;
	}
	

	public String getDescription() {
		return description;		
	}

	public double getMass() {
		return mass;
	}

	
}
