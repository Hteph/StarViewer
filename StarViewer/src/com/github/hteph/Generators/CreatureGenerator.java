package com.github.hteph.Generators;

import com.github.hteph.ObjectsOfAllSorts.Sophont;
import com.github.hteph.ObjectsOfAllSorts.Planet;
import com.github.hteph.ObjectsOfAllSorts.StellarObject;
import com.github.hteph.Utilities.Dice;

public class CreatureGenerator {

	private CreatureGenerator() {
		//No instances please
	}

	public static Sophont generator(StellarObject place) {

		String environment;
		Sophont lifeform = new Sophont(place);
		
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
				lifeform.addAttribute("Swimmer", "The main movement form is through liquid with limbs in form of fins");
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


}
