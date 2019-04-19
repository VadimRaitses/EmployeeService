#!/usr/bin/env bash

git clone https://github.com/VadimRaitses/EventService.git
pushd EventService
./gradlew build
touch ./Dockerfile
echo "FROM openjdk:8-jre " >> ./Dockerfile
echo "ADD /build/libs/eventservice-0.0.1.jar /eventservice.jar " >> ./Dockerfile
echo 'CMD ["java", "-jar", "-Dspring.profiles.active=prod" ,"eventservice.jar"] ' >> ./Dockerfile
docker build -t eventservice:latest .
docker run -d --name eventservice -p 8081:8081 --net=hubnetwork eventservice:latest
popd
