package com.atc;
/**
 * This Enum defines the various types of  Aircraft
 * @author Rakesh Kumar
 *
 */
public enum AcType {
	PASSENGER,CARGO;

	/**
	 * static method to convert the integer value to AcType enum value
	 * @param enumVal integer value of enum
	 * @return		returns enum variable
	 */
	public static AcType intToEnum(Integer enumVal){

		AcType acType;

		switch(enumVal){
		case 1:
			acType=AcType.PASSENGER;
			break;
		case 2:
			acType=AcType.CARGO;
			break;
		default:	
			acType=null;
		}
		return acType;	
	}
}
