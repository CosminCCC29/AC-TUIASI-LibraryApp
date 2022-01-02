#!/bin/bash

RED_COLOR="\e[31m"
GREEN_COLOR="\e[32m"
END_COLOR="\e[0m"

jar_paths=$(find ../../ -name "target")

for filepath in $jar_paths
do
	echo $filepath
	if [ "$(ls $filepath | grep ".jar")" != "" ]
	then
		cp Dockerfile $filepath/../Dockerfile
               	echo -e "${GREEN_COLOR} Dockerfile created in $(realpath $filepath/../Dockerfile).${END_COLOR}"
	else
		echo -e "${RED_COLOR} Dockerfile can not be created in $(realpath $filepath/../Dockerfile).${END_COLOR}"
	fi
done

