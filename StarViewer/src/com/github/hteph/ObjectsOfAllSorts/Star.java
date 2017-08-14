package com.github.hteph.ObjectsOfAllSorts;

import java.util.ArrayList;

import com.github.hteph.Utilities.numberUtilities;

public class Star extends StellarObject {
	
	private double lumosity;	
	private double radius;
	private String classification;
	private double age;
	private ArrayList<OrbitalObjects> orbitalObjects = new ArrayList<>();
	private int abundance;
	private double mass;
	
	// Constructor ------------------------------------------------------
	
	public Star(String name, String description, double lumosity, double mass, double radius, String classification, double age, int abundance) {
		
		super(name, description);
		
		this.lumosity = numberUtilities.nicefyDouble(lumosity);
		this.radius = numberUtilities.nicefyDouble(radius);
		this.classification = classification;
		this.age = numberUtilities.nicefyDouble(age);
		this.abundance = abundance;
		this.mass = numberUtilities.nicefyDouble(mass);
		
	}
	
	
	//Getters and Setter

	public double getLumosity() {
		return lumosity;
	}

	public double getRadius() {
		return radius;
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
		return "Star: "+ getName() + " (" + classification + ")";
	}


	public double getMass() {
		return mass;
	}








	
	
	

}
