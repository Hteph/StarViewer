package com.github.hteph.ObjectsOfAllSorts;

import java.util.ArrayList;

import com.github.hteph.Utilities.numberUtilities;

public class Jovian extends OrbitalObjects {
	

	private int radius;

	private int mass;
	private double orbitalPeriod; //in earth years
	private double axialTilt;
	private double eccentricity;
	private String classificationName;
	private double rotationalPeriod;

	private double magneticField;
	private int baseTemperature;

	private double orbitalInclination;
	String lifeType;

	


	
	// Constructor ----------------------------------------------

	public Jovian(String name, String description, String classificationName, double orbitDistance, StellarObject orbitingAround) {
		super(name, description, orbitDistance, orbitingAround);
		this.classificationName =classificationName;
	}

	

	//Methods --------------------------------------------------
	
		@Override
	public String toString() {
		return name + ", Mass = "+mass;
	}


	//Internal Methods ----------------------------------------




	// Getters and Setters -------------------------------------
	public double getMass() {
		return mass;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public double getOrbitalPeriod() {
		return orbitalPeriod;
	}

	public void setOrbitalPeriod(double orbitalPeriod) {
		this.orbitalPeriod = numberUtilities.nicefyDouble(orbitalPeriod);
	}

	public double getAxialTilt() {
		return axialTilt;
	}

	public void setAxialTilt(double axialTilt) {
		this.axialTilt = axialTilt;
	}

	public double getEccentricity() {
		return eccentricity;
	}

	public void setEccentricity(double eccentricity) {
		this.eccentricity = eccentricity;
	}

	public double getRotationalPeriod() {
		return rotationalPeriod;
	}

	public void setRotationalPeriod(double rotationalPeriod) {
		this.rotationalPeriod = rotationalPeriod;
	}

	public double getMagneticField() {
		return magneticField;
	}

	public void setMagneticField(double magneticField) {
		this.magneticField = numberUtilities.nicefyDouble( magneticField);
	}

	public int getBaseTemperature() {
		return baseTemperature;
	}

	public void setBaseTemperature(int baseTemperature) {
		this.baseTemperature = baseTemperature;
	}

		public double getOrbitalInclination() {
		return orbitalInclination;
	}

	public void setOrbitalInclination(double orbitalInclination) {
		this.orbitalInclination = orbitalInclination;
	}

	public String getLifeType() {
		return lifeType;
	}

	public void setLifeType(String lifeType) {
		this.lifeType = lifeType;
	}



	public void setMass(double mass) {
		this.mass = (int) mass;
		
	}



	public String getClassificationName() {
		return classificationName;
	}


}
