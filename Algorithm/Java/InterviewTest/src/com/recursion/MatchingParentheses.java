package com.recursion;

public class MatchingParentheses {

	int count;
	
	public MatchingParentheses(int count){
		this.count = count;
	}
	
	public void printPar(){
		char []str = new char[count*2];
		printPar(count,count,str,0);
	}
	
	private void printPar(int l,int r,char[] str,int count){
		if(l<0 ||l>r){
			return;
		}
		
		if(l==0 && r == 0){
			System.out.println(new String(str));
			return;
		}
		
		if(l > 0){
			str[count]='(';
			
			printPar(l-1,r,str,count+1);
		}
		if(r>0){
			str[count]=')';
			printPar(l,r-1,str,count+1);
		}
	}
}
