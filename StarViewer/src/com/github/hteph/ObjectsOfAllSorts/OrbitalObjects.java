package com.github.hteph.ObjectsOfAllSorts;

public class OrbitalObjects extends StellarObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected double orbitDistance;
	protected double orbitaleccentricity;
	protected char orbitalObjectClass;
	protected StellarObject orbitingAround;
		
	//Constructor ----------------------------------------------------
	

	public OrbitalObjects(String name, String description, double  orbitDistance, StellarObject orbitingAround) {
		super(name, description);
		this.orbitDistance = orbitDistance;
		this.orbitingAround =orbitingAround;
	}

	public double getOrbitalDistance() {
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
