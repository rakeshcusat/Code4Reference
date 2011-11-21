package com.search;

public class MatrixSearch {

	int [][] array;
	int m,n;
	
	public MatrixSearch(int[][] array, int m, int n){
		this.array = array;
		this.m = m;
		this.n = n;
	}
	
	public boolean seach(int no){
		
		int column=n-1;
		int row=0;
		while(column>=0 && row<n){
			
			if(array[row][column]==no){
				System.out.println("row : "+row+", Column : "+column);
				return true;
			}
			if(array[row][column]>no){
				column--;
			}else{
				row++;
			}
		}		
		return false;
	}
}
