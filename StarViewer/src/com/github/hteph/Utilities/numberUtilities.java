package com.github.hteph.Utilities;

public final class numberUtilities {
	
	

	// Constructor ----------------------------------------------
	
	private numberUtilities() {
		
		//No instances of this class, please
	}

	//Methods --------------------------------------------------
	
	public static double nicefyDouble(double number) {
		
		int divisor =(int) Math.log10(number);
		
		divisor +=3;
		
		if(divisor<1) return number = 0.001;
		
		number = ((int) (number*Math.pow(10, divisor)))/Math.pow(10, divisor);
		
		
		return number;
	}

	//Internal Methods ----------------------------------------

	// Getters and Setters -------------------------------------
}
