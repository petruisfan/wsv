#!/usr/bin/python

import os

CP=[ "target/webserver.jar",
    "target/dependency/log4j-1.2.16.jar"
    ]


cmd = "java -classpath " + ":".join(CP) + " com.vvs.Main -g"

#print cmd
os.system("rm target/logs/webserver.log")

os.system(cmd)
