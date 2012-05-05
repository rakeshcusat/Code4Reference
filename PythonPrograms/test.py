#!/usr/bin/python3.2
import os
import urllib
import urllib.parse
import urllib.request
import json


##,urllib.parse.urlencode(mapObj).encode('utf8')
data = urllib.parse.urlencode({'type': 'device_code','client_id': 150792241632891})
##print(data)
data = data.encode('utf-8')
##print(data);
request = urllib.request.Request("https://graph.facebook.com/oauth/device")
request.add_header("Content-Type","application/json;charset=utf-8")
try:
    response = urllib.request.urlopen(request,data)
    httpcode = response.getcode()
    responseData = response.read()
    dataMap = json.loads(responseData.decode())
except Exception as e:
    print ("Exception : "+str(e))
    
if httpcode != 200:
    print('Error : code :'+httpcode+', data : '+data)
    sys.exit(1)

print("Success : ")
print(dataMap)


