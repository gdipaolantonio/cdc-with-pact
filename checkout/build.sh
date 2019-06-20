#!/bin/bash

ROOT_PATH=`dirname $0`

################################################################################

sh $ROOT_PATH/mvnw clean install pact:publish
if [ $? != 0 ]
  then
    exit $?
fi
