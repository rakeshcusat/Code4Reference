package com.code4refernce.caliper;
import com.google.caliper.Runner;

public class CaliperRunner {
	public static void main(String[] args) {
		String myargs[] = new String[1];
		myargs[0] = new String("-Dindex=0,1,2,3,4,5,6,7,8,9,10");
		Runner.main(SimpleCaliperTest.class, myargs);
	}
}
