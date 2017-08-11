package com.github.hteph.Generators;

import java.util.Arrays;
import com.github.hteph.ObjectsOfAllSorts.AsteroidBelt;
import com.github.hteph.ObjectsOfAllSorts.OrbitalObjects;
import com.github.hteph.ObjectsOfAllSorts.Star;
import com.github.hteph.ObjectsOfAllSorts.StellarObject;
import com.github.hteph.Utilities.Dice;

public final class GenerateAsteroidBelt {



	private GenerateAsteroidBelt() {
		// No instances should be created by this method
	}


	public static OrbitalObjects Generator(String name, String description, int orbitNumber, Star orbitingAround, char[] orbitalObjectBasicList, double[] orbitalDistancesArray) {
		
		double eccentricity;
		double density;
		String asterioidBeltType;
		boolean outerZone = false;

		AsteroidBelt belt = new AsteroidBelt(name, description, orbitalDistancesArray[orbitNumber], orbitingAround);

		double snowLine = 5 * Math.pow(orbitingAround.getLumosity(), 0.5);

		
		if(orbitalDistancesArray[orbitNumber]>snowLine) outerZone=true;


//Eccentricity 

		eccentricity=(Dice.d6()-1)*(Dice.d6()-1)/(100*Dice.d6());					
		belt.setEccentricity(eccentricity);

		if(!outerZone){
			density = 0.3+(Dice.d6()+Dice.d6()-2)*0.127/Math.pow(0.4+(orbitalDistancesArray[orbitNumber]/Math.pow( orbitingAround.getLumosity(),0.5)),0.67);
		}else{
			density = 0.3+(Dice.d6()+Dice.d6()-2)*0.05;
		}


		// TODO the general type and composition of the belt can be further fleshed out
		int[] typeArray = new int[] {-2,-1,6,11};
		String[] asterioidBeltTypeArray = new String[] {"Mettalic","Silicate", "Carbonaceous", "Icy"};
		double[] densityArray = new double[] {0,0.8,1,1.2};

		int densMod = Arrays.binarySearch(densityArray, density);
		if(outerZone) densMod += 6;
		if(orbitalDistancesArray[orbitNumber]<0.75*Math.sqrt(orbitingAround.getLumosity())) densMod -= 2;

		int retVal = Arrays.binarySearch(typeArray, Dice._2d6()+densMod);		

		if(retVal<0) asterioidBeltType = asterioidBeltTypeArray[-retVal-1];
		else asterioidBeltType = asterioidBeltTypeArray[retVal];

		belt.setAsterioidBeltType(asterioidBeltType);

		int[] massArray = new int[] {0, 5, 7, 9, 11};
		int massMod = orbitingAround.getAbundance();
		if(outerZone) massMod +=2;
		if(orbitingAround.getAge()>7) massMod -=1;

		//TODO +2 from multiple star system

		int massBase = Arrays.binarySearch(massArray,Dice._2d6()+massMod);

		if(massBase<0) massBase = -massBase-1;


		belt.setMass( (Dice.d10())*Math.pow(10, 4-massBase));

		double beltWitdth = orbitalDistancesArray[orbitNumber+1]-orbitalDistancesArray[orbitNumber-1];

		if(orbitalObjectBasicList[orbitNumber-1]=='j' || orbitalObjectBasicList[orbitNumber-1]=='j'
				|| orbitalObjectBasicList[orbitNumber-1]=='J' || orbitalObjectBasicList[orbitNumber-1]=='J') beltWitdth /=2;


		belt.setAsteroidBeltWidht(beltWitdth);

// and here we return the result	

		return belt;
	}
// Inner methods -------------------------------------------------------------------------------------------------	




}
