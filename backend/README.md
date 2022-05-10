# Software Registry - Back End Project

This project exposes REST APIs to view and manage the following objects:

- Areas (an area can be seen as a gitlab group)
- Repositories
- Projects
- Projects extended information
- Dependencies within each Java Project
- Users
- User Access Logs
- Software Releases: information about project releases

## Build and Run

### Configuration

Before running the application it is necessary to configure the following files:

- `src/main/resources/application.properties`

### Run the application

Configure the file `deploy.sh` and run it. The script will basically do the following:

- Build the code
- Build a docker image based on nginx
- Run the docker image
