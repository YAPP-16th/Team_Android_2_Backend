#!/bin/bash
docker stop teamplay-external-api && docker rm teamplay-external-api
docker rmi $(docker images |grep 'teamplay-external-api')
docker-compose build --build-arg env=$1 && docker-compose up -d
docker-compose up -d
