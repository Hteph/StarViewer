package com.github.hteph.ObjectsOfAllSorts;

public class OrbitalObjects extends StellarObject {
	
	public OrbitalObjects(String name, String description, double mass, StellarObject orbitingAround) {
		super(name, description, mass);
		// TODO Auto-generated constructor stub
	}


	protected double orbitDistance;
	protected double orbitaleccentricity;
	protected char orbitalObjectClass;
	protected StellarObject orbitingAround;
	
	
	
	//Constructor ----------------------------------------------------
	



	public double getOrbitDistance() {
		return orbitDistance;
	}


	public double getOrbitaleccentricity() {
		return orbitaleccentricity;
	}


	public char getOrbitalObjectClass() {
		return orbitalObjectClass;
	}


	public StellarObject getOrbitingAround() {
		return orbitingAround;
	}
	
	
	
	


}
