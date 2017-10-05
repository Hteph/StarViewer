package com.github.hteph.ObjectsOfAllSorts;

public class AsteroidBelt extends OrbitalObjects{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double mass;	
	private double eccentricity;
	private String asterioidBeltType;
	private double asteroidBeltWidth;



	// Constructor ----------------------------------------------
	public AsteroidBelt(String name, String description, double orbitDistance, StellarObject orbitingAround) {
		super(name, description, orbitDistance, orbitingAround);
		// TODO Auto-generated constructor stub
	}
	//Methods --------------------------------------------------

	@Override
	public String toString() {
		return "Ateroid Belt " + name + ": " + description;
	}	

	//Internal Methods ----------------------------------------

	// Getters and Setters -------------------------------------
	private String objectClass="Asteroid Belt";
	public String getObjectClass() {
		return objectClass;
	}

	public void setAsteroidBeltWidth(double asteroidBeltWidth) {
		this.asteroidBeltWidth = asteroidBeltWidth;
	}
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

	public double getOrbitalDistance() {
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
