#!/bin/bash

RED_COLOR="\e[31m"
GREEN_COLOR="\e[32m"
END_COLOR="\e[0m"

jar_paths=$(find ../ -name "target")
port_number=8000

for filepath in $jar_paths
do
	image_name=$(echo $filepath | cut -d '/' -f2 | tr '[:upper:]' '[:lower:]')
	if [ -f $filepath/../Dockerfile ]
        then
		docker run -p $port_number:8080 -d $image_name
		echo -e "${GREEN_COLOR} Container from $image_name image run on external port $port_number and internal port 8080.${END_COLOR}"
		port_number=$(($port_number+1))
	else
		echo -e "${RED_COLOR} No image $image_name exists.${END_COLOR}"
	fi
done
