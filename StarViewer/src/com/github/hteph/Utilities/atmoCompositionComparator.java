package com.github.hteph.Utilities;

import java.io.Serializable;
import java.util.Comparator;

import com.github.hteph.ObjectsOfAllSorts.AmosphericGases;

public class atmoCompositionComparator implements Comparator<AmosphericGases>,Serializable{
	 
    /**
	 * 
	 */
	private static final long serialVersionUID = -7550070324501751233L;

	@Override
    public int compare(AmosphericGases gas1, AmosphericGases gas2) {
    	
    	// Observe the sorting logic, higher percentage is sorted first
    	
        if(gas1.getPercentageInAtmo() > gas2.getPercentageInAtmo()){
            return -1;
        } else {
            return 1;
        }
    }
}
