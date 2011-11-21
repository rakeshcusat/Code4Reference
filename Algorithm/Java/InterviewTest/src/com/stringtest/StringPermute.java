package com.stringtest;

public class StringPermute {
	String str;
	public StringPermute(String str){
		this.str = str;
	}
	public void permute(){
		int length = str.length();
		boolean[] used = new boolean[length];
		StringBuffer out = new StringBuffer();
		char[] in = str.toCharArray();
		
		doPermute(in, out,used,length,0);
	}
	private void doPermute(char[] in, StringBuffer out, boolean[] used,
			int length,int level) {
		
		if(level==length){
			System.out.println(out.toString());
			return;
		}
		
		for(int index=0;index<length;index++){
			if(used[index]){
				continue;
			}
			
			out.append(in[index]);
			used[index]=true;
			doPermute(in,out,used,length,level+1);
			used[index]=false;
			out.setLength(out.length()-1);
		}
		
	}
}
