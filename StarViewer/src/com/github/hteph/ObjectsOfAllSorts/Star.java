package com.github.hteph.ObjectsOfAllSorts;

import java.util.ArrayList;

public class Star extends StellarObject {
	
	private double lumosity;	
	private double diameter;
	private String classification;
	private double age;
	private ArrayList<OrbitalObjects> orbitalObjects;
	private int abundance;
	
	// Constructor ------------------------------------------------------
	
	public Star(String name, String description, double lumosity, double mass, double radius, String classification, double age, int abundance) {
		
		super(name, description, mass);
		this.lumosity = lumosity;
		this.mass = mass;
		this.diameter = radius;
		this.classification = classification;
		this.age = age;
		this.abundance = abundance;
	}
	
	
	//Getters and Setter

	public double getLumosity() {
		return lumosity;
	}

	public double getMass() {
		return mass;
	}

	public double getRadius() {
		return diameter;
	}

	public String getClassification() {
		return classification;
	}

	public double getAge(){
		return age;
	}
	
	public int getAbundance() {
		return abundance;
	}
	
	public ArrayList<OrbitalObjects> getOrbitalObjects() {
		return orbitalObjects;
	}


	public void setOrbitalObjects(OrbitalObjects orbitalObjects) {
		this.orbitalObjects.add(orbitalObjects);
	}
	
	
	//toString etc
	
	public String toString() {
		return "Star: "+ getName() + " (" + classification + ")\n" + getDescription();
	}





	
	
	

}
