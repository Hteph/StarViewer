package com.github.hteph.Generators;

import java.util.Arrays;

import com.github.hteph.ObjectsOfAllSorts.Jovian;
import com.github.hteph.ObjectsOfAllSorts.OrbitalObjects;
import com.github.hteph.ObjectsOfAllSorts.Star;
import com.github.hteph.Utilities.Dice;

public final class JovianGenerator {

	// Constructor ----------------------------------------------	
	private JovianGenerator() {
		//No instances of this, thank you
	}

	//Methods --------------------------------------------------

	public static OrbitalObjects Generator(String name, String description, String classificationName, double orbitDistance, char orbitalObjectClass, Star orbitingAround) {

		double mass;
		int radius;
		
		double density;
		double orbitalPeriod; //in earth years
		double axialTilt;
		double eccentricity;
		
		double rotationalPeriod;

		double magneticField;
		int baseTemperature;

		boolean InnerZone = false;

		double orbitalInclination;


		String lifeType; // TODO allow for Jovian life in the future

		Jovian gasGiant = new Jovian (name, description, classificationName, orbitDistance, orbitingAround);

		double snowLine = 5 * Math.pow(orbitingAround.getLumosity(), 0.5);
		if(orbitDistance<snowLine) InnerZone=true;

// size may not be all, but here it is set

		if(orbitalObjectClass=='J') {
			mass = 250*Dice._3d6()+Dice.d10()*100;
			radius = (int) (60000+(Dice.d10()-orbitingAround.getAge()/2.0)*2000);
			gasGiant.setRadius(radius);
			gasGiant.setMass(mass);
		}
		else {
			radius = (Dice._2d6())*7000;

			if(InnerZone) density = 0.1*Dice.d10()*0.025;
			else density = 0.08*Dice.d10()*0.025;

			mass = (int) Math.pow(radius/6380,3)*density;
			gasGiant.setRadius((int)radius);
			gasGiant.setMass(mass);
		}

		orbitalPeriod = Math.pow(Math.pow(orbitDistance,3)/orbitingAround.getMass(),0.5); //in earth years
		gasGiant.setOrbitalPeriod(orbitalPeriod);

//Eccentricity and Inclination

		int eccentryMod=1;

		eccentricity=eccentryMod*(Dice.d6()-1)*(Dice.d6()-1)/(100*Dice.d6());
		axialTilt = (Dice.d6()-1)+(Dice.d6()-1)/Dice.d6();
		orbitalInclination = eccentryMod*(Dice.d6()+Dice.d6())/(1+mass/10);	

		gasGiant.setEccentricity(eccentricity);
		gasGiant.setAxialTilt(axialTilt);
		gasGiant.setOrbitalInclination(orbitalInclination);

		double tidalForce = orbitingAround.getMass()*26640000/Math.pow(orbitDistance*400,3);

		rotationalPeriod = (Dice.d6()+Dice.d6()+8)*(1+0.1*(tidalForce*orbitingAround.getAge()-Math.pow(mass, 0.5)));
		if(Dice.d6()<2) rotationalPeriod=Math.pow(rotationalPeriod,Dice.d6());

		gasGiant.setRotationalPeriod(rotationalPeriod);

//Magnetic field

		int[] magneticMassArray = {0,50,200,500};
		double[] magneticMassArrayMin = {0.1 , 0.25 , 0.5 , 1.5, 1.5 };
		double[] magneticMassArrayMax = {1 , 1.5 , 3 , 25, 25 };


		int retVal = Arrays.binarySearch(magneticMassArray, (int) mass);



		if(retVal<0) retVal=-retVal-1;



		magneticField = (magneticMassArrayMax[retVal]-magneticMassArrayMin[retVal])/10*Dice.d10();

		gasGiant.setMagneticField(magneticField);

//Temperature
		baseTemperature = (int) (255/Math.sqrt((orbitDistance/Math.sqrt(orbitingAround.getLumosity()))));

		gasGiant.setBaseTemperature(baseTemperature);



		return (OrbitalObjects) gasGiant;
	}


// Inner methods -------------------------------------------------------------------------------------------------	


}
