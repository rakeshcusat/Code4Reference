#include <iostream>
#include <stack>

using namespace std;

void find_number(int array[],int size,int num){

	for(int index=0;index<size;)
	{
		if(array[index] == num){
			cout<<"Found the number at index = "<<index<<endl;
			return;
		}
		index += num-array[index];
	}
	cout<<"Couldn't find the number "<<endl;
}
int main(int argc,  char **argv){

	int myarray[]={3,4,5,4,3,2,3,4,5,6,7,8,9,10,9};
	find_number(myarray, sizeof(myarray)/sizeof(int),7);
	
return 0;
}