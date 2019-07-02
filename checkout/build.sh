#!/bin/bash

ROOT_PATH=`dirname $0`

################################################################################

sh $ROOT_PATH/mvnw clean install pact:publish -Dpact.verifier.publishResults=true
if [ $? != 0 ]
  then
    exit $?
fi
