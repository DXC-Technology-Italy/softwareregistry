#/bin/bash

# START CONFIGURATION
NAME=
GROUP=
TAG=
EXTERNAL_PORT=8282
INTERNAL_PORT=8080
DATA_HOME=
INDEX_HOME=
# END CONFIGURATION

docker stop ${NAME} && docker rm ${NAME}
docker rmi ${GROUP}/${NAME}:${TAG}

docker build -t ${GROUP}/${NAME}:${TAG} .
docker run --name ${NAME} -d -p 0.0.0.0:${EXTERNAL_PORT}:${INTERNAL_PORT} -v ${DATA_HOME}:/data -v ${INDEX_HOME}:/index ${GROUP}/${NAME}:${TAG}
