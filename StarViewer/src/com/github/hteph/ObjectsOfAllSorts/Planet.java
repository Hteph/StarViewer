package com.github.hteph.ObjectsOfAllSorts;

import java.util.ArrayList;

public class Planet extends OrbitalObjects {

	private String name;
	private String description;
	private double mass;
	private int radius;
	private double gravity;
	private double density;
	private double orbitalPeriod;
	private double axialTilt;
	private double eccentricity;
	private boolean tidelocked;
	private double rotationalPeriod;
	private double orbitDistance;

	private String tectonicCore;
	private double magneticField;
	private String hydrosphereDescription;
	private int hydrosphere;
	private ArrayList<AmosphericGases> atmoshericComposition = new ArrayList<AmosphericGases>();
	private double atmoPressure;
	private int surfaceTemp;
	private double[] rangeBandTemperature =new double[10];
	private double[] rangeBandTempSummer =new double[10];
	private double[] rangeBandTempWinter =new double[10];
	private double nightTempMod;
	private double dayTempMod;
	private String tectonicActivityGroup;
	private double orbitalInclination;
	private boolean boilingAtmo =false;

	private String lifeType;

	// Constructor ----------------------------------------------
	public Planet(String name, String description, double orbitDistance, StellarObject orbitingAround) {
		super(name, description, orbitDistance, orbitingAround);


	}
	//Methods --------------------------------------------------

	@Override
	public String toString() {
		return "Planet " + name + ": " + description + ", radius=" + radius
				+ ", hydrosphereDescription=" + hydrosphereDescription +", hydro%="+hydrosphere+ ", pressure="
				+ atmoPressure + ",\n surfaceTemp=" + surfaceTemp + ", lifeType=" + lifeType + "\n Atmo"+atmoshericComposition.toString();
	}	

	//Internal Methods ----------------------------------------

	// Getters and Setters -------------------------------------

	public double getMass() {
		return mass;
	}

	public void setMass(double mass) {
		this.mass = mass;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public double getGravity() {
		return gravity;
	}

	public void setGravity(double gravity) {
		this.gravity = gravity;
	}

	public double getDensity() {
		return density;
	}

	public void setDensity(double density) {
		this.density = density;
	}

	public double getOrbitalPeriod() {
		return orbitalPeriod;
	}

	public void setOrbitalPeriod(double orbitalPeriod) {
		this.orbitalPeriod = orbitalPeriod;
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

	public boolean isTidelocked() {
		return tidelocked;
	}

	public void setTidelocked(boolean tidelocked) {
		this.tidelocked = tidelocked;
	}

	public double getRotationalPeriod() {
		return rotationalPeriod;
	}

	public void setRotationalPeriod(double rotationalPeriod) {
		this.rotationalPeriod = rotationalPeriod;
	}

	public String getTectonicCore() {
		return tectonicCore;
	}

	public void setTectonicCore(String tectonicCore) {
		this.tectonicCore = tectonicCore;
	}

	public double getMagneticField() {
		return magneticField;
	}

	public void setMagneticField(double magneticField) {
		this.magneticField = magneticField;
	}

	public String getHydrosphereDescription() {
		return hydrosphereDescription;
	}

	public void setHydrosphereDescription(String hydrosphereDescription) {
		this.hydrosphereDescription = hydrosphereDescription;
	}

	public int getHydrosphere() {
		return hydrosphere;
	}

	public void setHydrosphere(int hydrosphere) {
		this.hydrosphere = hydrosphere;
	}

	public ArrayList<AmosphericGases> getAtmoshericComposition() {
		return atmoshericComposition;
	}

	public void setAtmoshericComposition(ArrayList<AmosphericGases> atmoshericComposition) {
		this.atmoshericComposition = atmoshericComposition;
	}

	public double getAtmoPressure() {
		return atmoPressure;
	}

	public void setAtmoPressure(double atmoPressure) {
		this.atmoPressure = atmoPressure;
	}



	public double getSurfaceTemp() {
		return surfaceTemp;
	}

	public void setSurfaceTemp(int surfaceTemp) {
		this.surfaceTemp = surfaceTemp;
	}


	public double[] getRangeBandTemperature() {
		return rangeBandTemperature;
	}

	public void setRangeBandTemperature(double[] rangeBandTemperature) {
		this.rangeBandTemperature = rangeBandTemperature;
	}

	public double[] getRangeBandTempSummer() {
		return rangeBandTempSummer;
	}

	public void setRangeBandTempSummer(double[] rangeBandTempSummer) {
		this.rangeBandTempSummer = rangeBandTempSummer;
	}

	public double[] getRangeBandTempWinter() {
		return rangeBandTempWinter;
	}

	public void setRangeBandTempWinter(double[] rangeBandTempWinter) {
		this.rangeBandTempWinter = rangeBandTempWinter;
	}

	public double getNightTempMod() {
		return nightTempMod;
	}

	public void setNightTempMod(double nightTempMod) {
		this.nightTempMod = nightTempMod;
	}

	public double getDayTempMod() {
		return dayTempMod;
	}

	public void setDayTempMod(double dayTempMod) {
		this.dayTempMod = dayTempMod;
	}



	public String getTectonicActivityGroup() {
		return tectonicActivityGroup;
	}

	public void setTectonicActivityGroup(String tectonicActivityGroup) {
		this.tectonicActivityGroup = tectonicActivityGroup;
	}

	public double getOrbitalInclination() {
		return orbitalInclination;
	}

	public void setOrbitalInclination(double orbitalInclination) {
		this.orbitalInclination = orbitalInclination;
	}

	public boolean isBoilingAtmo() {
		return boilingAtmo;
	}

	public void setBoilingAtmo(boolean boilingAtmo) {
		this.boilingAtmo = boilingAtmo;
	}


	public String getLifeType() {
		return lifeType;
	}

	public void setLifeType(String lifeType) {
		this.lifeType = lifeType;
	}

	public double getOrbitDistance() {
		return orbitDistance;
	}

	public void setOrbitDistance(double orbitDistance) {
		this.orbitDistance = orbitDistance;
	}


}
