package com.github.hteph.Generators;

import com.github.hteph.ObjectsOfAllSorts.Star;
import com.github.hteph.ObjectsOfAllSorts.StellarObject;
import com.github.hteph.Utilities.Dice;
import com.github.hteph.Utilities.NameGenerator;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;

public final class StarGenerator {

	private StarGenerator() {
		//No instances should be created of this method
	}

	//Method --------------------------------------------------------------------
	public static  StellarObject Generator() throws IOException{

		double lumosity;
		double mass;
		double diameter;
		String classification;
		double age; // in billion of earth years
		int temperature;	
		NameGenerator randomName = null;
		
		int testDice =Dice._3d6()-3;
		
		double randN =testDice/(15.0+Math.random()/10); //turning the dice roll into a continous sligthly skewed randomnumber.


		mass = 0.045/(0.001+Math.pow(randN,5)); // <-----------------------------------------MOST IMPORTANT STARTING POINT
		diameter= Math.pow(mass,2/3.0);
		temperature = 100*(int)((Math.round((500+4800*Math.pow(mass, 0.5)))*(0.8+Math.random()*0.4))/100);	
		lumosity = Math.pow(mass, 3.5);
		double inverseMass=1/mass;
		double maxAge =10*Math.pow(inverseMass, 2.5);
		age = (0.3+Math.random()*0.6)*Math.min(maxAge,13);
		double halfAgeBalance=2*age/maxAge;
		lumosity *= Math.pow(halfAgeBalance, 0.5);
		diameter *= Math.pow(halfAgeBalance, 1/3.0);


		classification = findStarClass(temperature);

		DecimalFormat df = new DecimalFormat("#.####");


		int abundance;
		int[] abundanceArray = new int[] {0,10,13,19,22};
		int retVal = Arrays.binarySearch(abundanceArray, (int) (Dice.d10()+Dice.d10()+age));

		if(retVal<0) abundance = 2-retVal+1;
		else abundance = 2-retVal;


			randomName = new NameGenerator("RomanNames");
	
			int randomNummer = 2+Dice.d6()/2;
		
		StellarObject star = new Star(randomName.compose(randomNummer), "Standard text", lumosity, mass, diameter, classification, age, abundance);
		return star;
	}

//Inner Methods	-------------------------------------------------------------------------------
	private static String findStarClass(int temperature){

		String[] classLetter ={"Y","T","M","K","G","F","A","B","O"};
		int[] temperatureClass = {500,1300,2400,3700,5200,6000,7500,10000,30000,100000};
		int decimal;
		String classification;


		
		int retValue =  Arrays.binarySearch(temperatureClass,temperature);

		if(retValue<0) decimal =Math.min(9, 10- (int) (10.0*(temperature-temperatureClass[-retValue-2])/(1.0*temperatureClass[-retValue-1]-temperatureClass[-retValue-2])));
		else decimal = 0;

		if(retValue<0) classification = classLetter[-retValue-2]+decimal+" V";
		else classification = classLetter[retValue]+decimal+" V";


		return classification;



	}

}

