#!/bin/bash

docker kill app2
docker rm app2
./rebuild.sh
docker run --name app2 -v ~/.m2/repository:/root/.m2/repository -d flaviof/app2

x=0
while [  $x -lt 5 ]; do
    echo -ne "."
    ((++x))
    sleep 0.5
done

./attach.sh app2 client

