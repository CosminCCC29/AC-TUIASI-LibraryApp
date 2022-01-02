#!/bin/bash

RED_COLOR="\e[31m"
GREEN_COLOR="\e[32m"
END_COLOR="\e[0m"

jar_paths=$(find ../ -name "target")

for filepath in $jar_paths
do
        image_name=$(echo $filepath | cut -d '/' -f2 | tr '[:upper:]' '[:lower:]')
	if [ "$(docker ps -aq --filter ancestor="$image_name")" != "" ]
	then
        	docker rm $(docker stop $(docker ps -a -q --filter ancestor=$image_name --format="{{.ID}}"))
		echo -e "${GREEN_COLOR} Container deleted for image $image_name.${END_COLOR}"
	else
		echo -e "${RED_COLOR} No container exists for image $image_name.${END_COLOR}"
	fi
done

