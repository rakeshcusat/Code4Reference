package com.stringtest;

public class StringSearch {

	String str;
	
	public StringSearch(String str){
		this.str =str;
	}
	
	public int rabinKarp(String sub){
		int count =-1;	
		int hsub = stringHash(sub);
		int m = sub.length();
		int n = this.str.length();
		int hs = stringHash(this.str.substring(0,m));
		
		for(int index=0;index<n-m-1;index++){
			if(hs==hsub){
				if(this.str.substring(index,index+m).equals(sub)){
					return index;
				}
			}
			
			hs=rollingHash(this.str,index,index+m+1,hs);
		}
		
		return -1;
	}
	
	/**
	 * zero based index.
	 * @param strarg
	 * @param start
	 * @param end
	 * @param hash
	 * @return
	 */
	private int rollingHash(String strarg,int start,int end,int hash){
		return hash-strarg.charAt(start)+strarg.charAt(end);
	}
	
	private int stringHash(String strarg){
		int hashvalue=0;
		
		for(int index=0;index<strarg.length();index++){
			hashvalue +=strarg.charAt(index);
		}
		return hashvalue;
	}
}
