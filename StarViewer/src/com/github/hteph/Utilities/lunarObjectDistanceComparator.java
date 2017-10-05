package com.github.hteph.Utilities;

import java.io.Serializable;
import java.util.Comparator;

import com.github.hteph.ObjectsOfAllSorts.StellarObject;

public class lunarObjectDistanceComparator implements Comparator<StellarObject>,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7431143695046494519L;

	@Override
	public int compare(StellarObject o1, StellarObject o2) {
		
        if(o1.getOrbitalDistance() > o2.getOrbitalDistance()){
            return -1;
        } else {
            return 1;
        }
	}

}
