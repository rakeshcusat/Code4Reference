#!/usr/bin/env python

import os

#For accessing the environment variable.
print os.environ['HOME']

#For setting the environment variable.
os.environ['PY_ENV_SET']='environ set the env'

print os.environ['PY_ENV_SET']

