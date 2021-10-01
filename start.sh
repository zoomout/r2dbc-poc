#!/bin/bash

set -e

./gradlew bootJar

docker_files="-f docker-compose.yml"

docker-compose $docker_files pull
docker-compose $docker_files build --pull
docker-compose ${docker_files} up --force-recreate --detach
