package com.github.hteph.Generators;

import com.github.hteph.ObjectsOfAllSorts.Sophont;

import java.util.Arrays;

import com.github.hteph.ObjectsOfAllSorts.Planet;
import com.github.hteph.ObjectsOfAllSorts.StellarObject;
import com.github.hteph.Utilities.Dice;

public class CreatureGenerator {

	private CreatureGenerator() {
		//No instances please
	}

	public static Sophont generator(StellarObject place) {

		Sophont lifeform = new Sophont(place);

		if(place instanceof Planet) lifeform= generateBaseSophont((Planet)place);

		return lifeform;

	}
	
	
	private static Sophont generateBaseSophont(Planet place) {
		
		int[] environmetChoiceArray = {4,5,8,12,13,15,16,18}; // The new way of calculating the array. Upto values
		String[] environmentList = {"Arctic","Desert","Plains","Forest","Swamp", "Seashore/Riverside", "Mountains","Sea" };

		int testVar = Arrays.binarySearch(environmetChoiceArray, Dice._3d6());

		if(testVar<0) testVar = -testVar-1;
		
		
		
		
		
		return null;	
		
	}
	
	
	
	/*
	
	

14-15 - Seashore or riverside. (Roll 2d+1 on this chan
to detennine surrounding terrain.)
16 - Mountains. (Roll 2M I on this chan to
detennine surrounding terrain.)

An::t:ic:
A frigid wasteland. What little vegetation can survive
appears during the short growing season. Always cold, for the
purpose of these tables.
Mobility type, roll 3d - 3: amphibious, 4-6: swimmer, 7-
8: burrower, 9-17: ground dweller, 18: Flyer.
Ground dwellers only: Ice Skates (3-6).
All types have a Survival (Arctic) skill bonus +1.
Diet, roll 3d (swimmer, amphibious: -2) - 1-10: camivore,
11-13: omnivore, 14+: herbivore, 18+: ergivore.
De§er1:
Arid lands, ranging from shifting dune-seas to slightly
more hospitable scrublands dotted with sparse, hardy cover
plants. Deserts can support a surprising variety of life. Always
hot, at least for the purpose of these tables!
Roll 3d - 3-8: burrower, 9-16: ground dweller, 17-18:
flyer.
Survival (Desert) skill bonus + I (3-14), Nictitating Membrane,
one level (3-9) (see p. CI62).
Diet, roll 3d (swimmer, amphibious: -3, burrowers: +1,
flyers: -3) - 2-9: carnivore, 10-13: omnivore, 14: herbivore,
15+: ergivore.
Swamp, Bog, or Mar§h
Wetlands, either on a seashore or lake or river side. Plentiful
vegetation, many ecological niches, little in the way of
solid land. Choose or roll Id for a temperature range - I: cold,
2-5: temperate, 6: hot.
Roll 3d - 3-8: amphibious, 9-10: swimmer, 11-17:
ground dweller, 18: flyer.
Diet, roll 3d (flyers: -I) - 2-9: carnivore, 10-13: omnivore,
14-16: herbivore, 17+: ergivore.
	
	
	
	Scrapped second try
	
	
	
	

		Sophont lifeform = new Sophont(place);

		int[] environmetChoiceArray = {3,5,7,9,11,12}; // The new way of calculating the array. Upto values
		String[] environmentList = {"Desert","Tundra/Steppe","Grasslands","Forest/Jungle", "Seashore", "Unusual Environment"};

		int testVar = Arrays.binarySearch(environmetChoiceArray, Dice._2d6());

		if(testVar<0) testVar = -testVar-1;

		String environment = environmentList[testVar];
		
		if(environment.equals("Unusual Environment")){

			
			int[] environmetExoticArray = {3,5,7,9,10,11,12}; // The new way of calculating the array. Upto values
			String[] environmentExoticList = {"Ice Desert","Mountains/Cliffside","Burrower","Swamp/Bog", "Aquatic", "Ocean Trench","Geothermal Vents"};

			int testVar2 = Arrays.binarySearch(environmetExoticArray, Dice._2d6());

			if(testVar2<0) testVar = -testVar2-1;

			environment =environmentExoticList[testVar2];

		}
		
		if(environment.equals("Mountains/Cliffside")){
			int[] environmetSecondArray = {3,5,7,9}; 
			String[] secondaryEnvironmentList = {"Desert","Tundra/Steppe","Grasslands","Forest/Jungle"};
			int testVar2 = Arrays.binarySearch(environmetSecondArray, Dice.d6()+2);
			if(testVar2<0) testVar2 = -testVar-1;
			environment +=" ("+ environmentList[testVar2]+")";
			
			lifeform.addAttribute(environment, "Living in the mountaineous terrain above "+environmentList[testVar2]);
			
			int a= Dice._3d6();
			if(a<8) lifeform.addAttribute("Climber", "The body has evolved to favour a lifestyle of climbing");
			else if(a<10) lifeform.addAttribute("Jumping", "By ");


			
			//Roll 2d: 3-8=Climbing Skill, 9-10=Jumping Skill 11=Glider, 12=Winged Flight
			
		}
		
		if(environment.equals("Seashore")) {
			int[] environmetSecondArray = {3,5,7,9}; 
			String[] secondaryEnvironmentList = {"Desert","Tundra/Steppe","Grasslands","Forest/Jungle"};
			int testVar2 = Arrays.binarySearch(environmetSecondArray, Dice.d6()+2);
			if(testVar2<0) testVar2 = -testVar-1;
			environment +=" ("+ environmentList[testVar2]+")";
			
			lifeform.addAttribute(environment, "Living in the interface between the sea and land");

			int a= Dice._3d6();
			if(a<9) lifeform.addAttribute("Swimmer", "The main movement form is through liquid with limbs in form of fins");
			else if(a<11) lifeform.addAttribute("Amphibious", "Evolved to survive both in and outside of water, usually dependent of a fairly humind environment.");


		}
		
		if(environment.equals("Burrower")) {
			int[] environmetSecondArray = {3,5,7,9}; 
			String[] secondaryEnvironmentList = {"Desert","Tundra/Steppe","Grasslands","Forest/Jungle"};
			int testVar2 = Arrays.binarySearch(environmetSecondArray, Dice.d6()+2);
			if(testVar2<0) testVar2 = -testVar-1;
			environment +=" ("+ environmentList[testVar2]+")";
			
			lifeform.addAttribute(environment, "Living in burrows under "+environmentList[testVar2]);
		}		
		
		lifeform.setEnvironment(environment);//Base environment set here, now time for details
			
		if(environment.contains("Forest/Jungle")) {

			int a= Dice._3d6();
			if(a<7) lifeform.addAttribute("Brachiator", "Brachiators use their arms to move from tree branch to tree branch. Their arms are longer than their legs, and are much more powerful.");
			else if(a==11) lifeform.addAttribute("Clinging", "Physical trait that allows you crawl along walls or ceilings.");
			else if(a==12) lifeform.addAttribute("Gliding", "Descending at an angle less than 45° with lift from adapted aerofoil membranes.");
			
		}

		if(environment.contains("Ice Desert")) {
			int a= Dice._3d6();
			if(a<10) lifeform.addAttribute("Swimmer", "The main movement form is through liquid with limbs in form of fins");
			if(a==18) lifeform.addAttribute("Ice Skates", "Optimised for movement over large ice vistas by using sharp ridges for gliding and traction");
		}
		
		
		
			/*

				2  Mountains/Cliffside: Roll for surrounding terrain.
				     Roll 2d: 3-8=Climbing Skill, 9-10=Jumping Skill 11=Glider, 12=Winged Flight
				3  Burrower: Roll 1d+1 for above-ground terrain.  Species has the Tunneling
				     Ability
				4  Swamp/Bog: Surrounding terrain is Forest/Jungle or Grassland
				     Roll 2d: 3-8=Swimmer, 9-10=Amphibious
				5  Aquatic: Species has Aquatic Disadvantage. 3-6=Gills, 7-9=Breath Holding,
				     10-12=Oxygen Storage.
				6  Exotic: Roll 1d and see below
				    1 Space-Dwelling (Vacuum Support, 3-8=Doesn't Breathe, 9-12=Oxygen Storage
				       & Decreased Life Support, 3-6=G-Intolerance, Free Fall@DX+3, ST-5, HT-2)
				    2 Ocean Trench (Aquatic and Pressure Support)
				    3 Geothermal Vents (Temperature Tolerance)
				    4 Exotic Biology (Increased Life Support, Unusual Biochemistry)
				    5 Gas Giant (Increased Life Support, G-Tolerance, Pressure Support,
				       Anaerobic, 3-8=Glider, 9-12=Flight )
				    6 Really Weird (Urban, Star Dwellers, Anti-Matter, etc.)
		
		}
		
		
		
		int hydro=0;
		double gravity =0;
		String tectonics ="None";

		if(place instanceof Planet) hydro = (int) ((Planet)place).getHydrosphere();
		if(place instanceof Planet) gravity = ((Planet)place).getGravity();
		if(place instanceof Planet) tectonics = ((Planet)place).getTectonicActivityGroup();

		//Generate Environment
		//Biomass environment
		environment = biomassEnvironment();

		if(Dice.d6()<4) environment+=" Closed";
		else environment+=" Open";

		int roll = Dice._2d6();
		roll+=(int)( Math.sqrt(hydro)-4);

		if(roll>10) environment+=" Liquid";
		else if(Dice.d6()<3 && hydro>5) {

			environment+=" Liquid Interface to "+biomassEnvironment();
		}

		lifeform.setBiomassEnvironment(environment);
		// Typical forest or jungle environment
		if(environment.contains("Rich") && environment.contains("Closed") && !environment.contains("Liquid")) {
			int roll1=Dice._2d6();
			if(gravity<0.75) roll1++;
			if(roll1>8) lifeform.addAttribute("Brachiator", "Brachiators use their arms to move from tree branch to tree branch. Their arms are longer than their legs, and are much more powerful.");
			else if(roll1==11) lifeform.addAttribute("Clinging", "Physical trait that allows you crawl along walls or ceilings.");
			else if(roll1>12) lifeform.addAttribute("Gliding", "Descending at an angle less than 45° with lift from adapted aerofoil membranes.");
			else if (roll==7 && Dice.d6()<3-gravity && !tectonics.equals("Extreme")) lifeform.addAttribute("Burrowing", "A burrow is a hole or tunnel excavated into the ground by an animal to create a space suitable for habitation, temporary refuge, or as a byproduct of locomotion. ");

		}

		if(environment.contains("Liquid")) {
			if(Dice.d6()<4) {

				if(Dice.d6()<5) lifeform.addAttribute("Aquatic", "Optimised for a life underwater and struggles to survive without it");
				else lifeform.addAttribute("Amphibious", "Evolved to survive both in and outside of water, usually dependent of a fairly humind environment.");
			}
			else if(Dice.d6()<4-Math.pow(gravity,2)) lifeform.addAttribute("Flyer", "The main movement form is through liquid with limbs in form of fins");
			else if(!environment.contains("Interface")) lifeform.addAttribute("Amphibious", "Evolved to survive both in and outside of water, usually dependent of a fairly humind environment.");
		}

		if(!environment.contains("Liquid") && !environment.contains("Rich")) {

		}


		return null;

	}


	//Internal Methods --------------------------------------------------
	private static String biomassEnvironment() {
		String Environment;
		switch (Dice.d6()) {
		case 1:
			Environment ="Scarse";
			break;
		case 2:
			Environment ="Marginal";
			break;
		case 3:
			Environment ="Marginal";
			break;

		default:
			Environment ="Rich";
			break;
		}
		return Environment;
	}
*/

}
