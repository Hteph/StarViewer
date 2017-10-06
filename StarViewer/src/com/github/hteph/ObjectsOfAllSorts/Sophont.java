package com.github.hteph.ObjectsOfAllSorts;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.github.hteph.Utilities.Dice;
import com.github.hteph.Utilities.NameGenerator;

public class Sophont {
	
	private String name;
	private String description;
	private StellarObject homeworld;
	private Map<String,Attribute> attributes = new HashMap<>();
	private String environment;
	

			
// Constructor -------------------------------------------
	
	public Sophont(StellarObject place) {
		
		NameGenerator randomName = null;
		
		try {
			randomName = new NameGenerator("FantasyNames");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int randomNummer = 2+Dice.d6()/2;
		name=randomName.compose(randomNummer)+" of "+place.getName();
	}

	
	//Getters and Setter --------------------------------------
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public StellarObject getHomeworld() {
		return homeworld;
	}

	public void setHomeworld(StellarObject homeworld) {
		this.homeworld = homeworld;
	}

	public Map<String, Attribute> getAttributes() {
		//Can change details in the attributes but not remove or add new ones
		Map<String,Attribute> immutable = new HashMap<String, Attribute>(attributes);
		
		return immutable;
	}

	public void setAttributes(Map<String, Attribute> attributes) {
		this.attributes = attributes;
	}
	
	
	public void addAttribute(String name, String description) {
		
		attributes.put(name, new Attribute(name, description));
	}
	
	public boolean isPresent(String name) {
				
		return attributes.containsKey(name);
		
	}
	
	public void removeAttribute(String name) {
		
		attributes.remove(name);
	}

	public void setEnvironment(String environment) {
		
		this.environment = environment;	
	}

	public String getEnvironment() {
		return environment;
	}
	

}
