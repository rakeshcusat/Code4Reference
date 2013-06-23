#!/usr/bin/env python

from github import Github
#import github3  #It is not used currently
from datetime import datetime
import argparse
import sys
import time

try:
    import MySQLdb
except ImportError:
    print ('Could not find package "MySQLdb". Try installing it with:')
    print ('   sudo apt-get install python-mysqldb')
    print ('')
    print ('Halting.')
    sys.exit(1)
       
#Some global variables       
DB_NAME = "githubdb"
TABLE_NAME = "github_user"
DB_PSWD="root"
DB_USER="root"
DB_HOST="localhost"

#Create data base statement for scema creation.
CREATE_DATA_BASE = "CREATE DATABASE IF NOT EXISTS {db_name}".format(db_name=DB_NAME)
#Create table statement
CREATE_USERINFO_TABLE = "CREATE TABLE IF NOT EXISTS {db_name}.{table_name} ( " \
						"id INTEGER NOT NULL, " \
						"name VARCHAR(200), " \
						"login VARCHAR(200) NOT NULL, " \
						"email VARCHAR(200), " \
						"location VARCHAR(200), " \
						"company VARCHAR(200), " \
						"repos INTEGER, " \
						"created DATETIME, " \
                        " PRIMARY KEY (id)" \
                        ")" \
						.format(db_name=DB_NAME, table_name=TABLE_NAME)  
                        
#Select max id statement                        
SELECT_MAX_ID = "SELECT MAX(id) FROM {db_name}.{table_name}".format(db_name=DB_NAME, table_name=TABLE_NAME)                        

#Insert record statement                          
INSERT_RECORD = "INSERT INTO {db_name}.{table_name} " \
                "( id, name, login, email, location, company, repos, created) " \
                "VALUES (%s, %s, %s, %s, %s, %s, %s, %s)".format(db_name=DB_NAME, table_name=TABLE_NAME)    
def get_cur_time():
    """Utility method to return the current time in string"""
    return datetime.now().strftime("%d-%m-%y-%H:%M:%S")
             
def create_schema(db_con, db_cursor):
    """This mehtod creates the database table"""
    try:
        #First create the database.
        db_cursor.execute(CREATE_DATA_BASE)
        #Now add define table.
        db_cursor.execute(CREATE_USERINFO_TABLE)
        db_con.commit()
        
    except MySQLdb.ProgrammingError as error:
        print("Error : "+str(error))
        
    except MySQLdb.IntegrityError as error:
        print("Error : "+str(error))
        
    except Exception as error:
        print("Couldn't create {table} ".format(table=tableName))
        print("Error : {err}".format(err=error))
        
def select_max_id(db_cursor):
    """This method selects max id from github_user table."""
    try:
        db_cursor.execute(SELECT_MAX_ID)
        result = db_cursor.fetchone()    
        
    except Exception as error:
        print ("Couldn't fetch max id from {table}".format(table=TABLE_NAME)) 
        print("Error : {err}".format(err=error))
    
    if result[0]:    
        print ("Max Id : {rslt}".format(rslt=result[0]))    
        return result[0]
    else:
        print ("There are no user info in db")
        return 0
    
def insert_user_info(db_con, 
                     db_cursor, 
                     user_id, 
                     name, 
                     login, 
                     email, 
                     location, 
                     company, 
                     repos, 
                     created_date):
    """This method inserts record in githubdb.github_user table."""
    
    try:
        db_cursor.execute(INSERT_RECORD, (user_id, 
                                          name, 
                                          login, 
                                          email, 
                                          location, 
                                          company,
                                          repos,
                                          created_date))
        db_con.commit()                                  
    except MySQLdb.ProgrammingError as error:
		print("Error : "+str(error))
        
    except MySQLdb.IntegrityError as error:
		print("Error : "+str(error))
        
    except Exception as error:
		print("Couldn't insert into {table}".format(table=TABLE_NAME))
		print("Error : {error}".format(error))

	
def get_user_detail(pswd):
	"""This method uses github3 library"""
	g = github3.login(username="rakeshcusat", password=pswd)
		
	fhandler = open("githubuser_"+get_cur_time(), "w")
	
	for user in g.iter_all_users():
		user.refresh()
		try:
			fhandler.write(" user: {0}, email: {1}, location: {2}\n".format(str(user), str(user.email), str(user.location)))
		except:
			print "Something wrong, user id : {0}".format(user.id);
	fhandler.close()		
	
                
def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("-u", 
                        "--gituser", 
                        dest="git_user",
                        help="Github user name",
                        required=True)
                        
    parser.add_argument("-p",
                        "--getpswd",
                        dest="git_password",
                        help="Github password",
                        required=True)
                        
    parser.add_argument("--dbuser",
                        dest="db_user",
                        default=DB_USER,
                        help="Database user name")
                        
    parser.add_argument("--dbpswd",
                        dest="db_pswd",
                        default=DB_PSWD,
                        help="Database password")
                        
    parser.add_argument("--dbhost",
                        dest="db_host",
                        default=DB_HOST,
                        help="Database host name")                        
                        
    parser.add_argument("-c",
                        "--createschema",
                        dest="create_schema",
                        action="store_true",
                        default=False,
                        help="Create the Data Base schema")                        
    args = parser.parse_args()

    print "args {ar}".format(ar=args)
     
    
    con = MySQLdb.connect(user=args.db_user, passwd=args.db_pswd, db=DB_NAME, host=args.db_host)
    db_cursor = con.cursor()
    
    if args.create_schema:
        create_schema(con, db_cursor)
        
    #Create Github object to access git API.
    gh = Github(login_or_token=args.git_user, password=args.git_password, per_page=100)
	
	
    for user in gh.get_users(since=select_max_id(db_cursor)):
        insert_user_info(con, 
                        db_cursor,
                        user.id, 
                        user.name, 
                        user.login, 
                        user.email, 
                        user.location,
                        user.company,
                        user.public_repos,
                        user.created_at)
                        
        print u"'{usr_Id}', '{usr_name}', '{usr_login}', "\
               "'{usr_email}', '{usr_location}', '{usr_company}', "\
               "'{usr_repo}', '{usr_joining}'\n".format(usr_Id=user.id, 
		                                                usr_name=user.name, 
		                                                usr_login=user.login,
                                                        usr_email=user.email, 
		                                                usr_location=user.location,
		                                                usr_company=user.company, 
		                                                usr_repo=user.public_repos,
		                                                usr_joining=user.created_at)
                          																		
        print "{cur_time} Rate limit : {limit}".format(cur_time=get_cur_time(),
                                                       limit=gh.rate_limiting)
                                                       
        #We should sleep for sometime to throttle the API call.
        time.sleep(0.5)
        
        if gh.rate_limiting == 0:
            print ("API call has exceeded the limit. We should wait for 15 mins.")
            time.sleep(900)
            print ("Resuming it again at {cur_time}".format(cur_time=get_cur_time()))
            
    con.close()
    sys.exit(0)
        
if __name__ == "__main__":
	main()
	
