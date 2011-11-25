#!/bin/bash

# development trick: rem,ove logs before starting.
rm target/logs/webserver.log

# start the application.
java -classpath target/webserver.jar:target/dependency/log4j-1.2.16.jar \
    com.vvs.Main -c #$1
