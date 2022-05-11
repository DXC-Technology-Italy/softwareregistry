# Software Registry - Front End Project

Front End to the application.

## Build and Run

### Configuration

Before running the application it is necessary to configure the following files:

- `app/src/environments/environment.dev.ts`
- `app/src/environments/environment.prod.ts`
- `app/src/environments/environment.ts`

### Run the application

Configure the file `deploy.sh` and run it. The script will basically do the following:

- Build the code
- Build a docker image based on nginx
- Run the docker image

## Run the project in development mode

`ng serve --configuration=dev`
