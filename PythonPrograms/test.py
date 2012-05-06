#!/usr/bin/python2.7
##import os
##import urllib
##import urllib.parse
##import urllib.request
##import json
import csv
import sys
import warnings

try:
    import MySQLdb
except ImportError:
    print ('Could not find package "MySQLdb". Try installing it with:')
    print ('   sudo apt-get install python-mysqldb')
    print ('')
    print ('Halting.')
    sys.exit(1)

##String constants
TABLE_NAME = "WhitelistNumber"
DB_NAME = "test"
USER = "root"
PASSWORD = "root"
CSV_FILE_NAME = "./whitelist.txt"

def custom_formatwarning(msg, *a):
    ## Ignore everything except the message
    return "WARNING : " + str(msg) + '\n'

def dropTable(dbCursor, tableName):
    """ Drop the given table from database if it exists"""

    query = "DROP TABLE IF EXISTS " + tableName
    try:
        dbCursor.execute(query)
    except MySQLdb.ProgrammingError as error:
      print("Error : "+str(error))
    except MySQLdb.IntegrityError as error:
        print("Error : "+str(error))
    except Exception as error:
        print("couldn't drop table " + tableName)
        print("Error :"+str(error))


def createTable(dbCursor, tableName):
    """ Create table if doesn't exist"""
    query = "CREATE TABLE IF NOT EXISTS " + tableName + \
            "(id  INT NOT NULL AUTO_INCREMENT PRIMARY KEY," \
            "mdn VARCHAR(100) NOT NULL," \
            "type VARCHAR(100) NOT NULL," \
            "description VARCHAR(255)," \
            "UNIQUE(mdn))"
    try:
        dbCursor.execute(query)
    except MySQLdb.ProgrammingError as error:
      print("Error : "+str(error))
    except MySQLdb.IntegrityError as error:
        print("Error : "+str(error))
    except Exception as error:
        print("couldn't create table " + tableName)
        print("Error :"+str(error))

def insertIntoDB(dbCursor,mdn,whitelistType,description):
    """This method insert data in whitelistNumber table
    """
    query = "INSERT INTO WhitelistNumber (mdn,type, description) VALUES(%s,%s,%s)"
    try:
       dbCursor.execute(query,(mdn,whitelistType,description))
    except MySQLdb.ProgrammingError as error:
        print("Error : "+str(error))
    except MySQLdb.IntegrityError as error:
        print("Error : "+str(error))
    except Exception as error:
        print("couldn't insert "+mdn+", "+whitelistType+", "+description+" ")
        print("Error :"+str(error))


def main():
    ##Configure custom warning
    warnings.formatwarning = custom_formatwarning

    db = MySQLdb.connect(user = USER, passwd = PASSWORD, db = DB_NAME)
    dbCursor = db.cursor()

    createTable(dbCursor,TABLE_NAME)
    with open(CSV_FILE_NAME,'r') as whiteListFile:
        csvReader = csv.reader(whiteListFile, delimiter=',')
        for row in csvReader:
            ##Make sure mdn is number
            if(row[0].isdigit()):
                insertIntoDB(dbCursor,row[0],row[1],row[2])
        whiteListFile.close()

    db.commit()
    ##Clean up
    db.close()


if __name__ == "__main__":
    main()

