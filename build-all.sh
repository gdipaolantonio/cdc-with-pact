#!/bin/bash

ROOT_PATH=`pwd`

################################################################################


echo "[build] Building all projects"

START_TIME=`date +%s`
for project in "frontend" "checkout" "flights" "orders"
do
  echo "[build] Building project '$project'"
  cd $ROOT_PATH/$project
  sh ./build.sh > ./build.out
  if [ $? != 0 ]
    then
      echo "[build] Failure for project '$project'"
      exit $?
  fi
  echo "[build] Success for project '$project'"
done

END_TIME=`date +%s`
EXECUTION_TIME=`expr $END_TIME - $START_TIME`
echo "[build] Successfully executed all project builds in $EXECUTION_TIME seconds"
