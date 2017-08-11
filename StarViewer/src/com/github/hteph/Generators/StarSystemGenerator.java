package com.github.hteph.Generators;

import java.util.ArrayList;

import com.github.hteph.ObjectsOfAllSorts.OrbitalObjects;
import com.github.hteph.ObjectsOfAllSorts.Star;
import com.github.hteph.ObjectsOfAllSorts.StellarObject;
import com.github.hteph.Utilities.Dice;

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
		int numberOfOrbits = Dice._2d6();
		
		
		int maxNumberOfOrbits = Math.min(numberOfOrbits, (int)star.getMass());
		
		for(int i=0;i<maxNumberOfOrbits;i++) numberOfOrbits++;
		
		double[] orbitalDistancesArray = new double[numberOfOrbits];
		

		
		//set the orbit distances
		for(int i=0;i<numberOfOrbits;i++) {
			if(i==0){
				orbitalDistancesArray[0] = 0.05*Math.pow(star.getMass(),2)*(Dice.d6()+Dice.d6());
			}else{
				orbitalDistancesArray[i] = 0.1+orbitalDistancesArray[i-1]*(1.1+(Dice.d10()*0.1));
			}
		}

		//populating the orbits, empty at start
		char[] orbitalObjectBasicList = new char[orbitalDistancesArray.length];		
		for(int i=0;i<numberOfOrbits;i++){
			orbitalObjectBasicList[i]='-';
			System.out.println(orbitalDistancesArray[i]);
		}
		


		//Dominant Gas giant (with accompanying Asteroid belt)?
		if(Dice.d6()<6){
			for(int i=numberOfOrbits-1;i>0;i--) {
				
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
		
		System.out.println(orbitalObjectBasicList);
		
		
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
			
			String name = "Something";
			String description = "Something, Something";
			switch (orbitalObjectBasicList[i]) {
			case 'j':
				name = "Gas Giant";
				description = " A relatively small Gas Giant";
				starSystemList.add(JovianGenerator.Generator(name, description, orbitalDistancesArray[i], orbitalObjectBasicList[i], star));
				objectCounter++;
				break;
				
			case 'J':
				name = "Super Jovian";
				description = "A truly massive Gas Giant, dominating the whole system";
				starSystemList.add(JovianGenerator.Generator(name, description, orbitalDistancesArray[i], orbitalObjectBasicList[i], star));
				objectCounter++;
				break;
				
			case 't': 
				name = "Planetoid";
				description = "Small and nicely rounded";
				starSystemList.add(GenerateTerrestrialPlanet.Generator(name+numeral, description, orbitalDistancesArray[i], orbitalObjectBasicList[i], star));
				objectCounter++;
				break;
			case 'T':
				name = "Terrestial";
				description = "Large and round";
				starSystemList.add(GenerateTerrestrialPlanet.Generator(name+numeral, description, orbitalDistancesArray[i], orbitalObjectBasicList[i], star));
				objectCounter++;
				break;
			case 'C':
				name = "Catched Terrestial";
				description = "Large and round, but from not originated in this system";
				starSystemList.add(GenerateTerrestrialPlanet.Generator(name+numeral, description, orbitalDistancesArray[i], orbitalObjectBasicList[i], star));
				objectCounter++;
				break;

			case 'c': //TODO this should use a special generator to allow for strange stuff as hulks, ancient stations etc etc
				starSystemList.add(GenerateTerrestrialPlanet.Generator("Catched object" +numeral, "Smaller than a planet, but not one of those asteroids, and not from here to start with", orbitalDistancesArray[i], orbitalObjectBasicList[i], star));
				objectCounter++;
				break;

			case 'A':
				starSystemList.add(GenerateAsteroidBelt.Generator("Asterioidbelt "+astroidBeltCounter, "A bunch of blocks", i, star, orbitalObjectBasicList, orbitalDistancesArray));
				astroidBeltCounter++;
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
	private static char generateOuterObject() {

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

	private static char generateInnerObject() {
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
