#include <stdio.h>

#define MAX_SIZE     4

void zigzagArray(int array[][MAX_SIZE], int size){

	int rows=0;
	int columns=0;
	
		while(rows<size){
			printf("%d ",array[rows][columns]);
			
			if(rows==size-1){
				rows = columns + 1;
				columns = size - 1;	
			}else if(columns ==0){
				columns = rows + 1;
				rows = 0;	
			}else{
				rows++;
				columns--;
			}
		}//end of while
}

int main(int arc, char **argv){
  
  int myarray[MAX_SIZE][MAX_SIZE]={{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,16}};
  
  zigzagArray(myarray,MAX_SIZE);
  return 0;
}

