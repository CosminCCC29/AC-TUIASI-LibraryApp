#!/bin/bash

RED_COLOR="\e[31m"
GREEN_COLOR="\e[32m"
END_COLOR="\e[0m"

jar_paths=$(find ../ -name "target")

for filepath in $jar_paths
do
        image_name=$(echo $filepath | cut -d '/' -f2 | tr '[:upper:]' '[:lower:]')
        if [ "$(docker images -q $image_name 2> /dev/null)" != "" ]
	then
		docker rmi $image_name
		echo -e "${GREEN_COLOR} Image $image_name deleted.${END_COLOR}"
        else
		echo -e "${RED_COLOR} No image $image_name exists.${END_COLOR}"
        fi
done
