package com.test;

public class ClassImplMarkerInterface implements MarkerInterface{
	String str;
	public ClassImplMarkerInterface(String str){
		this.str = str;
	}
	
	public void print(){
		System.out.println(str);
	}
}
