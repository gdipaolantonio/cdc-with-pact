#!/bin/bash

ROOT_PATH=`pwd`

################################################################################

echo "[build] Building all projects"

for project in "checkout" "flights" "frontend" "orders"
do
  echo "[build] Building project '$project'"
  cd $ROOT_PATH/$project
  sh ./build.sh
  if [ $? != 0 ]
    then
      echo "[build] Failure for project '$project'"
      exit $?
  fi
  echo "[build] Success for project '$project'"
done

echo "[build] Successfully executed all project builds"
