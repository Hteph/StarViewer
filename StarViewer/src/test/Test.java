package test;

import com.github.hteph.Generators.GenerateAsteroidBelt;
import com.github.hteph.Generators.StarGenerator;
import com.github.hteph.ObjectsOfAllSorts.AsteroidBelt;
import com.github.hteph.ObjectsOfAllSorts.Star;
import com.github.hteph.ObjectsOfAllSorts.StellarObject;

public class Test {

	public static void main(String[] args) {


		StellarObject star = StarGenerator.Generator();
		
		char[] orbitalObjectBasicList = {'A','t'};
		
		double[] orbitalDistancesArray = {1.0,2.0};
		
		AsteroidBelt belt = (AsteroidBelt) GenerateAsteroidBelt.Generator("Test", "Test", 1, (Star) star, orbitalObjectBasicList, orbitalDistancesArray);

		System.out.println(belt.toString());

	}

}
