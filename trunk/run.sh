#!/bin/bash

java -classpath target/webserver.jar:target/dependency/log4j-1.2.16.jar \
    com.vvs.Main -c #$1
