#!/bin/bash
#This script is to search jar file for java class file.
USAGE="Usage: `basename $0` [-hw] <class-name> <dir-path> 
 -h   For help 
 -w   exact class name match 
  
 e.g :
 ./`basename $0` -w Activity ./android-sdk-linux 

 "

#parse command line options
while getopts hw OPT;do
    case "$OPT" in
        h) 
            echo -e "$USAGE"
            exit 0
            ;;
        w)
           specific=1
           ;;
        \?)
          #getopts issues an error
          echo -e "$USAGE" >&2
          exit 1
          ;;
    esac
done            
shift `expr $OPTIND - 1`
#echo "print 1 : $1"
if [ "$specific" -eq 1 ]; then
    pattern="/$1\\.class"
else
    pattern=$1
fi    

shift

echo "pattern : $pattern"

for jar in $(find $* -type f -name "*.jar")
do
  match=`jar -tf $jar | grep $pattern`
  if [ ! -z "$match" ]
  then
    echo "Found in: $jar"
    echo "$match"
  fi
done
