#!/bin/bash

docker stop teamplay-external-api && docker rm teamplay-external-api
docker rmi $(docker images |grep 'teamplay-external-api')
ENV=$1 docker-compose up -d
