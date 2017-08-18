package com.github.hteph.Utilities;

import java.text.DecimalFormat;

public final class numberUtilities {
	
	

	// Constructor ----------------------------------------------
	
	private numberUtilities() {
		
		//No instances of this class, please
	}

	//Methods --------------------------------------------------
	
	public static double nicefyDouble(double number) {
		
		
	if(number<0)	number = (Math.round(number*1000))/1000.0;
	else if(number<100) number = (Math.round(number*100))/100.0;
	else  number = (Math.pow(10, Math.log10(number)-2)*(int) number/Math.pow(10, Math.log10(number)-2));
		
		return number;
	}

	//Internal Methods ----------------------------------------

	// Getters and Setters -------------------------------------
}
