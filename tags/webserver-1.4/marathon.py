#!/usr/bin/python

import os

MARATHON_HOME="/opt/marathon/marathon"
PROJECT_HOME="src/test/marathon"
REPORT_DIR="src/test/marathon/TestReports"

cmd = MARATHON_HOME + " -capture -reportdir " + REPORT_DIR +  " -batch " + PROJECT_HOME

os.system(cmd)
