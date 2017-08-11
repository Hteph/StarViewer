package com.github.hteph.ObjectsOfAllSorts;

public class AsteroidBelt extends OrbitalObjects{


	public AsteroidBelt(String name, String description, double orbitDistance, StellarObject orbitingAround) {
		super(name, description, orbitDistance, orbitingAround);
		// TODO Auto-generated constructor stub
	}

	private double mass;	
	private double eccentricity;
	private String asterioidBeltType;
	private double asteroidBeltWidth;



	// Constructor ----------------------------------------------

	//Methods --------------------------------------------------

	@Override
	public String toString() {
		return "Ateroid Belt " + name + ": " + description;
	}	

	//Internal Methods ----------------------------------------

	// Getters and Setters -------------------------------------

	public double getMass() {
		return mass;
	}

	public void setMass(double mass) {
		this.mass = mass;
	}	

	public double getEccentricity() {
		return eccentricity;
	}

	public void setEccentricity(double eccentricity) {
		this.eccentricity = eccentricity;
	}

	public double getOrbitDistance() {
		return orbitDistance;
	}

	public String getAsterioidBeltType() {
		return asterioidBeltType;
	}

	public void setAsterioidBeltType(String asterioidBeltType) {
		this.asterioidBeltType = asterioidBeltType;
	}

	public double getAsteroidBeltWidth() {
		return asteroidBeltWidth;
	}

	public void setAsteroidBeltWidht(double asteroidBeltWidth) {
		this.asteroidBeltWidth = asteroidBeltWidth;
	}



	


}
