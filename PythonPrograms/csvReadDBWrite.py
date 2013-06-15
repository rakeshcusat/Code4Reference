#!/usr/bin/python2.7
import csv
import sys
import warnings
import optparse
try:
    import MySQLdb
except ImportError:
    print ('Could not find package "MySQLdb". Try installing it with:')
    print ('   sudo apt-get install python-mysqldb')
    print ('')
    print ('Halting.')
    sys.exit(1)

##String constants
DB_TABLE_NAME = "WhitelistNumber"
DB_NAME = "test"
DB_USER = "root"
DB_PASSWORD = "root"
CSV_FILE_PATH = "./whitelist.txt"

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
    
    else:
        print("'" + tableName + "' dropped successfully")

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

def displayTable(dbCursor, tableName):
    """ Display the database table"""
    query = "SELECT * FROM " + tableName 
    try:
        dbCursor.execute(query)
        rows = dbCursor.fetchall()
        for row in rows:
            print(row)
            
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

def populateTable(dbCursor, dbTable, csvFilePath):
    ##Create Table
    createTable(dbCursor, dbTable)

    with open(csvFilePath, 'r') as whiteListFile:
        csvReader = csv.reader(whiteListFile, delimiter=',')
        for row in csvReader:
            ##Make sure mdn is number
            if(row[0].isdigit()):
                ##Insert data into whitelistNumber table from CSV file.
                insertIntoDB(dbCursor, row[0], row[1], row[2])
    whiteListFile.close()



def main():
    ##Argument parsing
    OPERATION_EXPLAIN = "OPERATION could be either of these populate, drop or display \n"  \
                        "populate : Reads the csv file and insert the record in whitelistNumber table \n" \
                        "drop     : Drops the WhitelistNumber table \n"  \
                        "display  : display the whitelistNumber table\n"
 
    parser = optparse.OptionParser(usage = "%prog OPERATION [options] \n" + OPERATION_EXPLAIN)
    parser.add_option("-f","--csvfile", default = CSV_FILE_PATH, dest = "csvFile",
                      help = "CSV file path (default: " + CSV_FILE_PATH + ")")
    parser.add_option("-t","--dbtable", default = DB_TABLE_NAME, dest = "dbTable",
                      help = "DB table name (default: " + DB_TABLE_NAME + ")")
    parser.add_option("-n","--dbname", default = DB_NAME, dest = "dbName",
                      help = "DataBase name (default: " + DB_NAME + ")")
    parser.add_option("-u","--dbuser", default = DB_USER, dest = "dbUser",
                      help = "DataBase user name (default: " + DB_USER + ")")
    parser.add_option("-p","--dbpassword", default = DB_PASSWORD, dest = "dbPassword",
                      help = "DataBase password (default: " + DB_PASSWORD + ")")
    (options, args) = parser.parse_args()

#    print(args[0])
    if len(args) != 1:
        parser.print_help()
        return -1
    else:
        ##Configure custom warning
        warnings.formatwarning = custom_formatwarning

        db = MySQLdb.connect(user = options.dbUser, passwd = options.dbPassword, db = options.dbName)
        dbCursor = db.cursor(cursorclass=MySQLdb.cursors.DictCursor)

        if args[0].lower() == "populate":
            populateTable(dbCursor, options.dbTable, options.csvFile)

        elif args[0].lower() == "drop":
            dropTable(dbCursor,options.dbTable)

        elif args[0].lower() == "display": 
                print('==============' + options.dbTable + '==============')
                displayTable(dbCursor, options.dbTable)
                print('===================== END =====================');        
        else:
            parser.print_help()
    
    db.commit()
    ##Clean up
    db.close()


if __name__ == "__main__":
    main()

