#!/bin/bash
docker-compose build --build-arg env=prod && docker-compose up -d
docker stop teamplay-external-api && docker rm teamplay-external-api
docker rmi $(docker images |grep 'teamplay-external-api')
docker-compose up -d
