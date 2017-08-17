package com.github.hteph.Generators;

import java.util.ArrayList;
import java.util.Arrays;

import com.github.hteph.ObjectsOfAllSorts.OrbitalObjects;
import com.github.hteph.ObjectsOfAllSorts.Star;
import com.github.hteph.ObjectsOfAllSorts.StellarObject;
import com.github.hteph.Utilities.Dice;
import com.github.hteph.Utilities.numberUtilities;

public final class StarSystemGenerator  {


	private StarSystemGenerator() {
		
		//No instances should be made of this!
	}


	//Methods ---------------------------------------------------------
	public static ArrayList<StellarObject> Generator(Star star){
		
		double innerLimit = Math.max(0.2 * star.getMass() , 0.0088 * Math.pow(star.getLumosity(), 0.5));
		//double innerHabitable = 0.95 * Math.pow(star.getLumosity(), 0.5); Try to use without locking to goldilock theory
		//double outerHabitable = 1.3 * Math.pow(star.getLumosity(), 0.5);
		double snowLine = 5 * Math.pow(star.getLumosity(), 0.5);
		double outerLimit = 40 * star.getMass();
		ArrayList<StellarObject> starSystemList = new ArrayList<>();

		starSystemList.add(0,star);

		//how many orbits?
		int numberOfOrbits = Dice._2d6() + (int)Math.sqrt(star.getMass()-2);
			
		int maxNumberOfOrbits = Math.min(numberOfOrbits, (int)star.getMass());
		
		for(int i=0;i<maxNumberOfOrbits;i++) numberOfOrbits++;
		
		double[] orbitalDistancesArray = new double[numberOfOrbits];
			
		//set the orbit distances
		
		orbitalDistancesArray[0] =((int)(100* (0.05*Math.pow(star.getMass(),2)*(Dice.d6()+Dice.d6()))))/100;
		
		for(int i=1;i<numberOfOrbits;i++) {

				orbitalDistancesArray[i] = numberUtilities.nicefyDouble(0.1+orbitalDistancesArray[i-1]*(1.1+(Dice.d10()*0.1)));	
		}

		//populating the orbits, empty at start
		char[] orbitalObjectBasicList = new char[orbitalDistancesArray.length];		
		for(int i=0;i<numberOfOrbits;i++) orbitalObjectBasicList[i]='-';
			
		

		//Dominant Gas giant (with accompanying Asteroid belt)?
		if(Dice.d6()<6){
			for(int i=numberOfOrbits-2;i>0;i--) {
				
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
				orbitalObjectBasicList[i]='+';
				
			}else if(orbitalDistancesArray[i]<snowLine){
				if(orbitalObjectBasicList[i]=='-') orbitalObjectBasicList[i]=generateInnerObject();
			}else{
				if(orbitalObjectBasicList[i]=='-') orbitalObjectBasicList[i]=generateOuterObject();
			}

		}
		
		//Detailed bodies
		String[] romNum = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI", "XII", "XIII", "XIV", "XV"};
		int astroidBeltCounter=1;
		int objectCounter=0;
		for(int i=0;i<numberOfOrbits;i++){
			String numeral;
			
			try { //Lazy protection if there is a very large number of objects...
				numeral = " " + romNum[objectCounter];
			} catch (Exception e) {

				numeral =" XX";
			}
			
			String classificationName = "Something";
			String description = "Something, Something";
			switch (orbitalObjectBasicList[i]) {
			case 'j':
				classificationName = "Gas Giant";
				description = " A relatively small Gas Giant";
				star.setOrbitalObjects(JovianGenerator.Generator(star.getName()+" "+numeral, description, classificationName,orbitalDistancesArray[i], orbitalObjectBasicList[i], star));
				objectCounter++;
				break;
				
			case 'J':
				classificationName = "Super Jovian";
				description = "A truly massive Gas Giant, dominating the whole system";
				star.setOrbitalObjects(JovianGenerator.Generator(star.getName()+" "+numeral, description, classificationName,orbitalDistancesArray[i], orbitalObjectBasicList[i], star));
				objectCounter++;
				break;
				
			case 't': 
				classificationName = "Planetoid";
				description = "Small and nicely rounded";
				star.setOrbitalObjects(GenerateTerrestrialPlanet.Generator(star.getName()+" "+numeral, description,classificationName, orbitalDistancesArray[i], orbitalObjectBasicList[i], star));
				objectCounter++;
				break;
			case 'T':
				classificationName = "Terrestial";
				description = "Large and round";
				star.setOrbitalObjects(GenerateTerrestrialPlanet.Generator(star.getName()+" "+numeral, description,classificationName, orbitalDistancesArray[i], orbitalObjectBasicList[i], star));
				objectCounter++;
				break;
			case 'C':
				classificationName = "Catched Terrestial";
				description = "Large and round, but from not originated in this system";
				star.setOrbitalObjects(GenerateTerrestrialPlanet.Generator(star.getName()+" "+numeral, description,classificationName, orbitalDistancesArray[i], orbitalObjectBasicList[i], star));
				objectCounter++;
				break;

			case 'c': //TODO this should use a special generator to allow for strange stuff as hulks, ancient stations etc etc
				star.setOrbitalObjects(GenerateTerrestrialPlanet.Generator(star.getName()+" " +numeral, "Smaller than a planet, but not one of those asteroids, and not from here to start with","Catched object", orbitalDistancesArray[i], orbitalObjectBasicList[i], star));
				objectCounter++;
				break;

			case 'A':
				star.setOrbitalObjects(GenerateAsteroidBelt.Generator("Asterioidbelt "+astroidBeltCounter, "A bunch of blocks", i, star, orbitalObjectBasicList, orbitalDistancesArray));
				astroidBeltCounter++;
				break;

			default:
				//Do nothing (probably 'E')
				break;
			}
		}


		return starSystemList;
	}

// internal methods
	private static char generateOuterObject() {
		
		char objectType;
		char[] typeArray = {'c','A','j','E','t','T','C','J', 'E', 'E'};
		int[]  roll= {3,4,5,8,11,15,17,18};
		
		int retVal = Arrays.binarySearch(roll, Dice._3d6());		

		if(retVal<0) objectType = typeArray[-retVal-1];
		else objectType = typeArray[retVal];

		return objectType;
		
	}
	
	private static char generateInnerObject() {
	
	char objectType;
	char[] typeArray = {'A','t','T','j', 'E', 'C','J', 'E'};
	int[]  roll= {4,8,13,14,15,17,18};
	
	int retVal = Arrays.binarySearch(roll, Dice._3d6());		

	if(retVal<0) objectType = typeArray[-retVal-1];
	else objectType = typeArray[retVal];

	return objectType;

	}

}
