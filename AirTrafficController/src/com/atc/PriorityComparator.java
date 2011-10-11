package com.atc;

import java.util.Comparator;

public class PriorityComparator implements Comparator<AC> {

	@Override
	public int compare(AC acFirst, AC acSecond) {
		/*comparing the priority of the Aircraft*/
		return (acFirst.getPriority()-acSecond.getPriority());
	}

}
