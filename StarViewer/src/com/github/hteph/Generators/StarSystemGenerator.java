package com.github.hteph.Generators;

import java.util.ArrayList;

import com.github.hteph.ObjectsOfAllSorts.OrbitalObjects;
import com.github.hteph.ObjectsOfAllSorts.Star;
import com.github.hteph.ObjectsOfAllSorts.StellarObject;
import com.github.hteph.Utilities.Dice;

public class StarSystemGenerator  {





	//Methods ---------------------------------------------------------
	public ArrayList<StellarObject> Generator(Star star){
		double innerLimit = Math.max(0.2 * star.getMass() , 0.0088 * Math.pow(star.getLumosity(), 0.5));
		double innerHabitable = 0.95 * Math.pow(star.getLumosity(), 0.5);
		double outerHabitable = 1.3 * Math.pow(star.getLumosity(), 0.5);
		double snowLine = 5 * Math.pow(star.getLumosity(), 0.5);
		double outerLimit = 40 * star.getMass();
		ArrayList<StellarObject> starSystemList = new ArrayList<>();

		starSystemList.add(0,star);

		//how many orbits?
		int numberOfOrbits = Dice.d6()+Dice.d6();
		for(int i=0;i<(int)star.getMass();i++) numberOfOrbits++;		
		double[] orbitalDistancesArray = new double[numberOfOrbits];

		//set the orbit distances
		for(int i=0;i<numberOfOrbits;i++) {
			if(i==0){
				orbitalDistancesArray[0] = 0.05*Math.pow(star.getMass(),2)*(Dice.d6()+Dice.d6());
			}else{
				orbitalDistancesArray[i] = 0.1+orbitalDistancesArray[i-1]*(1.1+(Dice.d6()+Dice.d6()-2));
			}
		}

		//populating the orbits, empty at start
		char[] orbitalObjectBasicList = new char[orbitalDistancesArray.length];		
		for(int i=0;i<numberOfOrbits;i++){
			orbitalObjectBasicList[i]='-';
		}

		//Dominant Gas giant (with accompanying Asteroid belt)?
		if(Dice.d6()<6){
			for(int i=numberOfOrbits;i>0;i--) {
				if(orbitalDistancesArray[i]<snowLine){
					orbitalObjectBasicList[i+1]='J';
					if(Dice.d6()<6) orbitalObjectBasicList[i]='A';
					break;
				}
			}
		}


		//General orbit content
		for(int i=0;i<numberOfOrbits;i++) {

			if(orbitalDistancesArray[i]>outerLimit || orbitalDistancesArray[i]<innerLimit) {
				// Do nothing
			}else if(orbitalDistancesArray[i]<snowLine){
				if(orbitalObjectBasicList[i]=='-') orbitalObjectBasicList[i]=generateInnerObject();
			}else{
				if(orbitalObjectBasicList[i]=='-') orbitalObjectBasicList[i]=generateOuterObject();
			}

		}

		//Detailed bodies

		for(int i=0;i<numberOfOrbits;i++){
			switch (orbitalObjectBasicList[i]) {
			case 'j':

				break;
			case 'J':

				break;
			case 't':case 'T':case 'C':
				
				starSystemList.add(GenerateTerrestrialPlanet.Generator("A Planet", "Big and round", orbitalDistancesArray[i], orbitalObjectBasicList[i], star));
				break;

			case 'c':
				break;
				
			case 'a':
				break;

			case 'A':
				starSystemList.add(GenerateAsteroidBelt.Generator("Asterioidbelt "+i, "A bunch of blocks", i, star, orbitalObjectBasicList, orbitalDistancesArray));
				break;

			default:
				//Do nothing (probably 'E')
				break;
			}
		}

		return starSystemList;
	}





	//		return orbitalObjectList;



// internal methods
	private char generateOuterObject() {

		switch (Dice._3d6()) {
		case 3:case 4:
			return 'j';// Small jovian
		case 5:	case 6:case 7:
			return 'c'; //Catched asteroid
		case 8:case 9:case 10:
			return 't';//small terrestial planet
		case 11:case 12:case 13:case 14:
			return 'T';//terrestial planet
		case 15:case 16:
			return 'A';//Asteroid belt
		case 17:
			return 'C';//Captured planet
		case 18:
			return 'J';//Super jovian
		default:
			return 'E';// Empty orbit
		}
	}

	private char generateInnerObject() {
		switch (Dice._3d6()) {
		case 3:case 4:
			return 'A'; //Asteroid belt
		case 5:	case 6:case 7:
			return 'T'; //terrestial planet
		case 8:case 9:case 10:
			return 'j'; // Small jovian
		case 11:case 12:case 13:case 14:
			return 'J'; //Super jovian
		case 15:case 16:
			return 'T'; //Terrstial planet
		case 17:
			return 'C'; //Captured planet
		case 18:
			return 'c'; // captured asteroid
		default:
			return 'E'; // Empty orbit
		}
	}

}
