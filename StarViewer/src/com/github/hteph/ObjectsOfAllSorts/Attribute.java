package com.github.hteph.ObjectsOfAllSorts;

public class Attribute {
	
	private String name;
	private String description;
	private int level;
	
	public Attribute(String name, String description) {
		
		this.name=name;
		this.description = description;
	}

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

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	public void increaseLevel() {
		this.level++;
	}
	
	public void decreaseLevel() {
		this.level--;
	}
	
	
}
