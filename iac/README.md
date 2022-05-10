# Software Registry Project - Infrastructure as Code Sources

## Run Jenkins Pipeline

Jenkins pipeline source code is located in the `Jenkins` directory. 
It is a groovy source file that can be *imported* as a Jenkins pipeline on a Jenkins instance.
It is necessary to configure the initial section of the `Jenkinsfile` based on the execution environment.

## How to create and run frontend and backend docker containers

1. Copy:
   1. backend/* to ${SOFTWARE REGISTRY ROOT}/backend
   2. frontend/* to ${SOFTWARE REGISTRY ROOT}/frontend
2. Run ${SOFTWARE REGISTRY ROOT}/backend/deploy.sh
3. Run ${SOFTWARE REGISTRY ROOT}/frontend/deploy.sh
