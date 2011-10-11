package com.atc;
/**
 * This Enum defines the various sizes of Aircraft
 * @author Rakesh Kumar
 *
 */

public enum AcSize {
	BIG, SMALL;

	/**
	 * static method to convert the integer value to AcSize enum value
	 * @param enumVal integer value of enum
	 * @return		returns AcSize enum variable
	 */
	public static AcSize intToEnum(Integer enumVal){

		AcSize acSize;

		switch(enumVal){
		case 1:
			acSize=AcSize.BIG;
			break;
		case 2:
			acSize=AcSize.SMALL;
			break;
		default:	
			acSize=null;
		}
		return acSize;	
	}
}
