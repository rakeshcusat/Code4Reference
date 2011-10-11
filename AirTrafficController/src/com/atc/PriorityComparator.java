package com.atc;

import java.util.Comparator;

public class PriorityComparator implements Comparator<Ac> {

	@Override
	public int compare(Ac acFirst, Ac acSecond) {
		/*comparing the priority of the Aircraft*/
		return (acFirst.getPriority()-acSecond.getPriority());
	}

}
