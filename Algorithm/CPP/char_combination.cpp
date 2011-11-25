#include <iostream>
#include <memory>
#include <cstdlib>
#include <vector>
#include <string>
using namespace std;

#define 	DEST_SIZE	2

void rec_combination(char* src, char * dest, int level, int len)
{
	if(level==DEST_SIZE)
	{
		dest[level]='\0';
		cout<<dest<<endl;
		return ;
	}
	
	for(int index=0;index<len;index++)
	{
		dest[level] = src[index];
		rec_combination(src,dest,level+1,len);
	}
}
int main(int argc,  char **argv){
	
	char *temp =(char*)malloc(sizeof(char)*(DEST_SIZE+1));
	char *src = "abc";
	rec_combination(src,temp,0,3);
	
return 0;
}