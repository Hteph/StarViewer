package com.github.hteph.MainModules;

import com.github.hteph.Generators.GenerateTerrestrialPlanet;
import com.github.hteph.Generators.StarGenerator;
import com.github.hteph.ObjectsOfAllSorts.Planet;
import com.github.hteph.ObjectsOfAllSorts.Star;
import com.github.hteph.ObjectsOfAllSorts.StellarObject;

public class MainConnectorOfStuff {

	public static void main(String[] args) {
		
		
		
		
		
			
		StellarObject testStar = StarGenerator.Generator();
		
		Planet testPlanet = (Planet) GenerateTerrestrialPlanet.Generator("Testus", "SomethingSomething", 1, 'J', (Star) testStar);
		
		System.out.println(testStar.toString());
		System.out.println(testPlanet.toString());

	}

}
