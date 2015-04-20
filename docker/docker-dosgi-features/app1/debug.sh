#!/bin/bash

docker kill app1
docker rm app1
./rebuild.sh
docker run --name app1 -v ~/.m2/repository:/root/.m2/repository -d bikeemotion/app1

x=0
while [  $x -lt 5 ]; do
    echo -ne "."
    ((++x))
    sleep 0.5
done

./attach.sh app1 client

