package com.atc;

import java.util.Comparator;

public class PriorityComparator implements Comparator<Ac> {
	/**
	 * This class has not been used currently, but later it will be used in shorting the 
	 * object array.
	 */
	@Override
	public int compare(Ac acFirst, Ac acSecond) {
		/*comparing the priority of the Aircraft*/
		return (acFirst.getPriority()-acSecond.getPriority());
	}

}
