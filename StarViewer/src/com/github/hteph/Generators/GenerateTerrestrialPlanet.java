package com.github.hteph.Generators;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import java.util.stream.DoubleStream;
import com.github.hteph.ObjectsOfAllSorts.AmosphericGases;
import com.github.hteph.ObjectsOfAllSorts.Jovian;
import com.github.hteph.ObjectsOfAllSorts.OrbitalObjects;
import com.github.hteph.ObjectsOfAllSorts.Planet;
import com.github.hteph.ObjectsOfAllSorts.Star;
import com.github.hteph.ObjectsOfAllSorts.StellarObject;
import com.github.hteph.Utilities.Dice;
import com.github.hteph.Utilities.atmoCompositionComparator;

public final class GenerateTerrestrialPlanet {


	private GenerateTerrestrialPlanet() {
		// No instances should be created by this method
	}


	public static OrbitalObjects Generator(String name, String description, String classificationName, double orbitDistance, char orbitalObjectClass, StellarObject centralObject, double lunarOrbitDistance) {

		//Last parameter (lunarOrbitDistance) is only used if this is a moon to be created
		double mass;
		int radius;
		double gravity;
		double density;
		double orbitalPeriod; //in earth years
		double axialTilt;
		double eccentricity;
		boolean tidelocked = false;
		boolean planetLocked = false;
		//boolean moonLocked = false; //Not implemented yet
		double rotationalPeriod;
		double tectonicActivity;
		String tectonicCore;
		double magneticField;
		int baseTemperature;
		String hydrosphereDescription;
		int hydrosphere;
		int waterVaporFactor;
		TreeSet<AmosphericGases> atmoshericComposition = new TreeSet<AmosphericGases>(new atmoCompositionComparator());
		double atmoPressure;
		double albedo;
		double greenhouseFactor;
		double surfaceTemp;
		String atmoModeration = "No";
		boolean InnerZone = false;
		String tectonicActivityGroup;
		double orbitalInclination;
		OrbitalObjects moonsPlanet = null;
		double moonOrbit = 0; //in planetary radii
		double moonsPlanetMass = 0;
		double lunarOrbitalPeriod = 0;
		double moonTidal;
		double moonsPlanetsRadii = 0;

		boolean hasGaia;
		String lifeType;
		Star orbitingAround;
		double tidalForce = 0;
		double tidelock;
		double sumOfLunarTidal=0;

		//Ugly hack to be able to reuse this method for generating moons
		if(orbitalObjectClass =='m') {

			if(centralObject instanceof Planet) {
				moonsPlanet = (Planet) centralObject;
				moonsPlanetMass = ((Planet)moonsPlanet).getMass();
				moonsPlanetsRadii = ((Planet)moonsPlanet).getRadius();


			}
			if(centralObject instanceof Jovian) {
				moonsPlanet = (Jovian) centralObject;
				moonsPlanetMass = ((Jovian) centralObject).getMass();
				moonsPlanetsRadii= ((Jovian) centralObject).getRadius();

			}

			orbitingAround = (Star) moonsPlanet.getOrbitingAround();
			
			orbitDistance = moonsPlanet.getOrbitalDistance();
			
		}else orbitingAround = (Star) centralObject;


		Planet planet = new Planet (name, description, classificationName, orbitDistance, orbitingAround);
		if(orbitalObjectClass =='m') planet.setLunarOrbitDistance(lunarOrbitDistance);

		double snowLine = 5 * Math.pow(orbitingAround.getLumosity(), 0.5);
		if(orbitDistance<snowLine) InnerZone=true;


		// size may not be all, but here it is set
		//TODO add greater varity for moon objects, depending on planet

		int a=900; //Default for planets

		if(orbitalObjectClass =='m') {
			switch (Dice.d10()) {
			case 1:
				a=1;
				break;
			case 2:
				a=5;
				break;
			case 3:
				a=10;
				break;
			case 4:
				a=50;
				break;
			case 5:
				a=100;
				break;
			case 6:
				a=500;
				break;

			default:a=250;
			break;
			}

			if(moonsPlanet instanceof Planet) a=(int) Math.min(a, ((Planet) moonsPlanet).getRadius()/2);
		}

		if(orbitalObjectClass=='t' ||  orbitalObjectClass=='c') a=90;


		radius = (Dice._2d6())*a;

		planet.setRadius((int)radius);


		//density

		if(orbitDistance<snowLine){
			density = 0.3+(Dice._2d6()-2)*0.127/Math.pow(0.4+(orbitDistance/Math.pow( orbitingAround.getLumosity(),0.5)),0.67);
		}else{
			density = 0.3+(Dice._2d6()-2)*0.05;
		}
		mass =Math.pow(radius/6380.0,3)*density;
		gravity = mass/Math.pow((radius/6380.0),2);

		if(orbitalObjectClass =='m') lunarOrbitalPeriod = Math.sqrt(Math.pow((moonOrbit*moonsPlanetsRadii)/400000,3)*793.64/(moonsPlanetMass+mass));
		orbitalPeriod = Math.pow(Math.pow(orbitDistance,3)/orbitingAround.getMass(),0.5); //in earth days

		planet.setMass(mass);
		planet.setDensity(density);
		planet.setGravity(gravity);
		planet.setOrbitalPeriod(orbitalPeriod);

		//Eccentricity and Inclination

		int eccentryMod=1;
		if(orbitalObjectClass=='C' || orbitalObjectClass=='c') eccentryMod +=3;

		eccentricity=eccentryMod*(Dice.d6()-1)*(Dice.d6()-1)/(100*Dice.d6());

		axialTilt = (int)(10*Dice._3d6()/2*Math.random());

		orbitalInclination = eccentryMod*(Dice._2d6())/(1+mass/10);	

		planet.setAxialTilt(axialTilt);
		planet.setOrbitalInclination(orbitalInclination);

		// TODO tidelocked or not should take into consideration moons too, generate moons here!


		if(!(orbitalObjectClass == 'm') ) createMoons(planet, InnerZone);
				
		if(planet.getLunarObjects().size()>0) {
			
			double lunartidal[]=new double[planet.getLunarObjects().size()];
						
			for(int n=0;n<planet.getLunarObjects().size();n++) {

				for(StellarObject next:planet.getLunarObjects()) {					
					
					if(next instanceof Planet) lunartidal[n] = ((Planet)next).getMass()*26640000/333000.0/Math.pow(planet.getRadius()*((Planet)next).getLunarOrbitDistance()*400/149600000,3);					
					
					((Planet)next).setLunarTidal(lunartidal[n]);
				}
			}
			sumOfLunarTidal = DoubleStream.of(lunartidal).sum();
		}


		if(orbitalObjectClass =='m') {

			moonTidal= moonsPlanetMass *26640000/333000.0/Math.pow(((Planet) moonsPlanet).getRadius()*moonOrbit*400/149600000,3);
			if(moonTidal >1) planetLocked =true;
			planet.setPlanetLocked(true);

		}else {

			tidalForce =(orbitingAround.getMass()*26640000/Math.pow(orbitDistance*400,3))/(1+sumOfLunarTidal);
			tidelock = (0.83+(Dice.d6()+Dice.d6()-2)*0.03)*tidalForce*orbitingAround.getAge()/6.6;

			if(tidelock>1) {

				tidelocked=true;
				planet.wipeMoons(); //Tidelocked planets generally can't have moon
			}
		}

		//Rotation - day/night cycle
		
		if(tidelocked){
			rotationalPeriod=orbitalPeriod*365;
		}else if(planetLocked){

			rotationalPeriod=lunarOrbitalPeriod;
		}else{
			rotationalPeriod = (Dice.d6()+Dice.d6()+8)*(1+0.1*(tidalForce*orbitingAround.getAge()-Math.pow(mass, 0.5)));
			if(Dice.d6()<2) rotationalPeriod=Math.pow(rotationalPeriod,Dice.d6());

			if(rotationalPeriod>orbitalPeriod/2.0) {

				double[] resonanceArray = {0.5,2/3.0,1,1.5,2,2.5,3,3.5};
				double[] eccentricityEffect = {0.1,0.15,0.21,0.39,0.57,0.72,0.87,0.87};

				int resultResonance = Arrays.binarySearch(resonanceArray, rotationalPeriod/orbitalPeriod);

				if(resultResonance<0) {
					eccentricity=eccentricityEffect[-resultResonance-2];
					rotationalPeriod = resonanceArray[-resultResonance-2];
				}else {
					resultResonance = (int) Math.min(resultResonance, 6); //TODO there is somethng fishy here, Edge case of greater than 0.87 relation still causes problem Should rethink methodology
					eccentricity=eccentricityEffect[resultResonance];
					rotationalPeriod = resonanceArray[-resultResonance];
				}
			}
		}

		planet.setEccentricity(eccentricity);
		planet.setRotationalPeriod(rotationalPeriod);

		//TODO tectonics should include moons!

		//TODO c type should have special treatment

		tectonicCore = findTectonicGroup(InnerZone, density);
		tectonicActivity = (5+Dice._2d6()-2)*Math.pow(mass, 0.5)/orbitingAround.getAge();
		tectonicActivity *=(1+0.5*tidalForce);
		tectonicActivityGroup = findTectonicActivityGroup(tectonicActivity);

		planet.setTectonicCore(tectonicCore);
		planet.setTectonicActivityGroup(tectonicActivityGroup);
		
		//ditching tectonicActivitynumber as it is a value of little interest further on.

		//Magnetic field
		if(tectonicCore.contains("metal")){
			magneticField =10 * 1/( Math.sqrt(( rotationalPeriod / 24.0 ))) * Math.pow(density, 2) * Math.sqrt(mass) / orbitingAround.getAge();
			if(tectonicCore.contains("small")) magneticField *=0.5;
			if(tectonicCore.contains("medium")) magneticField *=0.75;
			if(tectonicActivityGroup.equals("Dead")) magneticField = Dice.d6()/15.0;
		}else{
			magneticField = Dice.d6()/20.0;
		}

		planet.setMagneticField(magneticField);

		//Temperature
		baseTemperature = (int) (255/Math.sqrt((orbitDistance/Math.sqrt(orbitingAround.getLumosity()))));

		//base temp is an value of little use beyond this generator and is not propagated to the planet object

		//Hydrosphere
		hydrosphereDescription = findHydrosphereDescription(InnerZone, baseTemperature);
		hydrosphere = findTheHydrosphere(hydrosphereDescription, radius);
		if(hydrosphereDescription.equals("Liquid") || hydrosphereDescription.equals("Ice Sheet")){
			waterVaporFactor = Math.max(0, (baseTemperature-240)/100 * hydrosphere * (Dice._2d6()-1));
		}else{
			waterVaporFactor=0;
		}

		planet.setHydrosphereDescription(hydrosphereDescription);
		planet.setHydrosphere(hydrosphere);

		//Atmoshperic details

		atmoshericComposition = makeAtmoshpere(orbitingAround, baseTemperature, tectonicActivityGroup, radius, gravity, planet);
		atmoPressure = findAtmoPressure(tectonicActivityGroup, hydrosphere, planet.isBoilingAtmo(), mass, atmoshericComposition);

		// TODO Special considerations for c objects, this should be expanded upon when these gets more details

		if (orbitalObjectClass=='c') { //These should never had a chance to get an "real" atmosphere in the first place but may have some traces
			if(Dice.d6()<6) atmoPressure=0;
			else atmoPressure =0.001;
		}		

		double nicePressure = ((int)(atmoPressure*1000))/1000.0;

		if(nicePressure==0 && atmoPressure>0) nicePressure=0.001;

		if (nicePressure==0) atmoshericComposition.clear();
		if (atmoshericComposition.size()==0) { //There are edge cases where all of atmo has boiled away
			nicePressure = 0;
			planet.setHydrosphereDescription("Remnants");
			planet.setHydrosphere(0);
		}

		planet.setAtmoPressure(nicePressure);

		// The composition could be adjusted for the existence of life, so is set below

		//Bioshpere
		hasGaia = testLife(baseTemperature, atmoPressure, hydrosphere, atmoshericComposition);
		if(hasGaia)  lifeType = findLifeType(atmoshericComposition);
		else lifeType = "No indegious life";
		
		if(lifeType.equals("Oxygen Breathing")) adjustForOxygen(atmoPressure, atmoshericComposition);

		albedo = findAlbedo(InnerZone, atmoPressure, hydrosphere, hydrosphereDescription);
		double greenhouseGasEffect = findGreenhouseGases(atmoshericComposition, atmoPressure);

		greenhouseFactor =  1 + Math.sqrt(atmoPressure) *0.01 * (Dice.d6()+Dice.d6()-1) + Math.sqrt(greenhouseGasEffect) * 0.1 + waterVaporFactor * 0.1;

		//TODO Here adding some Gaia moderation factor (needs tweaking probably) moving a bit more towards water/carbon ideal
		if (lifeType.equals("Oxygen Breathing") && baseTemperature>350) greenhouseFactor *=0.8;
		if (lifeType.equals("Oxygen Breathing") && baseTemperature<250) greenhouseFactor *=1.2;

		// My take on the effect of greenhouse and albedo on temperature max planerary temp is 1000 and the half point is 400


		if(hasGaia ) surfaceTemp = 400*(baseTemperature*albedo*greenhouseFactor)/(350+baseTemperature*albedo*greenhouseFactor);
		else if(atmoPressure>0) surfaceTemp = 800*(baseTemperature*albedo*greenhouseFactor)/(400+baseTemperature*albedo*greenhouseFactor);
		else surfaceTemp = 1200*(baseTemperature*albedo*greenhouseFactor)/(800+baseTemperature*albedo*greenhouseFactor);;


		planet.setAtmoshericComposition(atmoshericComposition);
		planet.setLifeType(lifeType);
		planet.setSurfaceTemp((int)surfaceTemp);

		//Climate
		setAllKindOfLocalTemperature(planet, atmoPressure,hydrosphere,rotationalPeriod, 
				axialTilt, atmoModeration, surfaceTemp, orbitalPeriod); // sets all the temperature stuff from axial tilt etc etc

		//TODO Weather and day night temp cycle


		// and here we return the result	

		return (OrbitalObjects) planet;
	}





	// Inner methods -------------------------------------------------------------------------------------------------	


	private static void adjustForOxygen(double atmoPressure, TreeSet<AmosphericGases> atmoshericComposition) {

		boolean substitutionMade =false;
		ArrayList<AmosphericGases> atmoList = new ArrayList<>(atmoshericComposition);
		

		
		atmoshericComposition.clear();
		
		int oxygenMax = (int)Math.max(Dice.d6(),Math.min(100/atmoList.size(),((Dice._3d6())*2/atmoPressure)));


			for(int i=0;i<atmoList.size();i++) {
			if(atmoList.get(i).getName().equals("CO2")){
				if(atmoList.get(i).getPercentageInAtmo()<=oxygenMax){
					atmoList.add(new AmosphericGases("O2",atmoList.get(i).getPercentageInAtmo()));
					atmoList.remove(atmoList.get(i));
					substitutionMade=true;

				} else {
					atmoshericComposition.add(new AmosphericGases("O2",(int) oxygenMax));
					atmoList.get(i).setPercentageInAtmo(atmoList.get(i).getPercentageInAtmo()-oxygenMax);
					substitutionMade=true;
				}
			}			
		}

		//if CO2 didn't exists take largest and use a piece of that		
		if(!substitutionMade){
			atmoshericComposition.add(new AmosphericGases("O2",(int) oxygenMax));
			atmoshericComposition.first().setPercentageInAtmo(atmoshericComposition.first().getPercentageInAtmo()-oxygenMax);
		}
		
		
		atmoshericComposition.addAll(atmoList);
	}

	private static String findLifeType(Set<AmosphericGases> atmoshericComposition) {
		String tempLifeType = "Oxygen Breathing";
		for(AmosphericGases gas: atmoshericComposition){
			if(gas.getName().equals("NH3")) tempLifeType = "Amonnia Breathing";
			else if(gas.getName().equals("F2")) tempLifeType = "Flouride Breathing"; //TODO Should be removed?
			else if(gas.getName().equals("Cl2")) tempLifeType = "Cloride Breathing"; //TODO Implement in the same way as Oxygen
		}
		return tempLifeType;
	}

	/**
	 * Method to set the temperature of the latitude range bands and the day/night variation
	 * @param atmoPressure 
	 * @param hydrosphere 
	 * @param rotationalPeriod 
	 * @param axialTilt 
	 * @param atmoModeration 
	 * @param surfaceTemp 
	 * @param orbitalPeriod 
	 */

	/*TODO 
	 * great season variations from the orbital eccentricity and multiple stars system
	 * day night variation estimation, is interesting for worlds with short year/long days
	 */

	private static void setAllKindOfLocalTemperature(Planet planet, double atmoPressure, int hydrosphere, 
			double rotationalPeriod, double axialTilt, Object atmoModeration, double surfaceTemp, 
			double orbitalPeriod) {

		double[][] temperatureRangeBand= new double[][]{ // First is Low Moderation atmos, then Average etc
			{1.10, 1.07, 1.05, 1.03, 1.00, 0.97, 0.93, 0.87, 0.78, 0.68},
			{1.05, 1.04, 1.03, 1.02, 1.00, 0.98, 0.95, 0.90, 0.82, 0.75},
			{1.02, 1.02, 1.02, 1.01, 1.00, 0.99, 0.98, 0.95, 0.91, 0.87},
			{1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00}
		};

		double[] summerTemperature = new double[10];
		double[] winterTemperature = new double[10];
		double[] latitudeTemperature = new double[10];
		double[] baseTemperature = new double[10];
		int index = 0;
		int testModeration=0;						

		testModeration = (int) ((hydrosphere -60)/10);
		testModeration =(atmoPressure<0.1)?-3:1;
		testModeration =(int) atmoPressure;
		testModeration =(rotationalPeriod<10)?-3:1;
		testModeration =(int) (Math.sqrt(rotationalPeriod/24));
		testModeration =(int) (10/axialTilt);

		atmoModeration = (testModeration<-2)?"Low":"Average";
		atmoModeration = (testModeration>2)?"High":"Average";
		if(atmoPressure==0)atmoModeration="No";
		if(atmoPressure>10)atmoModeration="Extreme";

		if(atmoModeration.equals("High")) index =2;
		else if(atmoModeration.equals("Average")) index =1;
		else if (atmoModeration.equals("Extreme")) index = 3;

		for(int i=0;i<10;i++){
			latitudeTemperature[i] = (int)(temperatureRangeBand[index][i]*surfaceTemp);
		}

		for(int i=0;i<10;i++){
			baseTemperature[i] = latitudeTemperature[i]-274;
		}

		for(int i=0;i<10;i++){

			double seasonEffect = 1;
			// This part is supposed to shift the rangebands for summer /winter effects, it makes an
			// to me unproven assumption that winter temperatures at the poles is not changed by seasonal effects
			// this feels odd but I have to delve further into the science before I dismiss it.
			// the effect occurs from interscetion of axial tilt effects and rangeband effects in a way that
			//makes me suspect it is unintentional.
			int axialTiltEffect = (int) (axialTilt/10);
			int summer = Math.max(0, i-axialTiltEffect);
			int winter =Math.min(9, i+axialTiltEffect);

			if(i<3 && axialTiltEffect<4) seasonEffect *= 0.75;
			if(i>8 && axialTiltEffect>3) seasonEffect *= 2;
			if(orbitalPeriod<0.25 && !atmoModeration.equals("Low"))	seasonEffect *= 0.75;
			if(orbitalPeriod>3 && !atmoModeration.equals("High") && axialTilt>40)	seasonEffect *= 1.5;

			summerTemperature[i]=(int)(latitudeTemperature[summer]-latitudeTemperature[i])*seasonEffect;
			winterTemperature[i]=(int)(latitudeTemperature[winter]-latitudeTemperature[i])*seasonEffect;

		}

		planet.setRangeBandTemperature(baseTemperature);
		planet.setRangeBandTempSummer(summerTemperature);
		planet.setRangeBandTempWinter(winterTemperature);

	}
	/*TODO
	 * This should be reworked (in conjuction with atmo) to remove CL and F from naturally occuring and instead
	 * treat them similar to Oxygen. Also the Ammonia is dependent on free water as written right now
	 */
	private static boolean testLife(int baseTemperature, double atmoPressure, int hydrosphere, Set<AmosphericGases> atmoshericComposition) {


		double lifeIndex = 0;
		if(baseTemperature<100 || baseTemperature>450) {
			lifeIndex -= 5;
		}else if(baseTemperature<250 || baseTemperature>350) {
			lifeIndex -= 1;
		} else {
			lifeIndex +=3;
		}


		if (atmoPressure<0.1){
			lifeIndex -=10;
		}else if((atmoPressure>5)){
			lifeIndex -=1;
		}

		if (hydrosphere<1){
			lifeIndex -=3;
		}else if((hydrosphere>3)){
			lifeIndex +=1;
		}

		// This needs to be redone, remove Cl and F2 from occuring gases and instead add them similar to Oxygen

		for(AmosphericGases gas: atmoshericComposition) { //TODO göras om till en stream?

			if(gas.getName().equals("NH3") && Dice.d6()<3) lifeIndex +=1;
			else if(gas.getName().equals("CL2")&& Dice.d6()<3) lifeIndex +=3; //TODO Cl2 should be treated like oxygen instead.
			else if(gas.getName().equals("F2")&& Dice.d6()<3) lifeIndex +=3; //TODO should probably be removed alltogheter

		}

		return (lifeIndex<1)?false:true;
	}

	private static double findGreenhouseGases(Set<AmosphericGases> atmoshericComposition, double atmoPressure) {
		double tempGreenhouseGasEffect = 0;

		for(AmosphericGases gas: atmoshericComposition){
			if(gas.getName().equals("CO2")) tempGreenhouseGasEffect += gas.getPercentageInAtmo()*atmoPressure;
			if(gas.getName().equals("CH4")) tempGreenhouseGasEffect += gas.getPercentageInAtmo()*atmoPressure*4;
			if(gas.getName().equals("SO2")) tempGreenhouseGasEffect += gas.getPercentageInAtmo()*atmoPressure*8;
			if(gas.getName().equals("H2S")) tempGreenhouseGasEffect += gas.getPercentageInAtmo()*atmoPressure*8;
			if(gas.getName().equals("H2SO4")) tempGreenhouseGasEffect += gas.getPercentageInAtmo()*atmoPressure*16;
			if(gas.getName().equals("NO2")) tempGreenhouseGasEffect += gas.getPercentageInAtmo()*atmoPressure*8;
			if(gas.getName().equals("NH3")) tempGreenhouseGasEffect += gas.getPercentageInAtmo()*atmoPressure*8;
		}
		return tempGreenhouseGasEffect;
	}


	private static double findAlbedo(boolean InnerZone, double atmoPressure, int hydrosphere, Object hydrosphereDescription) {
		double tempAlbedo=1;
		int randAlbedo=Dice.d6()+Dice.d6();

		if(InnerZone){
			int mod = 0;
			if(atmoPressure<0.05) mod = 2;
			if(atmoPressure>50){
				mod = -4;
			}else if(atmoPressure>5){
				mod = -2;
			}
			if(hydrosphere>50 && hydrosphereDescription.equals("Ice Sheet") && mod>-2) mod=-2;
			if(hydrosphere>90 && hydrosphereDescription.equals("Ice Sheet") && mod>-4) mod=-4;

			randAlbedo +=mod;
			if(randAlbedo<2){
				tempAlbedo = 0.75+(Dice._2d6()-2)*0.01;


			}else if(randAlbedo<4){
				tempAlbedo = 0.85+(Dice._2d6()-2)*0.01;
			}else if(randAlbedo<7){
				tempAlbedo = 0.95+(Dice._2d6()-2)*0.01;
			}else if(randAlbedo<10){
				tempAlbedo = 1.05+(Dice._2d6()-2)*0.01;
			}else{
				tempAlbedo = 1.15+(Dice._2d6()-2)*0.01;
			}

		}else{
			int mod = 0;
			if(atmoPressure>1) mod = 1;

			randAlbedo +=mod;
			if(randAlbedo<4){
				tempAlbedo = 0.75+(Dice._2d6()-2)*0.01;
			}else if(randAlbedo<6){
				tempAlbedo = 0.85+(Dice._2d6()-2)*0.01;
			}else if(randAlbedo<8){
				tempAlbedo = 0.95+(Dice._2d6()-2)*0.01;
			}else if(randAlbedo<10){
				tempAlbedo = 1.05+(Dice._2d6()-2)*0.01;
			}else{
				tempAlbedo = 1.15+(Dice._2d6()-2)*0.01;
			}
		}
		return tempAlbedo;
	}

	private static double findAtmoPressure(String tectonicActivityGroup, int hydrosphere, boolean boilingAtmo, double mass, Set<AmosphericGases> atmoshericComposition) {

		// TODO redo this with a better algorithm, binary search and so on so on
		double pressure;
		int mod=0;
		if(tectonicActivityGroup.equals("Dead")) mod -=1;
		if(tectonicActivityGroup.equals("Extreme")) mod +=2;
		if(hydrosphere>0) mod +=1;
		if(boilingAtmo) mod -=1;

		switch (Dice._2d6()+mod) {
		case 2:
			pressure = (Dice._2d6())*0.005;
			break;
		case 3:
			pressure = (Dice._2d6())*0.01;
			break;
		case 4:case 5:
			pressure = (Dice._2d6())*0.05;
			break;
		case 6:case 7:
			pressure = (Dice._2d6())*0.1;
			break;
		case 8:case 9:case 10:
			pressure = (Dice._2d6())*0.2;
			break;
		case 11:
			pressure = (Dice._2d6())*0.5;			
			break;
		case 12:
			pressure = (Dice._2d6())*1;
			break;
		case 13:
			pressure = (Dice._2d6())*3;			
			break;
		case 14:
			pressure = (Dice._2d6())*5;			
			break;
		default:
			pressure =(Dice._2d6())*0.001;
			break;
		}
		if(atmoshericComposition.isEmpty()) pressure=0;
		pressure *= mass;
		return pressure;
	}

	private static TreeSet<AmosphericGases> makeAtmoshpere(Star star, int baseTemperature, String tectonicActivityGroup, double radius, double gravity, Planet planet) {

		Set<String> makeAtmoshpere =new TreeSet<String>();
		TreeSet<AmosphericGases> atmoArray = new TreeSet<AmosphericGases>(new atmoCompositionComparator());

		if (baseTemperature>400){
			switch (Dice.d6()+Dice.d6()) {
			case 2:
				makeAtmoshpere.add("N2");
				if(Dice.d6()<4) makeAtmoshpere.add("CO2");
				if(Dice.d6()<4) makeAtmoshpere.add("NO2");
				if(Dice.d6()<4) makeAtmoshpere.add("SO2");
				if(Dice.d6()<2) makeAtmoshpere.add("Cl2"); //TODO remove to oxygen check
				if(Dice.d6()<2) makeAtmoshpere.add("F2");
				if(Dice.d6()<2) makeAtmoshpere.add("Ne");
				if(Dice.d6()<2) makeAtmoshpere.add("Ar");
				if(tectonicActivityGroup== "Extreme"){
					makeAtmoshpere.add("SO2");
					makeAtmoshpere.add("H2S");
				}

				break;
			case 3:
				makeAtmoshpere.add("CO2");
				break;
			case 4:case 5:
				makeAtmoshpere.add("H2O");//Obs just adding water to below
			case 6:case 7:case 8:
				makeAtmoshpere.add("N2");
				makeAtmoshpere.add("CO2");
				break;
			case 9:case 10:
				makeAtmoshpere.add("NO2");
				makeAtmoshpere.add("SO2");
				break;
			case 11:case 12:
				makeAtmoshpere.add("SO2");
				break;
			default:
				makeAtmoshpere.add("N2");
				break;
			};
		}else if(baseTemperature>240){
			switch (Dice.d6()+Dice.d6()) {
			case 2:
				makeAtmoshpere.add("N2");
				if(Dice.d6()<4) makeAtmoshpere.add("CO2");
				if(Dice.d6()<4) makeAtmoshpere.add("NO2");
				if(Dice.d6()<4) makeAtmoshpere.add("SO2");
				if(Dice.d6()<2) makeAtmoshpere.add("Cl2"); //TODO remove to oxygen check
				if(Dice.d6()<2) makeAtmoshpere.add("F2"); //TODO remove to oxygen check
				if(Dice.d6()<2) makeAtmoshpere.add("Ne");
				if(Dice.d6()<2) makeAtmoshpere.add("Ar");
				if(Dice.d6()<2) makeAtmoshpere.add("He");
				if(Dice.d6()<2) makeAtmoshpere.add("NH3");

				break;
			case 3:
				makeAtmoshpere.add("CO2");
				break;
			case 4:case 5:
				makeAtmoshpere.add("H2O");//Obs just adding water to below
			case 6:case 7:case 8:
				makeAtmoshpere.add("N2");
				makeAtmoshpere.add("CO2");
				break;
			case 9:case 10:
				makeAtmoshpere.add("N2");
				makeAtmoshpere.add("CH4");
				break;
			case 11:case 12:
				makeAtmoshpere.add("CO2");
				makeAtmoshpere.add("CH4");
				makeAtmoshpere.add("NH3");
				break;
			default:
				makeAtmoshpere.add("N2");
				break;
			};
		}else if(baseTemperature>150){
			switch (Dice.d6()+Dice.d6()) {
			case 2:
				makeAtmoshpere.add("N2");
				if(Dice.d6()<4) makeAtmoshpere.add("CO2");
				if(Dice.d6()<4) makeAtmoshpere.add("NO2");
				if(Dice.d6()<4) makeAtmoshpere.add("SO2");
				if(Dice.d6()<2) makeAtmoshpere.add("Cl2");	//TODO remove to oxygen check
				if(Dice.d6()<2) makeAtmoshpere.add("F2");	//TODO remove to oxygen check
				if(Dice.d6()<2) makeAtmoshpere.add("Ne");
				if(Dice.d6()<2) makeAtmoshpere.add("Ar");
				if(Dice.d6()<2) makeAtmoshpere.add("He");
				if(Dice.d6()<2) makeAtmoshpere.add("H2");
				if(Dice.d6()<2) makeAtmoshpere.add("NH3");

				break;
			case 3:
				makeAtmoshpere.add("CO2");
				break;


			case 4:case 5:case 6:case 7:case 8:
				makeAtmoshpere.add("N2");
				makeAtmoshpere.add("CO2");
				break;
			case 9:case 10:
				makeAtmoshpere.add("N2");
				makeAtmoshpere.add("CH4");
				break;
			case 11:case 12:
				makeAtmoshpere.add("H2");
				makeAtmoshpere.add("He");
				break;
			default:
				makeAtmoshpere.add("N2");
				break;
			};
		}else if(baseTemperature>50){
			switch (Dice.d6()+Dice.d6()) {
			case 2:
				makeAtmoshpere.add("N2");
				if(Dice.d6()<4) makeAtmoshpere.add("CO2");
				if(Dice.d6()<4) makeAtmoshpere.add("NO2");
				if(Dice.d6()<4) makeAtmoshpere.add("SO2");
				if(Dice.d6()<2) makeAtmoshpere.add("Cl2");	//TODO remove to oxygen check
				if(Dice.d6()<2) makeAtmoshpere.add("F2"); //TODO remove to oxygen check
				if(Dice.d6()<2) makeAtmoshpere.add("Ne");
				if(Dice.d6()<2) makeAtmoshpere.add("Ar");
				if(Dice.d6()<2) makeAtmoshpere.add("He");
				if(Dice.d6()<2) makeAtmoshpere.add("H2");
				if(Dice.d6()<2) makeAtmoshpere.add("CO");
				if(Dice.d6()<2) makeAtmoshpere.add("NH3");


				break;
			case 3:case 4:case 5:
				makeAtmoshpere.add("H2");
				makeAtmoshpere.add("He");;
				break;
			case 6:case 7:case 8:
				makeAtmoshpere.add("H2");
				makeAtmoshpere.add("He");
				makeAtmoshpere.add("N2");
				break;
			case 9:case 10:
				makeAtmoshpere.add("N2");
				makeAtmoshpere.add("CH4");
				break;
			case 11:case 12:
				makeAtmoshpere.add("N2");
				makeAtmoshpere.add("CO");
				break;
			default:
				makeAtmoshpere.add("N2");
				break;
			};
		}else{
			switch (Dice.d6()+Dice.d6()) {
			case 2:
				makeAtmoshpere.add("N2");
				if(Dice.d6()<4) makeAtmoshpere.add("CO2");
				if(Dice.d6()<4) makeAtmoshpere.add("NO2");
				if(Dice.d6()<4) makeAtmoshpere.add("SO2");
				if(Dice.d6()<2) makeAtmoshpere.add("Cl2");	//TODO remove to oxygen check
				if(Dice.d6()<2) makeAtmoshpere.add("F2");	//TODO remove to oxygen check
				if(Dice.d6()<2) makeAtmoshpere.add("Ne");
				if(Dice.d6()<2) makeAtmoshpere.add("Ar");
				if(Dice.d6()<2) makeAtmoshpere.add("He");
				if(Dice.d6()<2) makeAtmoshpere.add("H2");
				if(Dice.d6()<2) makeAtmoshpere.add("NH3");

				break;
			case 3:case 4:case 5:
				makeAtmoshpere.add("Ne");
				makeAtmoshpere.add("N2");
				break;
			case 6:case 7:case 8:
				makeAtmoshpere.add("He");
				makeAtmoshpere.add("H2");
				break;
			case 9:case 10:
				makeAtmoshpere.add("H2");
				makeAtmoshpere.add("N2");
				break;
			case 11:case 12:
				makeAtmoshpere.add("He");
				break;
			default:
				makeAtmoshpere.add("N2");
				break;
			};
		}

		if(tectonicActivityGroup== "Extreme" && Dice.d6()<3){
			makeAtmoshpere.add("SO2");
			makeAtmoshpere.add("H2S");
			if(Dice.d6()<2) makeAtmoshpere.add("H2SO4");
		}

		double retainedGases = 0.02783 * baseTemperature / Math.pow(Math.pow((19600 * gravity * radius),0.5) /11200, 2);

		boolean boilingAtmo=false;

		if(retainedGases>2) boilingAtmo=makeAtmoshpere.remove("H2");
		if(retainedGases>4) boilingAtmo=makeAtmoshpere.remove("He");
		if(retainedGases>16) boilingAtmo=makeAtmoshpere.remove("CH4");
		if(retainedGases>17) boilingAtmo=makeAtmoshpere.remove("NH3");
		if(retainedGases>17) boilingAtmo=makeAtmoshpere.remove("H2O");
		if(retainedGases>20) boilingAtmo=makeAtmoshpere.remove("Ne");
		if(retainedGases>28) boilingAtmo=makeAtmoshpere.remove("N2");
		if(retainedGases>28) boilingAtmo=makeAtmoshpere.remove("CO");
		if(retainedGases>30) boilingAtmo=makeAtmoshpere.remove("NO");
		if(retainedGases>34) boilingAtmo=makeAtmoshpere.remove("H2S");
		if(retainedGases>38) boilingAtmo=makeAtmoshpere.remove("F2");
		if(retainedGases>40) boilingAtmo=makeAtmoshpere.remove("Ar");
		if(retainedGases>44) boilingAtmo=makeAtmoshpere.remove("CO2");
		if(retainedGases>46) boilingAtmo=makeAtmoshpere.remove("NO2");
		if(retainedGases>62) boilingAtmo=makeAtmoshpere.remove("SO2");
		if(retainedGases>70) boilingAtmo=makeAtmoshpere.remove("Cl2");
		if(retainedGases>98) boilingAtmo=makeAtmoshpere.remove("H2SO4");

		planet.setBoilingAtmo(boilingAtmo);

		if((star.getClassification().contains("A") || star.getClassification().contains("B")) && baseTemperature>150) {
			makeAtmoshpere.remove("H2O");
			makeAtmoshpere.remove("NH3");
			makeAtmoshpere.remove("CH4");
			makeAtmoshpere.remove("H2S");
		}
		if(star.getClassification().contains("F") && baseTemperature>180) {
			makeAtmoshpere.remove("H2O");
			makeAtmoshpere.remove("NH3");
			makeAtmoshpere.remove("CH4");
			makeAtmoshpere.remove("H2S");
		}
		if(star.getClassification().contains("G") && baseTemperature>230) {
			makeAtmoshpere.remove("H2O");
			makeAtmoshpere.remove("NH3");
			makeAtmoshpere.remove("CH4");
			makeAtmoshpere.remove("H2S");
		}
		if(star.getClassification().contains("K") && baseTemperature>250) {
			makeAtmoshpere.remove("H2O");
			makeAtmoshpere.remove("NH3");
			makeAtmoshpere.remove("CH4");
			makeAtmoshpere.remove("H2S");
		}
		if((star.getClassification().contains("M") ) && baseTemperature>270) {
			makeAtmoshpere.remove("H2O");
			makeAtmoshpere.remove("NH3");
			makeAtmoshpere.remove("CH4");
			makeAtmoshpere.remove("H2S");
		}

		if(!makeAtmoshpere.isEmpty()){

			int percentage =100;
			int[] part = new int[makeAtmoshpere.size()];

			part[0] = (5*(Dice._2d6())+30); //size of primary gas

			percentage = 100-part[0];

			
			for(int i=1;i<part.length;i++){
				part[i]=percentage/2;
				percentage -=part[i];
			}

			part[0] +=percentage; //whats left is added to primary gas

			ArrayList<String> randGas = new ArrayList<>();

			randGas.addAll(makeAtmoshpere);

			Collections.shuffle(randGas);

			for(int i = 0; i<part.length;i++){
				atmoArray.add(new AmosphericGases(randGas.get(i), part[i]));	
			}
		}
		
		
		
		return atmoArray;
	}

	private static int findTheHydrosphere(Object hydrosphereDescription, int radius) {
		int tempHydro=0;
		if(hydrosphereDescription.equals("Liquid") || hydrosphereDescription.equals("Ice Sheet")){
			if(radius<2000){
				switch (Dice._2d6()) {
				case 2:case 3:case 4:case 5:case 6:
					tempHydro = 0;
					break;
				case 7:
					tempHydro = Dice._2d6()-1;
					break;
				case 8:case 9:
					tempHydro = Dice._2d6()+10;
					break;
				case 10:case 11:
					tempHydro = 2 * (Dice._2d6())+25;
					break;
				case 12:
					tempHydro = 5 * (Dice._2d6())+40;
					break;
				default:
					tempHydro = 0;
					break;
				}

			}else if(radius<4000){
				switch (Dice._2d6()) {
				case 2:
					tempHydro = Dice.d6();
					break;
				case 3:case 4:
					tempHydro = Dice._2d6()-1;
					break;
				case 5:case 6:
					tempHydro = Dice._2d6()+10;
					break;
				case 7:case 8:case 9:
					tempHydro = 2 * (Dice._2d6())+25;
					break;
				case 10:case 11:case 12:
					tempHydro = 4 * (Dice._2d6())+40;
					break;
				default:
					tempHydro = 0;
					break;
				}

			}else if(radius<7000){
				switch (Dice._2d6()) {
				case 2:
					tempHydro = Dice.d6();
					break;
				case 3:
					tempHydro = Dice._2d6()-1;
					break;
				case 4:case 5:
					tempHydro = Dice._2d6()+10;
					break;
				case 6:case 7:
					tempHydro = 2 * (Dice._2d6())+25;
					break;
				case 8:case 9:case 10:case 11:
					tempHydro = 4 * (Dice._2d6())+40;
					break;
				case 12:
					tempHydro = 100;
					break;
				default:
					tempHydro = 0;
					break;
				}
			}else{
				switch (Dice._2d6()) {
				case 2:
					tempHydro = Dice.d6();
					break;
				case 3:
					tempHydro = Dice._2d6()-1;
					break;
				case 4:
					tempHydro = Dice._2d6()+10;
					break;
				case 5:
					tempHydro = 2 * (Dice._2d6())+25;
					break;
				case 6:case 7:case 8:case 9:
					tempHydro = 4 * (Dice._2d6())+40;
					break;
				case 10:case 11:case 12:
					tempHydro = 100;
					break;
				default:
					tempHydro = 0;
					break;
				}
			}
		}
		tempHydro = Math.min(100, tempHydro);

		return tempHydro;
	}

	private static String findHydrosphereDescription(boolean InnerZone, int baseTemperature) {
		String tempHydroD;
		if (!InnerZone){
			tempHydroD="Crustal";
		}else if(baseTemperature>500){
			tempHydroD ="None";
		}else if(baseTemperature>370){
			tempHydroD ="Vapor";
		}else if(baseTemperature>245){
			tempHydroD ="Liquid";
		}else{
			tempHydroD ="Ice Sheet";
		}
		return tempHydroD;
	}

	private static String findTectonicActivityGroup(double tectonicActivity) {

		String tempTectonicActivityGroup;

		if(tectonicActivity<0.5){
			tempTectonicActivityGroup = "Dead";
		}else if(tectonicActivity<1){

			switch (Dice.d6()+Dice.d6()) {
			case 2:case 3:case 4:case 5:case 6:case 7:case 8:
				tempTectonicActivityGroup = "Dead";
				break;
			case 9:case 10:case 11:
				tempTectonicActivityGroup = "Hot Spot";
				break;
			case 12:
				tempTectonicActivityGroup = "Plastic";
				break;


			default:
				tempTectonicActivityGroup = "Dead";
				break;
			}
		}else if(tectonicActivity<2){
			switch (Dice.d6()+Dice.d6()) {
			case 2:case 3:case 4:
				tempTectonicActivityGroup = "Dead";
				break;
			case 5:case 6:case 7:
				tempTectonicActivityGroup = "Hot Spot";
				break;
			case 8:case 9:
				tempTectonicActivityGroup = "Plastic";
				break;
			case 10:case 11:case 12:
				tempTectonicActivityGroup = "Plate Tectonics";
				break;
			default:
				tempTectonicActivityGroup = "Plastic";
				break;
			}	

		}else if(tectonicActivity<3){
			switch (Dice.d6()+Dice.d6()) {
			case 2:case 3:case 4:
				tempTectonicActivityGroup = "Hot Spot";
				break;
			case 5:case 6:case 7:
				tempTectonicActivityGroup = "Plastic";
				break;
			case 8:case 9:case 10:case 11:case 12:
				tempTectonicActivityGroup = "Plate Tectonics";
				break;
			default:
				tempTectonicActivityGroup = "Plate Tectonics";
				break;
			}	

		}else if(tectonicActivity<3){
			switch (Dice.d6()+Dice.d6()) {
			case 2:case 3:case 4:
				tempTectonicActivityGroup = "Hot Spot";
				break;
			case 5:case 6:case 7:
				tempTectonicActivityGroup = "Plastic";
				break;
			case 8:case 9:case 10:case 11:case 12:
				tempTectonicActivityGroup = "Plate Tectonics";
				break;
			default:
				tempTectonicActivityGroup = "Plate Tectonics";
				break;
			}	

		}else if(tectonicActivity<5){
			switch (Dice.d6()+Dice.d6()) {
			case 2:case 3:
				tempTectonicActivityGroup = "Hot Spot";
				break;
			case 4:case 5:
				tempTectonicActivityGroup = "Plastic";
				break;
			case 6:case 7:case 8:case 9:
				tempTectonicActivityGroup = "Plate Tectonics";
				break;
			case 10:case 11:case 12:
				tempTectonicActivityGroup = "Platelet Tectonics";
				break;
			default:
				tempTectonicActivityGroup = "Plate Tectonics";
				break;
			}
		}else{
			switch (Dice.d6()+Dice.d6()) {
			case 2:case 3:
				tempTectonicActivityGroup = "Plastic";
				break;
			case 4:case 5:
				tempTectonicActivityGroup = "Plate Tectonics";
				break;
			case 6:case 7:case 8:case 9:
				tempTectonicActivityGroup = "Platelet Tectonic";
				break;
			case 10:case 11:case 12:
				tempTectonicActivityGroup = "Extreme";
				break;
			default:
				tempTectonicActivityGroup = "Platelet Tectonics";
				break;
			}
		}
		return tempTectonicActivityGroup;
	}

	private static String findTectonicGroup(boolean InnerZone, double density) {

		String tempTectonics;
		if(InnerZone){
			if(density<0.7){
				if(Dice.d6()<4){
					tempTectonics = "Silicates core";
				}else{
					tempTectonics = "Silicates, small metal core";
				}
			}
			if(density<1){
				tempTectonics = "Iron-nickel, medium metal core";
			}else{
				tempTectonics = "Iron-nickel, large metal core";
			}
		}else{
			if(density<0.3){
				tempTectonics = "Ice core";
			}
			if(density<1){
				tempTectonics = "Silicate core";
			}else{
				if(Dice.d6()<4){
					tempTectonics = "Silicates core";
				}else{
					tempTectonics = "Silicates, small metal core";
				}
			}	
		}
		return tempTectonics;
	}
	
	
	private static void createMoons(Planet planet, boolean innerZone) {
		char[] letters = {'a','b','c','d','e','f','g','h', 'i','j','k'};
		int number=0;		
		if(!innerZone)  number =Math.max(0,(Dice._2d6()-5)/2);
		else number =(int) Math.max(0,(Dice._2d6()-2)/1.5);
		
		if(number==0) return; //If there is no moons, bail out of this
		
		double[] lunarOrbitDistance = new double[number];
				
		lunarOrbitDistance[0]=3+150/number*(Dice._2d6()-2)/10; //in planet Radii
		
		
		for(int i=1;i<number;i++) {
			lunarOrbitDistance[i]=lunarOrbitDistance[i-1]+1+150/number*(Dice._2d6()-2)/10;
		}
		
		System.out.println(planet.getName());
		
		for(int i=0;i<number;i++) {
		Planet moon=(Planet) GenerateTerrestrialPlanet.Generator(planet.getName()+letters[i], "A moon","lunar object", planet.getOrbitalDistance(),'m', planet, lunarOrbitDistance[i]);
		
		// TODO here a roche limit check should be made!!
		
		planet.addLunarObjects(moon);
		}

	}

}
