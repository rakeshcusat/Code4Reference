package com.atc;

import java.io.Serializable;

public class Ac implements Serializable,Comparable<Ac>{


	/**
	 * 
	 */
	private static final long serialVersionUID = -3483763564235984554L;

	private String	acName;	/*Air-craft name*/
	private AcType 	acType;	/*Air-craft type, it could be Passenger or Cargo */
	private AcSize 	acSize;	/*Air-craft Size it could be Big or small*/
	private Integer priority;  /*priority of the Aircraft*/

	public Ac(){};  /*default construct for the Serializable interface*/

	public Ac(String acName,AcType acType,AcSize acSize){
		this.acName = acName;
		this.acType = acType;
		this.acSize = acSize;
		calcPriority(); 
	}

	/**
	 * This method calculates the priority of the Air-craft
	 */
	private void calcPriority(){
		/*Assumption: The lowest value of the priority will get the highest priority*/
		setPriority(acType.ordinal()+acSize.ordinal()); 
	}
	/**
	 * returns the Air-craft name
	 * @return
	 */
	public String getAcName() {
		return acName;
	}
	/**
	 * sets the Air-craft name
	 * @param acName
	 */
	public void setAcName(String acName) {
		this.acName = acName;
	}

	/**
	 * returns Air-craft type
	 * @return
	 */
	public AcType getAcType() {
		return acType;
	}

	/**
	 * sets Air-craft type
	 * @param acType
	 */
	public void setAcType(AcType acType) {
		this.acType = acType;
	}

	/**
	 * Returns Air-craft size
	 * @return
	 */
	public AcSize getAcSize() {
		return acSize;
	}

	/**
	 * Sets the Air-craft size
	 * @param acSize
	 */
	public void setAcSize(AcSize acSize) {
		this.acSize = acSize;
	}

	/**
	 * returns the priority of the Air-craft
	 * @return
	 */
	public Integer getPriority() {
		return priority;
	}

	/**
	 * Sets the priority of the Air-craft
	 * @param priority
	 */
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	/**
	 * provides the comparison of different object.
	 */
	@Override
	public int compareTo(Ac ac) {

		return this.getPriority()-ac.getPriority();
	}
	/**
	 * toString() implementation.
	 */
	public String toString(){
		return "Air-Craft : '"+getAcName()+"' Type : '"+getAcType()+"' Size : '"+getAcSize()+"'";
	}
}
