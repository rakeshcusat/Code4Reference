#!/usr/bin/python3.2
import os
import urllib
import json


url = "https://graph.facebook.com/713371871"
args = str('{"id": "713371871","name": "Rakesh Kumar","first_name": "Rakesh","last_name": "Kumar", "username": "hamepal","gender": "male","locale": "en_GB"}')
print(url)
##params = "[,".join(args)+"]"
mapObj = json.loads(args)
##,urllib.parse.urlencode(mapObj).encode('utf8')
response = urllib.request.urlopen(url)
httpcode = response.getcode()
data = response.read()
dataMap = json.loads(data.decode())

if httpcode != 200:
    print('Error : code :'+httpcode+', data : '+data)
    sys.exit(1)

print("Success : ")
print(dataMap)

