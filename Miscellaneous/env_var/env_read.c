#include <stdio.h>
#include <stdlib.h>

void print_env(char * env_var){

  char *path = getenv (env_var);

  if (path!=NULL){  
    printf (" %s \n",path); 
  }
  else{ 
    printf(" %s not found in the environment\n", env_var); 
  }
}
int main ()
{
  print_env("HOME");
  putenv("C_ENV_SET=C set the env");
  print_env("C_ENV_SET");
  return 0;
}




