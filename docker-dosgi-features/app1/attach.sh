#!/bin/bash

docker exec -it $(docker inspect --format '{{ .Id }}' $1) $2
echo -e "\e[0mNormal Text"
