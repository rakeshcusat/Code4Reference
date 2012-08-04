#!/usr/bin/env python
from tempfile import mkstemp
from shutil import move
from os import remove, close
import sys

def replace(filename, pattern, subst):
    #Create temp file
    fh, abs_path = mkstemp()
    new_file = open(abs_path,'w')
    old_file = open(filename)
    for line in old_file:
        new_file.write(line.replace(pattern, subst))
    #close temp file
    new_file.close()
    close(fh)
    old_file.close()
    #Remove original file
    remove(filename)
    #Move new file
    move(abs_path, filename)

def main():
    if len(sys.argv) != 2:
        print ('%(prog)s filename ')
        exit()
    
    replace(sys.argv[1],"<br />",'\n')
    
if __name__=="__main__":
    """If the this file  run as program then execute the main method"""
    main()    
   
