package com.github.hteph.Utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import com.github.hteph.Generators.StarGenerator;
import com.github.hteph.Generators.StarSystemGenerator;
import com.github.hteph.ObjectsOfAllSorts.Star;
import com.github.hteph.ObjectsOfAllSorts.StellarObject;

public class saveThisLocally {


	public static void saveThis(ArrayList<ArrayList<StellarObject>> testSystem) {
		
		try {
			FileOutputStream fos = new FileOutputStream("myGalaxy.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(testSystem);
			oos.close();
		}
			catch (IOException e) {
				e.printStackTrace();
			
			}

		}
	
	
	
	public static ArrayList<ArrayList<StellarObject>> restoreSaved(){
		
		ArrayList<ArrayList<StellarObject>> result=null;
		
		try {
		FileInputStream fis = new FileInputStream("myGalaxy.ser");
		ObjectInputStream ois = new ObjectInputStream(fis);
		result = (ArrayList<ArrayList<StellarObject>>) ois.readObject();
		ois.close();
		
		
		} catch (FileNotFoundException e) { //If the file do not exist, create it and fill it with a new generated system.
			
			StellarObject star = null;
			star = StarGenerator.Generator();
			ArrayList<StellarObject> systemList = StarSystemGenerator.Generator((Star) star);
			ArrayList<ArrayList<StellarObject>> testSystem = new ArrayList<>();

			testSystem.add(systemList);
			saveThisLocally.saveThis(testSystem);
			result=testSystem;
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return result;
		
		
	}
	
	
	
	}
