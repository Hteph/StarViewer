package com.github.hteph.ObjectsOfAllSorts;

public abstract class StellarObject {
	
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



	
}
