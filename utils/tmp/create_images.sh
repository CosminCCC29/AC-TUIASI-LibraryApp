#!/bin/bash

RED_COLOR="\e[31m"
GREEN_COLOR="\e[32m"
END_COLOR="\e[0m"

jar_paths=$(find ../ -name "target")

for filepath in $jar_paths 
do
	image_name=$(echo $filepath | cut -d '/' -f2 | tr '[:upper:]' '[:lower:]')	
	
	if [ -f $filepath/../Dockerfile ]
	then
		docker build -t $image_name $filepath/../
		echo -e "${GREEN_COLOR} Image $image_name created.${END_COLOR}"
	else
		echo -e "${RED_COLOR} No docker file for $image_name image.${END_COLOR}"
	fi
done
