package com.github.hteph.ObjectsOfAllSorts;

import java.io.Serializable;

public abstract class StellarObject implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected String name;
	protected String description;



	
	
	
	//Constructor -------------------------------------------------------------

	public StellarObject(String name, String description) {
		super();
		this.name = name;
		this.description = description;

	}
	
	


	public String getName() {
	
		return name;
	}
	

	public String getDescription() {
		return description;		
	}

	public abstract double getOrbitalDistance();

	
}
