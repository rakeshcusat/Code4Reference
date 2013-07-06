#!/usr/bin/env python

#import github3  #It is not used currently
from github import Github
from datetime import datetime
import argparse
import sys
import time
import traceback

#For sending email-notification
from smtplib import SMTP    
from smtplib import SMTPException  

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
MAX_ATTEMPTS = 6

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
INSERT_RECORD = u"INSERT INTO {db_name}.{table_name} " \
                u"( id, name, login, email, location, company, repos, created) " \
                u"VALUES (%s, %s, %s, %s, %s, %s, %s, %s)".format(db_name=DB_NAME, table_name=TABLE_NAME) 

#e-mail related variables                
EMAIL_SUBJECT = "Github test notification"
EMAIL_SUBJECT_URGENT = "[URGENT]: " + EMAIL_SUBJECT
EMAIL_FROM = "notification@code4reference.com"
EMAIL_RECEIVERS = ['hamepal@gmail.com']

def listToStr(lst):
    """This method makes comma separated list item string"""
    return ','.join(lst)
    
def send_email(sub, msg):
    """This method sends an email""" 
  
    msg_header = "From: " + EMAIL_FROM + "\n" + \
                 "To: " + listToStr(EMAIL_RECEIVERS) + "\n" + \
                 "Subject: " + sub + "\n"
    msg_body =  msg_header + msg

    try:
      smtpObj = SMTP('localhost')
      smtpObj.sendmail(EMAIL_FROM, EMAIL_RECEIVERS, msg_body)
      smtpObj.quit()
    except SMTPException as error:
      print "Error: unable to send email :  {err}".format(err=error)
                   
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
		print("Error : {err}".format(err=error))

			

def get_pygithub_user_details(db_con, db_cursor, args):
    #Create Github object to access git API.
    gh = Github(login_or_token=args.git_user, password=args.git_password, per_page=100)
    while_count = 0
    try:
        while while_count < MAX_ATTEMPTS:
            try:
                for user in gh.get_users(since=select_max_id(db_cursor)):
                    try:
                        insert_user_info(db_con, 
                                        db_cursor,
                                        user.id, 
                                        (u'' + user.name) if user.name else None, 
                                        (u'' + user.login), 
                                        (u'' + user.email) if user.email else None, 
                                        (u'' + user.location) if user.location else None,
                                        (u'' + user.company) if user.company else None,
                                        user.public_repos,
                                        user.created_at)
                                        
                    except Exception as error:
                        exc_type, exc_value, exc_traceback = sys.exc_info()
                        print_and_send_exception("Got excetpion , but moving forward anyways",
                                                 EMAIL_SUBJECT,
                                                 exc_type, 
                                                 exc_value, 
                                                 exc_traceback)
                       
                        
                    print "{cur_time} Rate limit : {limit}".format(cur_time=get_cur_time(),
                                                                   limit=gh.rate_limiting)
                                                                   
                    #We should sleep for sometime to throttle the API call.
                    time.sleep(0.5)
                    
                    if gh.rate_limiting[0] == 0:
                        print ("API call has exceeded the limit. We should wait for 15 mins.")
                        time.sleep(900)
                        print ("Resuming it again at {cur_time}".format(cur_time=get_cur_time()))
                        
            except RateLimitExceededException:
                send_email(EMAIL_SUBJECT, 
                         "Rate limit has exhausted, will resume after 15 mins.")
                time.sleep(900)
                while_count +=1           
                               
                                                            
    except Exception as error:
        exc_type, exc_value, exc_traceback = sys.exc_info()
        print_and_send_exception("Got excetpion , terminating", EMAIL_SUBJECT_URGENT, exc_type, exc_value, exc_traceback)
        print (error_message)
        sys.exit(1)
    
    #Finally send a termination email.
    send_email(EMAIL_SUBJECT, "Seems everything is done")
        
def print_and_send_exception(initial_msg, sub, exc_type, exc_value, exc_traceback):
        error_message = initial_msg + ": {err} ".format(err=repr(traceback.format_exception(exc_type, 
                                                                               exc_value,
                                                                               exc_traceback)))
        print (error_message)
        send_email(sub,error_message)
        
                                                                                       
def get_github3_user_details(db_con, db_cursor, args):
    """This method uses github3 library"""
    gh = github3.login(username=args.git_user, password=git_password)
			
    for user in gh.iter_all_users():
        user.refresh()
        try:
            fhandler.write(" user: {0}, email: {1}, location: {2}\n".format(str(user), str(user.email), str(user.location)))
        except:
            print "Something wrong, user id : {0}".format(user.id);
 
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
        
    get_pygithub_user_details(con, db_cursor, args)
            
    con.close()
    sys.exit(0)
        
if __name__ == "__main__":
	main()
	
