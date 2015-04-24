#!/bin/bash

CONTAINER_NAME=${1-app1}
DEBUG=${2-false}
ATTACH_COMMAND=${3-client}
TAG=${4-latest}

docker kill $CONTAINER_NAME
docker rm $CONTAINER_NAME
./rebuild.sh

if [ $DEBUG = "true" ]; then
    docker run -p 5005:5005 --name $CONTAINER_NAME -v ~/.m2/repository:/root/.m2/repository -d flaviof/app1:$TAG
else
    docker run --name $CONTAINER_NAME -v ~/.m2/repository:/root/.m2/repository -d flaviof/app1:$TAG
fi

x=0
while [  $x -lt 5 ]; do
    echo -ne "."
    ((++x))
    sleep 0.5
done

./attach.sh $CONTAINER_NAME client

