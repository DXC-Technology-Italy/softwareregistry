#!/bin/bash

export PATH=$PATH:/opt/node/bin

# START CONFIGURATION
NAME=
GROUP=
TAG=
EXTERNAL_PORT=8181
INTERNAL_PORT=80
DATA_HOME=
# END CONFIGURATION

echo "Building angular code"
ANGULAR_BUILD="$(cd app && npm i && ng build --prod)"
echo "Done..."

echo "Stopping running instances..."
docker stop ${NAME} && docker rm ${NAME} && docker rmi ${GROUP}/${NAME}:${TAG}
echo "Done..."

echo "Building Docker image"
docker build -t ${GROUP}/${NAME}:${TAG} .
echo "Done..."

echo "Run:"
docker run --name ${NAME} -p ${EXTERNAL_PORT}:${INTERNAL_PORT} -d -v ${DATA_HOME}:/usr/share/nginx/html/data ${GROUP}/${NAME}:${TAG}
