#!/bin/bash
set -xe

echo "     * Stopping ..."
docker_files="-f docker-compose.yml"
docker-compose $docker_files down -v;
docker system prune -f --volumes
echo "     * Done"
